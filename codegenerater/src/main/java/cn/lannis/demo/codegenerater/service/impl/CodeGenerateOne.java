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
import cn.lannis.demo.codegenerater.entity.ColumnVo;
import cn.lannis.demo.codegenerater.entity.TableVo;
import cn.lannis.demo.codegenerater.properties.CodeConfigProperties;
import cn.lannis.demo.codegenerater.utils.DbReadTableUtil;
import cn.lannis.demo.codegenerater.utils.NonceUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @package: cn.lannis.demo.codegenerater.service.impl
 * @program: demos
 * @description: 代码生成1
 * @author: LuBangTao
 * @create: 2019-11-14 13:21
 **/
@Slf4j
public class CodeGenerateOne extends BaseCodeGenerate implements IGenerate{
    private TableVo tableVo;

    public CodeGenerateOne(TableVo tableVo) {
        this.tableVo = tableVo;
    }

    public Map<String, Object> initData() throws Exception{
        log.info("【初始化数据】");
        List<ColumnVo> columns = new ArrayList<ColumnVo>();
        List<ColumnVo> originalColumns = new ArrayList<ColumnVo>();
        Map<String, Object> data = new HashMap<String, Object>();
        log.info("当前包名：{}",CodeConfigProperties.bussiPackage);
        data.put("bussiPackage", CodeConfigProperties.bussiPackage);
        log.info("当前项目名：{}",this.tableVo.getEntityPackage());
        data.put("entityPackage", this.tableVo.getEntityPackage());
        log.info("当前实体名：{}",this.tableVo.getEntityName());
        data.put("entityName", this.tableVo.getEntityName());
        log.info("当前表名：{}",this.tableVo.getTableName());
        data.put("tableName", this.tableVo.getTableName());
        log.info("当前tableId：{}",CodeConfigProperties.DB_TABLE_ID);
        data.put("tableId", CodeConfigProperties.DB_TABLE_ID);
        this.tableVo.setFieldRequiredNum(StringUtils.isNotEmpty(CodeConfigProperties.PAGE_FIELD_REQUIRED_NUM) ? Integer.parseInt(CodeConfigProperties.PAGE_FIELD_REQUIRED_NUM) : -1);
        this.tableVo.setSearchFieldNum(StringUtils.isNotEmpty(CodeConfigProperties.PAGE_FIELD_SEARCH_NUM) ? Integer.parseInt(CodeConfigProperties.PAGE_FIELD_SEARCH_NUM) : -1);
        log.info("当前表实体类：{}",this.tableVo);
        data.put("tableVo", this.tableVo);
        columns = DbReadTableUtil.readTableColumn(this.tableVo.getTableName());
        data.put("columns", columns);
        originalColumns = DbReadTableUtil.readOriginalTableColumn(this.tableVo.getTableName());
        data.put("originalColumns", originalColumns);
        for (ColumnVo c : originalColumns) {
            if (c.getFieldName().toLowerCase().equals(CodeConfigProperties.DB_TABLE_ID.toLowerCase())) {
                data.put("primary_key_type", c.getFieldType());
            }
        }
        long serialVersionUID = NonceUtils.randomLong() + NonceUtils.currentMills();
        data.put("serialVersionUID", String.valueOf(serialVersionUID));
        log.info("code template data: " + data.toString());
        log.info("【初始化数据完成】");
        return data;
    }

    public String generateCodeFile(){
        try {
        log.info("开始执行代码生成流程---[表名:" + this.tableVo.getTableName() + "]");
        String projectPath = CodeConfigProperties.projectPath;
        Map<String, Object> templateData = initData();
        CreateFileConfig createFileConfig = new CreateFileConfig();
            if(templateData!=null){
                generateFileCommon(createFileConfig, projectPath, templateData);
            }else {
                return "0";
            };
        } catch (Exception e) {
            return e.getMessage();
        }
        log.info("【代码生成完成】");
        return "1";
    }

    public static void main(String[] args) {
        TableVo table = new TableVo();
        //设置表名
        table.setTableName("TBTJYYDR");
        table.setPrimaryKeyPolicy("uuid");
        //项目名称
        table.setEntityPackage("wechatsysmanager");
        //实体名称
        table.setEntityName("ExamAppoint");
        //描述，包括Controller,Service,ServiceImpl,Mapper
        table.setFtlDescription("体检预约(Appointment)");
        new CodeGenerateOne(table).generateCodeFile();
        System.out.println("结束");
    }
}
