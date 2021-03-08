package com.cqp.shiroblog.config;

import com.cqp.shiroblog.modules.shiro.auth.AuthFilter;
import com.cqp.shiroblog.modules.shiro.auth.CustomerRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName ShiroConfig.java
 * @Description shiro配置类 把自定义的 AuthFilter AuthRealm 注入
 * @createTime 2021年03月05日 14:33:00
 */
@Component
public class ShiroConfig {
    @Bean("securityManager")
    public SecurityManager securityManager(@Qualifier("getRealm") Realm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        securityManager.setRememberMeManager(null);
        return securityManager;
    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        //auth过滤
        Map<String, Filter> filters = new HashMap<>();
        // 设置过滤
        filters.put("auth", new AuthFilter());

        shiroFilter.setFilters(filters);
        Map<String, String> filterMap = new LinkedHashMap<>();
        // anno匿名访问  auth验证
        filterMap.put("/webjars/**", "anon");
        filterMap.put("/druid/**", "anon");
        filterMap.put("/sys/login", "anon");
        filterMap.put("/sys/register","anon");
        filterMap.put("/swagger/**", "anon");
        filterMap.put("/v2/api-docs", "anon");
        filterMap.put("/swagger-ui.html", "anon");
        filterMap.put("/swagger-resources/**", "anon");
        filterMap.put("/doc.html", "anon");
        // 除了以上路径，其他都需要权限验证
        //过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        filterMap.put("/**", "auth");// 主要通过注解方式校验权限
        shiroFilter.setFilterChainDefinitionMap(filterMap);
        return shiroFilter;
    }

    // 创建自定义realm
    @Bean
    public Realm getRealm(){
        CustomerRealm customerRealm = new CustomerRealm();
        return customerRealm;
    }

    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    // 开启注解代理（默认好像已经开启，可以不要）
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
    @Bean
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        return creator;
    }




}
