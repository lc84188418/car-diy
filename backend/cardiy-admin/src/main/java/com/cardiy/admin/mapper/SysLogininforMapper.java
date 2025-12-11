package com.cardiy.admin.mapper;

import com.cardiy.admin.domain.SysLogininfor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * 系统访问日志 数据层
 */
@Repository
public interface SysLogininforMapper extends MongoRepository<SysLogininfor, String> {
    
    /**
     * 根据业务ID查询登录日志
     */
    SysLogininfor findByInfoId(Long infoId);
    
    /**
     * 根据登录时间范围查询
     */
    Page<SysLogininfor> findByLoginTimeBetween(LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);
}

