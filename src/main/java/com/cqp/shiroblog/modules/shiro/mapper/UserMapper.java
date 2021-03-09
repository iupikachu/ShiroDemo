package com.cqp.shiroblog.modules.shiro.mapper;

import com.cqp.shiroblog.modules.shiro.entity.Role;
import com.cqp.shiroblog.modules.shiro.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chenqipeng
 * @since 2021-03-04
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
     // 根据id 查询用户
     User findUserByUserId(Integer userId);
     // 根据用户名查找用户
     User findByUserName(String UserName);
     // 保存用户
     void save (User user);

     // 根据用户id查询用户的所有角色
     User findRolesByUserId(Integer userId);
     // 根据角色id获得权限集合
     Role findPermissionByRoleId(Integer roleId);

}
