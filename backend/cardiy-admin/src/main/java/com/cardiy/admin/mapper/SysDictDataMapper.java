package com.cardiy.admin.mapper;

import com.cardiy.admin.domain.SysDictData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 字典数据 数据层
 */
@Repository
public interface SysDictDataMapper extends MongoRepository<SysDictData, String> {
    
    /**
     * 根据业务ID查询字典数据
     */
    SysDictData findByDictCode(String dictCode);
    
    /**
     * 根据字典类型查询
     */
    List<SysDictData> findByDictTypeOrderByDictSortAsc(String dictType);
    
    /**
     * 根据字典类型和状态查询
     */
    List<SysDictData> findByDictTypeAndStatusOrderByDictSortAsc(String dictType, String status);
}

