package com.cqp.shiroblog.modules.shiro.service.impl;

import com.cqp.shiroblog.modules.shiro.entity.Permission;
import com.cqp.shiroblog.modules.shiro.entity.Role;
import com.cqp.shiroblog.modules.shiro.entity.User;
import com.cqp.shiroblog.modules.shiro.mapper.UserMapper;
import com.cqp.shiroblog.modules.shiro.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenqipeng
 * @since 2021-03-04
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public User findUserByUserId(Integer userId) {
        return userMapper.findUserByUserId(userId);
    }

    @Override
    public User findRolesByUserId(Integer userId) {
        return userMapper.findRolesByUserId(userId);
    }

    @Override
    public Role findPermissionByRoleId(Integer roleId) {
        return userMapper.findPermissionByRoleId(roleId);
    }

}
