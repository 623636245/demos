package com.cdyxsoft.cloud.rules.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cdyxsoft.cloud.rules.entity.User;
import com.cdyxsoft.cloud.rules.mapper.UserMapper;
import com.cdyxsoft.cloud.rules.service.IUserService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 用户信息
 * @author： LuBangTao
 * @date： 2019-11-19 08:56:59
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public void handleCreate() {
        /**
         * 添加一个用户
         */
        User user = new User();
        user.setCappid("1");
        this.save(user);
        /**
         * 添加或更新一个用户
         */
        this.saveOrUpdate(user);

        /**
         * 添加多个用户
         */
        List<User> users = new ArrayList<>();
        for (int a = 0; a < 3; a++) {
            User temp = new User();
            temp.setCappid(a + "");
            users.add(temp);
        }
        this.saveBatch(users);
        /**
         * 添加或更新多个用户
         */
        this.saveOrUpdateBatch(users);
    }

    @Override
    public void handleDelete() {
        /**
         * 删除CBM为1的用户
         */
        this.removeById("1");

        /**
         * 删除CBM为1的用户
         */
        QueryWrapper qw = new QueryWrapper();
        qw.eq("CBM", "1");
        this.remove(qw);
        /**
         * 删除CBM为 1 和 2 的用户
         */
        List<String> ids = new ArrayList<>();
        ids.add("1");
        ids.add("2");
        this.removeByIds(ids);
    }

    @Override
    public void handleUpdate() {
        /**
         * 更新ID为1的用户
         */
        User user = new User();
        user.setCappid("1");
        this.updateById(user);
        /**
         * 没有则保存，有则更新
         */
        this.saveOrUpdate(user);
        /**
         * 更新CBM为1且CMC为张三的用户
         */
        QueryWrapper qw = new QueryWrapper();
        qw.eq("CBM", "1");
        qw.eq("CMC", "张三");
        this.update(user, qw);
        /**
         * 通过ID更新多个用户
         */
        List<User> users = new ArrayList<>();
        for (int a = 0; a < 3; a++) {
            User temp = new User();
            temp.setCappid(a + "");
            users.add(temp);
        }
        this.updateBatchById(users);
        /**
         * 添加或更新多个用户
         */
        this.saveOrUpdateBatch(users);

    }

    @Override
    public void handleRetrieve() {
        /**
         * 获取CBM为1的用户
         */
        User user = this.getById("1");
        /**
         * 条件查询编码为1和名称为张三的用户
         */
        QueryWrapper qw = new QueryWrapper();
        qw.eq("CBM","1");
        qw.eq("CMC","张三");
        List<User> users1 = this.list(qw);
        /**
         * 查询所有用户数据
         */
        List<User> users2 = this.list();

        /**
         * 查询满足的CBM
         */
        List<String> ids = new ArrayList<>();
        ids.add("1");
        ids.add("2");
        this.listByIds(ids);

        /**
         * 查询记录数
         */
        int count = this.count();
        /**
         * 条件查询记录数
         */
        QueryWrapper qwCount = new QueryWrapper();
        qwCount.eq("CBM","1");
        int count2 = this.count(qwCount);
    }
}
