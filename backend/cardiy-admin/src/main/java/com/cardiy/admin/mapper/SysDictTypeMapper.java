package com.cardiy.admin.mapper;

import com.cardiy.admin.domain.SysDictType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 字典类型 数据层
 */
@Repository
public interface SysDictTypeMapper extends MongoRepository<SysDictType, String> {
    
    /**
     * 根据业务ID查询字典类型
     */
    SysDictType findByDictId(String dictId);
    
    /**
     * 根据字典类型查询
     */
    SysDictType findByDictType(String dictType);
    
    /**
     * 根据状态查询字典类型
     */
    List<SysDictType> findByStatusOrderByDictIdAsc(String status);
}

