package com.cqp.shiroblog.common.utils;

import org.apache.shiro.crypto.hash.Md5Hash;

import java.util.Random;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName SaltUtils.java
 * @Description 盐生成器
 * @createTime 2021年03月06日 10:25:00
 */
public class SaltUtils {
    public static String getSalt (int n){
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$^&*()_+".toCharArray();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < n ; i++){
            char aChar = chars[new Random().nextInt(chars.length)];
            sb.append(aChar);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
//        String salt = getSalt(8);
//        System.out.println(salt);
          String salt ="nn#EYhS$";
        Md5Hash md5Hash = new Md5Hash("123456",salt,1024);
        System.out.println(md5Hash.toHex());

    }
}
