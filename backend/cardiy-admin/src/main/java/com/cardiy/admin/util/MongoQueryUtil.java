package com.cardiy.admin.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.regex.Pattern;

/**
 * MongoDB 查询工具类
 */
public class MongoQueryUtil {
    
    /**
     * 构建模糊查询条件
     */
    public static Criteria buildLikeCriteria(String field, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        // MongoDB 模糊查询使用正则表达式，不区分大小写
        Pattern pattern = Pattern.compile(".*" + Pattern.quote(value) + ".*", Pattern.CASE_INSENSITIVE);
        return Criteria.where(field).regex(pattern);
    }
    
    /**
     * 构建精确匹配条件
     */
    public static Criteria buildEqualCriteria(String field, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return Criteria.where(field).is(value);
    }
    
    /**
     * 组合多个条件（AND）
     */
    public static Criteria combineCriteria(List<Criteria> criteriaList) {
        if (criteriaList == null || criteriaList.isEmpty()) {
            return new Criteria();
        }
        if (criteriaList.size() == 1) {
            return criteriaList.get(0);
        }
        return new Criteria().andOperator(criteriaList.toArray(new Criteria[0]));
    }
    
    /**
     * 执行查询并分页
     */
    public static <T> Page<T> queryWithPage(MongoTemplate mongoTemplate, Query query, Class<T> entityClass, Pageable pageable) {
        // 查询总数
        long total = mongoTemplate.count(query, entityClass);
        
        // 分页查询
        query.with(pageable);
        List<T> content = mongoTemplate.find(query, entityClass);
        
        return new PageImpl<>(content, pageable, total);
    }
}

