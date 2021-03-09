package com.cqp.shiroblog.modules.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

import java.util.Collection;
import java.util.Set;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName RedisCacheManager.java
 * @Description 自定义 redis 缓存的实现
 * @createTime 2021年03月09日 10:19:00
 */
public class RedisCacheManager<k,v> implements CacheManager {


    @Override
    public <K, V> Cache<K, V> getCache(String cacheName) throws CacheException {
        System.out.println("cacheName: "+cacheName);
        return new RedisCache<K,V>(cacheName);
    }
}
