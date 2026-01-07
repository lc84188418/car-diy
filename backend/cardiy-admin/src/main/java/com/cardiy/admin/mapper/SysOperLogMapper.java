package com.cardiy.admin.mapper;

import com.cardiy.admin.domain.SysOperLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * 操作日志 数据层
 */
@Repository
public interface SysOperLogMapper extends MongoRepository<SysOperLog, String> {
    
    /**
     * 根据业务ID查询操作日志
     */
    SysOperLog findByOperId(String operId);
    
    /**
     * 根据操作时间范围查询
     */
    Page<SysOperLog> findByTimeBetween(Date startTime, Date endTime, Pageable pageable);
}

