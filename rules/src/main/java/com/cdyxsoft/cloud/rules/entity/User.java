package com.cdyxsoft.cloud.rules.entity;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: 机构信息
 * @author： LuBangTao
 * @date：   2019-11-19 14:43:04
 */
@Data
@TableName("TBJG")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**唯一编码*/
	@TableId("CBM")
	private String cbm;
	/**商户/用户名称*/
	private String cmc;
	/**商户/用户地址*/
	private String cdz;
	/**微信APPID*/
	private String cappid;
	/**微信AppSecret*/
	private String cappsecret;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date ccjsj;
	/**更新时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date cgxsj;
	/**是否有效：
1：有效【默认】
0：无效*/
	private Integer izt;
}
