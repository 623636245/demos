package cn.lannis.demo.codegenerater.service.impl;
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

import cn.lannis.demo.codegenerater.config.CreateFileConfig;
import cn.lannis.demo.codegenerater.constants.Common;
import cn.lannis.demo.codegenerater.properties.CodeConfigProperties;
import cn.lannis.demo.codegenerater.utils.FileHelper;
import cn.lannis.demo.codegenerater.utils.FreemarkerHelper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @package: cn.lannis.demo.codegenerater.service.impl
 * @program: demos
 * @description:
 * @author: LuBangTao
 * @create: 2019-11-14 13:23
 **/
@Slf4j
public class BaseCodeGenerate {
    protected static String sourceEncoding = "UTF-8";

    protected void generateFileCommon(CreateFileConfig createFileConfig, String projectPath, Map<String, Object> templateData) throws Exception {
        log.info("文件生成保存路径：{}", projectPath);
        for (int i = 0; i < createFileConfig.getTemplateRootDirs().size(); i++) {
            File templateRootDir = (File) createFileConfig.getTemplateRootDirs().get(i);
            scanTemplatesAndProcess(projectPath, templateRootDir, templateData, createFileConfig);
        }
    }

    protected void scanTemplatesAndProcess(String projectPath, File templateRootDir, Map<String, Object> templateData, CreateFileConfig createFileConfig) throws Exception {
        if (templateRootDir == null) {
            throw new IllegalStateException("'templateRootDir' must be not null");
        }
        log.info("-------------------load template from templateRootDir = '" + templateRootDir.getAbsolutePath() + "' outJavaRootDir:" + new File(CodeConfigProperties.sourceRootPackage.replace(".", File.separator)).getAbsolutePath() + "' outWebappRootDir:" + new File(CodeConfigProperties.webRootPackage.replace(".", File.separator)).getAbsolutePath());
        List<?> srcFiles = FileHelper.searchAllNotIgnoreFile(templateRootDir);
        log.info("【待生成文件数量】" + srcFiles.size());
        log.info("【待生成文件列表】" + srcFiles.toString());
        for (int i = 0; i < srcFiles.size(); i++) {
            File srcFile = (File) srcFiles.get(i);
            executeGenerate(projectPath, templateRootDir, templateData, srcFile, createFileConfig);
        }
    }

    protected void executeGenerate(String projectPath, File templateRootDir, Map<String, Object> templateData, File srcFile, CreateFileConfig createFileConfig) throws Exception {
        log.debug("【当前文件临时根路径】:{}" , templateRootDir.getPath());
        log.debug("【当前文件临时路径】:{}" ,srcFile.getPath());

        String templateFile = FileHelper.getRelativePath(templateRootDir, srcFile);
        try {
            String outputFilepath = proceeForOutputFilepath(templateData, templateFile, createFileConfig);
            if (outputFilepath.startsWith("java")) {
                String path = projectPath + File.separator + CodeConfigProperties.sourceRootPackage.replace(".", File.separator);
                String soure = path;
                outputFilepath = outputFilepath.substring("java".length());
                outputFilepath = soure + outputFilepath;
                log.debug("【当前文件输出完整路径】：{}", outputFilepath);
                generateNewFileOrInsertIntoFile(templateFile, outputFilepath, templateData, createFileConfig);
            } else if (outputFilepath.startsWith("webapp")) {
                String path = projectPath + File.separator + CodeConfigProperties.webRootPackage.replace(".", File.separator);

                String soure = path;
                outputFilepath = outputFilepath.substring("webapp".length());
                outputFilepath = soure + outputFilepath;
                log.debug("-------webapp---outputFilepath---" + outputFilepath);
                generateNewFileOrInsertIntoFile(templateFile, outputFilepath, templateData, createFileConfig);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
        }
    }

    protected void generateNewFileOrInsertIntoFile(String templateFile, String outputFilepath, Map<String, Object> templateModel, CreateFileConfig createFileConfig) throws Exception {
        if (outputFilepath.endsWith("i")) {
            outputFilepath = outputFilepath.substring(0, outputFilepath.length() - 1);
        }
        Template template = getFreeMarkerTemplate(templateFile, createFileConfig);
        template.setOutputEncoding(sourceEncoding);
        File absoluteOutputFilePath = FileHelper.parentMkdir(outputFilepath);
        log.info("[最终]\t template:" + templateFile + " ==> " + outputFilepath);
        Common.files.add(new File(outputFilepath));
        FreemarkerHelper.processTemplate(template, templateModel, absoluteOutputFilePath, sourceEncoding);
        if (isCutFile(absoluteOutputFilePath)) {
            splitFile(absoluteOutputFilePath, "#segment#");
        }
    }

    protected Template getFreeMarkerTemplate(String templateName, CreateFileConfig createFileConfig) throws IOException {
        return FreemarkerHelper.newFreeMarkerConfiguration(createFileConfig.getTemplateRootDirs(), sourceEncoding, templateName).getTemplate(templateName);
    }

    protected boolean isCutFile(File file) {
        if (file.getName().startsWith("[1-n]")) {
            return true;
        }
        return false;
    }

    protected static void splitFile(File file, String splitStr) {
        InputStreamReader isr = null;
        BufferedReader br = null;
        List<Writer> flist = new ArrayList<Writer>();
        try {
            isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
            br = new BufferedReader(isr);

            boolean start = false;
            Writer targerFile = null;
            String row;
            while ((row = br.readLine()) != null) {
                if ((row.trim().length() > 0) && (row.startsWith(splitStr))) {
                    String fileName = row.substring(splitStr.length());
                    String parant = file.getParentFile().getAbsolutePath();
                    fileName = parant + File.separator + fileName;
                    log.info("[generate]\t split file:" + file.getAbsolutePath() + " ==> " + fileName);

                    targerFile = new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8");
                    flist.add(targerFile);
                    start = true;
                } else if (start) {
                    log.debug("row : " + row);
                    targerFile.append(row + "\r\n");
                }
            }
            for (int i = 0; i < flist.size(); i++) {
                ((Writer) flist.get(i)).close();
            }
            br.close();
            isr.close();

            log.info("[generate]\t delete file:" + file.getAbsolutePath());
            forceDelete(file);
            return;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (flist.size() > 0) {
                    for (int i = 0; i < flist.size(); i++) {
                        if (flist.get(i) != null) {
                            ((Writer) flist.get(i)).close();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected static String proceeForOutputFilepath(Map<String, Object> filePathModel, String templateFile, CreateFileConfig createFileConfig) throws Exception {
        String outputFilePath = templateFile;

        int testExpressionIndex = -1;
        if ((testExpressionIndex = templateFile.indexOf('@')) != -1) {
            outputFilePath = templateFile.substring(0, testExpressionIndex);
            String testExpressionKey = templateFile.substring(testExpressionIndex + 1);
            Object expressionValue = filePathModel.get(testExpressionKey);
            if (expressionValue == null) {
                System.err.println("[not-generate] WARN: test expression is null by key:[" + testExpressionKey + "] on template:[" + templateFile + "]");
                return null;
            }
            if (!"true".equals(String.valueOf(expressionValue))) {
                log.error("[not-generate]\t test expression '@" + testExpressionKey + "' is false,template:" + templateFile);
                return null;
            }
        }
        Configuration conf = FreemarkerHelper.newFreeMarkerConfiguration(createFileConfig.getTemplateRootDirs(), sourceEncoding, "/");
        outputFilePath = FreemarkerHelper.processTemplateString(outputFilePath, filePathModel, conf);
        String extName = outputFilePath.substring(outputFilePath.lastIndexOf("."));
        String fileName = outputFilePath.substring(0, outputFilePath.lastIndexOf(".")).replace(".", File.separator);
        outputFilePath = fileName + extName;
        return outputFilePath;
    }

    protected static boolean forceDelete(File f) {
        boolean result = false;
        int tryCount = 0;
        while ((!result) && (tryCount++ < 10)) {
            System.gc();
            result = f.delete();
        }
        return result;
    }
}
