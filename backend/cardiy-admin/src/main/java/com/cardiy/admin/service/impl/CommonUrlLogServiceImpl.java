package com.cardiy.admin.service.impl;

import com.cardiy.admin.domain.CommonUrlLogEntity;
import com.cardiy.admin.mapper.CommonUrlLogMapper;
import com.cardiy.admin.service.CommonUrlLogService;
import com.cardiy.common.constant.RedisConstant;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Set;

/**
 * @author liuchun
 * @version v2.4.0
 * @className CommonUrlLogServiceImpl
 * @desc TODO
 * @createTime 2026/1/15 14:17
 */
@Slf4j
@Service
public class CommonUrlLogServiceImpl implements CommonUrlLogService {
    @Resource
    private CommonUrlLogMapper commonUrlLogMapper;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Resource
    private RedissonClient redissonClient;

    /**
     * @desc:批量更新数量
     * @author: liuchun
     * @createTime: 2024/9/23 1:43
     * @param: [set]
     * @version: V1.29.0
     * @return: void
     **/
    @Override
    public void updateBatch(Set<CommonUrlLogEntity> set) {
        for (CommonUrlLogEntity entity : set) {
            //分布式锁，防止并发安全
            String redisKey = MessageFormat.format(RedisConstant.DL_USER_URL_LOG_KEY, entity.getMethodName(), entity.getUrl());
            RLock lock = redissonClient.getLock(redisKey);
            lock.lock();
            try {
                //先查询是否有记录，有则更新，没有则新增
                //id=null, url=/CardiyAdmin/api/admin/system/dept/list, methodName=GET, day=20260115, month=202601, year=2026, count=1, isStandard=1, isInner=1)
                //url,methodName,day 唯一确定一条记录
                Criteria criteria = new Criteria();
                criteria.and("url").is(entity.getUrl());
                criteria.and("methodName").is(entity.getMethodName());
                criteria.and("day").is(entity.getDay());
                Query query = new Query(criteria);
                if (mongoTemplate.exists(query, CommonUrlLogEntity.class)) {
                    // 构建更新操作：$inc 操作符用于字段递增
                    Update update = new Update().inc("count", entity.getCount());
                    mongoTemplate.updateFirst(query, update, CommonUrlLogEntity.class);
                } else {
                    //新增
                    commonUrlLogMapper.insert(entity);
                }
            } catch (Exception e) {
                log.error("CommonUrlLogService.updateBatch error:{}", e.getMessage(), e);
            } finally {
                lock.unlock();
            }

        }
    }
}
