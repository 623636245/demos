package cn.lannis.demo.codegenerater.controller;
//                            _ooOoo_
//                           o8888888o
//                           88" . "88
//                           (| -_- |)
//                            O\ = /O
//                        ____/`---'\____
//                      .   ' \\| |// `.
//                       / \\||| : |||// \
//                     / _||||| -:- |||||- \
//                       | | \\\ - /// | |
//                     | \_| ''\---/'' | |
//                      \ .-\__ `-` ___/-. /
//                   ___`. .' /--.--\ `. . __
//                ."" '< `.___\_<|>_/___.' >'"".
//               | | : `- \`.;`\ _ /`;.`/ - ` : | |
//                 \ \ `-. \_ __\ /__ _/ .-` / /
//         ======`-.____`-.___\_____/___.-`____.-'======
//                            `=---='
//
//         .............................................
//                  佛祖镇楼           BUG辟易
//
//                             佛曰:
//
//                  写字楼里写字间，写字间里程序员；
//                  程序人员写程序，又拿程序换酒钱。
//                  酒醒只在网上坐，酒醉还来网下眠；
//                  酒醉酒醒日复日，网上网下年复年。
//                  但愿老死电脑间，不愿鞠躬老板前；
//                  奔驰宝马贵者趣，公交自行程序员。
//                  别人笑我忒疯癫，我笑自己命太贱；
//                  不见满街漂亮妹，哪个归得程序员？

import cn.lannis.demo.codegenerater.constants.Common;
import cn.lannis.demo.codegenerater.entity.Config;
import cn.lannis.demo.codegenerater.entity.TableVo;
import cn.lannis.demo.codegenerater.service.impl.CodeGenerateOne;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @package: cn.lannis.demo.codegenerater
 * @program: demos
 * @description: 代码生成控制器
 * @author: LuBangTao
 * @create: 2019-11-14 13:16
 **/
@Controller
@RequestMapping("/")
public class GenerateController {
    @RequestMapping("/")
    public String start() {
        return "index";
//        TableVo table = new TableVo();
//        //表名
//        table.setTableName("db_mapping");
//        //表主键策略（目前只支持UUID）
//        table.setPrimaryKeyPolicy("uuid");
//        //子业务包名
//        table.setEntityPackage("system");
//        //实体类名
//        table.setEntityName("DBMapping");
//        //功能描述
//        table.setFtlDescription("数据库库表字段匹配");
//        new CodeGenerateOne(table).generateCodeFile();
//        System.out.println("----生成完成----");
//        return "";
    }

    @RequestMapping("/generate")
    public void download(Config config, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        System.out.println(config.toString());
        try {
            TableVo table = new TableVo();
            //设置表名
            table.setTableName(config.getTbName());
            table.setPrimaryKeyPolicy("uuid");
            //项目名称
            table.setEntityPackage(config.getXmmc());
            //实体名称
            table.setEntityName(config.getStmc());
            //描述，包括Controller,Service,ServiceImpl,Mapper
            table.setFtlDescription(config.getMs());
            String flag = new CodeGenerateOne(table).generateCodeFile();
            if("1".equals(flag)){
                /**这个集合就是你想要打包的所有文件，
                 * 这里假设已经准备好了所要打包的文件*/
//            List<File> files = new ArrayList<File>();
                /**创建一个临时压缩文件，
                 * 我们会把文件流全部注入到这个文件中
                 * 这里的文件你可以自定义是.rar还是.zip*/
                File file = new File("D:/code.rar");
                if (!file.exists()) {
                    file.createNewFile();
                }
                response.reset();
                //response.getWriter()
                //创建文件输出流
                FileOutputStream fous = new FileOutputStream(file);
                /**打包的方法我们会用到ZipOutputStream这样一个输出流,
                 * 所以这里我们把输出流转换一下*/
                ZipOutputStream zipOut = new ZipOutputStream(fous);
                /**这个方法接受的就是一个所要打包文件的集合，
                 * 还有一个ZipOutputStream*/
                System.out.println("sss:"+Common.files.toString());
                zipFile(Common.files, zipOut);
                zipOut.close();
                fous.close();
                Common.files.clear();
                downloadZip(file, response);
            }else{
                response.getWriter().write(flag);
            }
        } catch (Exception e) {
            e.printStackTrace();
//            ;
        }
        /**直到文件的打包已经成功了，
         * 文件的打包过程被我封装在FileUtil.zipFile这个静态方法中，
         * 稍后会呈现出来，接下来的就是往客户端写数据了*/
//        return response;
    }

    public static void zipFile(List files, ZipOutputStream outputStream) {
        int size = files.size();
        for (int i = 0; i < size; i++) {
            File file = (File) files.get(i);
            zipFile(file, outputStream);
        }
    }

    public static HttpServletResponse downloadZip(File file, HttpServletResponse response) {
        try {
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(file.getPath()));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();

            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");

//如果输出的是中文名的文件，在此处就要用URLEncoder.encode方法进行处理
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                File f = new File(file.getPath());
                f.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    /**
     * 根据输入的文件与输出流对文件进行打包
     */
    public static void zipFile(File inputFile, ZipOutputStream ouputStream) {
        try {
            if (inputFile.exists()) {
                /**如果是目录的话这里是不采取操作的，
                 * 至于目录的打包正在研究中*/
                if (inputFile.isFile()) {
                    FileInputStream IN = new FileInputStream(inputFile);
                    BufferedInputStream bins = new BufferedInputStream(IN, 512);
                    //org.apache.tools.zip.ZipEntry
                    ZipEntry entry = new ZipEntry(inputFile.getName());
                    ouputStream.putNextEntry(entry);
                    // 向压缩文件中输出数据
                    int nNumber;
                    byte[] buffer = new byte[512];
                    while ((nNumber = bins.read(buffer)) != -1) {
                        ouputStream.write(buffer, 0, nNumber);
                    }
                    // 关闭创建的流对象
                    bins.close();
                    IN.close();
                } else {
                    try {
                        File[] files = inputFile.listFiles();
                        for (int i = 0; i < files.length; i++) {
                            zipFile(files[i], ouputStream);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
