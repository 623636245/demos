package cn.lannis.demo.codegenerater.properties;
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

import java.util.ResourceBundle;

/**
 * @package: cn.lannis.demo.codegenerater.properties
 * @program: demos
 * @description:
 * @author: LuBangTao
 * @create: 2019-11-14 13:37
 **/
public class CodeConfigProperties {
    private static final ResourceBundle database_bundle = ResourceBundle.getBundle("jeecg/jeecg_database");
    private static final ResourceBundle config_bundle = ResourceBundle.getBundle("jeecg/jeecg_config");
    public static String databaseType = "sqlserver";
    public static String diverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    public static String url = "jdbc:sqlserver://192.168.200.241:1433;DatabaseName=web_wechat";
    public static String username = "sa";
    public static String password = "123qwe,.";
    public static String databaseName = "web_wechat";
    public static String projectPath = "c:/workspace/jeecg";
    public static String bussiPackage = "com.jeecg";
    public static String templatePath = "/jeecg/code-template/";
    public static boolean DB_FILED_CONVERT = true;
    public static String PAGE_FIELD_REQUIRED_NUM = "4";
    public static String PAGE_FIELD_SEARCH_NUM = "3";

    static {
        diverName = getDIVER_NAME();
        url = getURL();
        username = getUSERNAME();
        password = getPASSWORD();
        databaseName = getDATABASE_NAME();
        sourceRootPackage = getSourceRootPackage();
        webRootPackage = getWebRootPackage();
        bussiPackage = getBussiPackage();
        templatePath = getTemplatePath();
        projectPath = getProject_path();

        DB_TABLE_ID = getJeecg_generate_table_id();
        DB_FILED_CONVERT = getDB_FILED_CONVERT();

        PAGE_FILTER_FIELDS = getJeecg_generate_ui_filter_fields();

        PAGE_FIELD_SEARCH_NUM = getJeecg_ui_search_filed_num();
        if ((url.indexOf("mysql") >= 0) || (url.indexOf("MYSQL") >= 0)) {
            databaseType = "mysql";
        } else if ((url.indexOf("oracle") >= 0) || (url.indexOf("ORACLE") >= 0)) {
            databaseType = "oracle";
        } else if ((url.indexOf("postgresql") >= 0) || (url.indexOf("POSTGRESQL") >= 0)) {
            databaseType = "postgresql";
        } else if ((url.indexOf("sqlserver") >= 0) || (url.indexOf("sqlserver") >= 0)) {
            databaseType = "sqlserver";
        }
    }
    public static String sourceRootPackage = CodeConfigProperties.sourceRootPackage.replace(".", "/");
    public static String webRootPackage = CodeConfigProperties.webRootPackage.replace(".", "/");
    public static String DB_TABLE_ID;
    public static String PAGE_FILTER_FIELDS;

    public static final String getDIVER_NAME() {
        return database_bundle.getString("diver_name");
    }

    public static final String getURL() {
        return database_bundle.getString("url");
    }

    public static final String getUSERNAME() {
        return database_bundle.getString("username");
    }

    public static final String getPASSWORD() {
        return database_bundle.getString("password");
    }

    public static final String getDATABASE_NAME() {
        return database_bundle.getString("database_name");
    }

    public static final boolean getDB_FILED_CONVERT() {
        String s = config_bundle.getString("db_filed_convert");
        if (s.toString().equals("false")) {
            return false;
        }
        return true;
    }

    private static String getBussiPackage() {
        return config_bundle.getString("bussi_package");
    }

    private static String getTemplatePath() {
        return config_bundle.getString("templatepath");
    }

    public static final String getSourceRootPackage() {
        return config_bundle.getString("source_root_package");
    }

    public static final String getWebRootPackage() {
        return config_bundle.getString("webroot_package");
    }

    public static final String getJeecg_generate_table_id() {
        return config_bundle.getString("db_table_id");
    }

    public static final String getJeecg_generate_ui_filter_fields() {
        return config_bundle.getString("page_filter_fields");
    }

    public static final String getJeecg_ui_search_filed_num() {
        return config_bundle.getString("page_search_filed_num");
    }

    public static final String getJeecg_ui_field_required_num() {
        return config_bundle.getString("page_field_required_num");
    }

    public static String getProject_path() {
        String projp = config_bundle.getString("project_path");
        if ((projp != null) && (!"".equals(projp))) {
            projectPath = projp;
        }
        return projectPath;
    }

    public static void setProject_path(String projectPath) {
        projectPath = CodeConfigProperties.projectPath;
    }
}
