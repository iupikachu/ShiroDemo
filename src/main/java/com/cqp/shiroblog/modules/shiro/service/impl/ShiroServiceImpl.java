package com.cqp.shiroblog.modules.shiro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cqp.shiroblog.common.utils.SaltUtils;
import com.cqp.shiroblog.modules.shiro.entity.User;
import com.cqp.shiroblog.modules.shiro.mapper.UserMapper;
import com.cqp.shiroblog.modules.shiro.service.ShiroService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName ShiroServiceImpl.java
 * @Description TODO
 * @createTime 2021年03月04日 14:15:00
 */
@Service
public class ShiroServiceImpl implements ShiroService {
    @Autowired
    UserMapper userMapper;

    @Override
    public User findByUsername(String username) {
        User user = userMapper.findByUserName(username);
        return user;
    }

    /**
     * 根据 userid 找到用户
     * @param userId
     * @return
     */
    @Override
    public User findByUserId(Integer userId) {
        return userMapper.selectById(userId);
    }

    /**
     * 密码+md5+salt 数据库保存salt
     * @param user
     */
    @Override
    public void register(User user) {
     // 明文密码进行 md5 + salt + hash散列
        //1. 生成盐(8位)
        String salt = SaltUtils.getSalt(8);
        //2. 将随机盐保存到数据库
        user.setSalt(salt);
       //3. 明文密码进行 md5 salt hash
        Md5Hash md5Hash = new Md5Hash(user.getPassword(), salt, 1024);
        user.setPassword(md5Hash.toHex());
        userMapper.save(user);
    }
}
