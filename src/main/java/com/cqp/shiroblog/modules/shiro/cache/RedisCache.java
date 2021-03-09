package com.cqp.shiroblog.modules.shiro.cache;

import com.cqp.shiroblog.common.utils.ApplicationContextUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Set;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName RedisCache.java
 * @Description 自定义缓存的实现
 * @createTime 2021年03月09日 10:26:00
 */
public class RedisCache<k,v> implements Cache<k,v> {
    private String cacheName;
    public RedisCache() {
    }

    public RedisCache(String cacheName) {
        this.cacheName = cacheName;
    }

    private RedisTemplate getRedisTemplate(){
        RedisTemplate redisTemplate = (RedisTemplate) ApplicationContextUtils.getBean("redisTemplate");
        // RedisTemplate默认对对象进行操作  k是字符串 v就是对象 所以改变redisTemplte k的序列化方式 改为String的序列化方式
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // Redis 的 Hash 结构   Map<String,Map<String, Object>>;
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }


    @Override
    // 从redis中获取缓存
    public v get(k k) throws CacheException {
        System.out.println("redis get key:"+k);
        return (v) getRedisTemplate().opsForHash().get(this.cacheName,k.toString());
    }

    @Override
    // 把查询数据库的东西放入缓存
    public v put(k k, v v) throws CacheException {
        System.out.println("redis put key:"+k);
        System.out.println("redis put value:"+v);
        getRedisTemplate().opsForHash().put(this.cacheName,k.toString(),v);
        return null;
    }

    @Override
    // 清空指定的缓存
    public v remove(k k) throws CacheException {
        System.out.println("===remove===");
        return (v)getRedisTemplate().opsForHash().delete(this.cacheName,k.toString());
    }

    @Override
    // 清空缓存
    public void clear() throws CacheException {
        System.out.println("===clear===");
        getRedisTemplate().delete(this.cacheName);
    }

    @Override
    public int size() {
        return getRedisTemplate().opsForHash().size(this.cacheName).intValue();
    }

    @Override
    public Set<k> keys() {
        return getRedisTemplate().opsForHash().keys(this.cacheName);
    }

    @Override
    public Collection<v> values() {
        return getRedisTemplate().opsForHash().values(this.cacheName);
    }
}
