package com.cardiy.admin.service;

import com.cardiy.admin.domain.SysDictType;

import java.util.List;
import java.util.Optional;

/**
 * 字典类型 业务层
 */
public interface ISysDictTypeService {
    
    SysDictType save(SysDictType dictType);
    
    Optional<SysDictType> findById(String dictId);
    
    List<SysDictType> findAll();
    
    List<SysDictType> findByStatus(String status);
    
    void deleteById(String dictId);
    
    void deleteAllById(List<String> dictIds);
}

