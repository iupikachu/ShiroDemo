package com.cqp.shiroblog;

import com.cqp.shiroblog.modules.shiro.entity.User;
import com.cqp.shiroblog.modules.shiro.mapper.UserMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShiroBlogApplicationTests {
    @Autowired
    UserMapper userMapper;
    @Test
    void contextLoads() {

        User user= userMapper.findRolesByUserId(5);
        System.out.println(user.toString());
    }

}
