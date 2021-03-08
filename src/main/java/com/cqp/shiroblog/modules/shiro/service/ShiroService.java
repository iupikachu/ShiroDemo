package com.cqp.shiroblog.modules.shiro.service;

import com.cqp.shiroblog.modules.shiro.entity.User;


public interface ShiroService {
    /**
     * Find user by username
     */
    
    User findByUsername(String username);






    /**
     * find user by userid
     */
    User findByUserId(Integer userId);

    /**
     * 注册
     */
    public void register(User user);
}
