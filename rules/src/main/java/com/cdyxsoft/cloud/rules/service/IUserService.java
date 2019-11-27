package com.cdyxsoft.cloud.rules.service;

import com.cdyxsoft.cloud.rules.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @description: 用户信息
 * @author: LuBangTao
 * @date：   2019-11-19 14:43:04
 **/
public interface IUserService extends IService<User> {

    /**
     * 添加用户接口
     */
    void handleCreate();

    /**
     * 删除用户方法
     */
    void handleDelete();

    /**
     * 更新用户方法
     */
    void handleUpdate();

    /**
     * 添加用户方法
     */
    void handleRetrieve();
}
