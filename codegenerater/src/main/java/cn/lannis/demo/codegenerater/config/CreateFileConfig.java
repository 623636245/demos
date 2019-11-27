package cn.lannis.demo.codegenerater.config;
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

import cn.lannis.demo.codegenerater.properties.CodeConfigProperties;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @package: cn.lannis.demo.codegenerater.config
 * @program: demos
 * @description:
 * @author: LuBangTao
 * @create: 2019-11-14 13:24
 **/
@Slf4j
public class CreateFileConfig {
    private List<File> templateRootDirs = new ArrayList<File>();
    private String stylePath;

    private void setTemplateRootDir(File templateRootDir) {
        setTemplateRootDirs(new File[] { templateRootDir });
    }

    private void setTemplateRootDirs(File... templateRootDirs) {
        this.templateRootDirs = Arrays.asList(templateRootDirs);
    }

    public String getStylePath() {
        return this.stylePath;
    }

    public void setStylePath(String stylePath) {
        this.stylePath = stylePath;
    }

    public List<File> getTemplateRootDirs() {
        String classpath = getClass().getResource(CodeConfigProperties.templatePath).getFile();
        classpath = classpath.replaceAll("%20", " ");
        log.debug("-------classpath-------" + classpath);
        setTemplateRootDir(new File(classpath));
        return this.templateRootDirs;
    }

    public void setTemplateRootDirs(List<File> templateRootDirs) {
        this.templateRootDirs = templateRootDirs;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{\"templateRootDirs\":\"");
        builder.append(this.templateRootDirs);
        builder.append("\",\"stylePath\":\"");
        builder.append(this.stylePath);
        builder.append("\"} ");
        return builder.toString();
    }
}
