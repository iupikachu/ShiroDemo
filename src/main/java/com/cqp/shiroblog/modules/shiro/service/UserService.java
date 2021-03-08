package com.cqp.shiroblog.modules.shiro.service;

import com.cqp.shiroblog.modules.shiro.entity.Permission;
import com.cqp.shiroblog.modules.shiro.entity.Role;
import com.cqp.shiroblog.modules.shiro.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenqipeng
 * @since 2021-03-04
 */
public interface UserService extends IService<User> {



    // 根据用户id查询用户的所有角色
    User findRolesByUserId(Integer userId);
    // 根据角色id查询权限集合
    Role findPermissionByRoleId(Integer roleId);

}
