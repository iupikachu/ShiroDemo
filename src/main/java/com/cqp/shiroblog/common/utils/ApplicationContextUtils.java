package com.cqp.shiroblog.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName ApplicationContextUtils.java
 * @Description TODO
 * @createTime 2021年03月09日 11:07:00
 */
@Component
public class ApplicationContextUtils implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;  // 拿到工厂
    }

    // 根据bean名字获取工厂中指定的 bean对象
    public static Object getBean(String beanName){
        return context.getBean(beanName);
    }

}