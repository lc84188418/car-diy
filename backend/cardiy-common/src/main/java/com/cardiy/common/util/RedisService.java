package com.cardiy.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * redis工具类
 * @author liuchun
 * @className RedisService.java
 * @date 2021:10:10 23:09
 * @since version 1.0.0
 */
@SuppressWarnings(value = {"unchecked", "rawtypes"})
@Component
public class RedisService {
    @Autowired
    public RedisTemplate redisTemplate;

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key   缓存的键值
     * @param value 缓存的值
     */
    public <T> void set(final String key, final T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key      缓存的键值
     * @param value    缓存的值
     * @param timeout  时间
     * @param timeUnit 时间颗粒度
     */
    public <T> void set(final String key, final T value, final Long timeout, final TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public <T> T get(final String key) {
        return (T) redisTemplate.opsForValue().get(key);
    }


    /**
     * 缓存List数据
     *
     * @param key      缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    public <T> long setList(final String key, final List<T> dataList) {
        Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
        return count == null ? 0 : count;
    }

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public <T> List<T> getList(final String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }


    /**
     * 缓存Set
     *
     * @param key     缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    public <T> BoundSetOperations<String, T> setSet(final String key, final Set<T> dataSet) {
        BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);
        Iterator<T> it = dataSet.iterator();
        while (it.hasNext()) {
            setOperation.add(it.next());
        }
        return setOperation;
    }

    /**
     * 获得缓存的set
     *
     * @param key
     * @return
     */
    public <T> Set<T> getSet(final String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * @param [key, values]
     * @return java.lang.Long
     * @author liuchun
     * 移除set元素
     * @date 14:58 2026/1/4
     * @version v2.4.0
     **/
    public Long removeSet(final String key, final Object... values) {
        return redisTemplate.opsForSet().remove(key, values);
    }

    /**
     * @param [key, value, score]
     * @return java.lang.Boolean
     * @author liuchun
     * 添加zset元素
     * @date 15:20 2026/1/4
     * @version v2.4.0
     **/
    public Boolean addZSet(final String key, final Object value, double score) {
        return redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * @param [key, value]
     * @return java.lang.Double
     * @author liuchun
     * 获取zset元素分数
     * @date 15:19 2026/1/4
     * @version v2.4.0
     **/
    public Double scoreZSet(final String key, final Object value) {
        return redisTemplate.opsForZSet().score(key, value);
    }

    /**
     * @param [key]
     * @return java.util.Set<T>
     * @author liuchun
     * 正序获取zset元素
     * @date 15:19 2026/1/4
     * @version v2.4.0
     **/
    public <T> Set<T> rangeZSet(final String key) {
        return redisTemplate.opsForZSet().range(key, 0, -1);
    }

    /**
     * @param [key]
     * @return java.util.Set<T>
     * @author liuchun
     * 逆序获取zset元素
     * @date 15:19 2026/1/4
     * @version v2.4.0
     **/
    public <T> Set<T> reverseRangeZSet(final String key) {
        return redisTemplate.opsForZSet().reverseRange(key, 0, -1);
    }

    /**
     * @param [key, min, max]
     * @return java.util.Set<T>
     * @author liuchun
     * 按分数范围获取zset元素
     * @date 15:18 2026/1/4
     * @version v2.4.0
     **/
    public <T> Set<T> rangeByScoreZSet(final String key, final double min, final double max) {
        return redisTemplate.opsForZSet().rangeByScore(key, min, max);
    }

    /**
     * 移除zset元素
     *
     * @param [key, values]
     * @return java.lang.Long
     * @author liuchun
     * @date 15:18 2026/1/4
     * @version v2.4.0
     **/
    public Long removeZSet(final String key, final Object... values) {
        return redisTemplate.opsForZSet().remove(key, values);
    }

    /**
     * 缓存hash
     *
     * @param key
     * @param value
     */
    public void hSet(final String key, final Object hashKey, final Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * 获取hash
     *
     * @param [key, hashKey]
     * @return T
     * @author liuchun
     * @date 15:29 2026/1/4
     * @version v2.4.0
     **/
    public <T> T hGet(final String key, final Object hashKey) {
        return (T) redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 获取多个Hashkey的数据
     *
     * @param key   Redis键
     * @param hKeys Hash键集合
     * @return Hash对象集合
     */
    public <T> List<T> hmGet(final String key, final Collection<Object> hKeys) {
        return redisTemplate.opsForHash().multiGet(key, hKeys);
    }

    /**
     * 缓存Map
     *
     * @param key
     * @param dataMap
     */
    public <T> void setMap(final String key, final Map<String, T> dataMap) {
        if (dataMap != null) {
            redisTemplate.opsForHash().putAll(key, dataMap);
        }
    }

    /**
     * 获得缓存的Map
     *
     * @param key
     * @return
     */
    public <T> Map<String, T> getMap(final String key) {
        return redisTemplate.opsForHash().entries(key);
    }


    /**
     * 删除hash字段
     *
     * @param [key, hKeys]
     * @return java.lang.Long
     * @author liuchun
     * @date 15:33 2026/1/4
     * @version v2.4.0
     **/
    public Long hDelete(final String key, final Object... hKeys) {
        return redisTemplate.opsForHash().delete(key, hKeys);
    }


    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public Collection<String> keys(final String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 是否存在key
     *
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 设置有效时间，单位：秒
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @param unit    时间单位
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout, final TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 删除单个key
     * 具体的某一个key
     *
     * @param key
     */
    public boolean delete(final String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 删除带有某前缀的所有key
     *
     * @param keyPrefix 多个对象
     * @return
     */
    public boolean deletePrefix(String keyPrefix) {
        Set<String> keys = redisTemplate.keys(keyPrefix + "*");
        Long deleteNum = redisTemplate.delete(keys);
        return deleteNum > 0;
    }

    /**
     * 删除集合key
     *
     * @param collection 多个对象
     * @return
     */
    public long delete(final Collection collection) {
        return redisTemplate.delete(collection);
    }

    /**
     * 获得自增键,并放入redis中
     *
     * @param key
     * @return
     */
    public Long getIncreaseNum(final String key) {
        assert redisTemplate.getConnectionFactory() != null;
        RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        return entityIdCounter.getAndIncrement();
    }

}