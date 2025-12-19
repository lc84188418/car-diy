package com.cardiy.admin.service.impl;

import cn.hutool.core.util.IdUtil;
import com.cardiy.admin.domain.SysDictType;
import com.cardiy.admin.mapper.SysDictTypeMapper;
import com.cardiy.admin.service.ISysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 字典类型 业务层处理
 */
@Service
public class SysDictTypeServiceImpl implements ISysDictTypeService {
    
    @Autowired
    private SysDictTypeMapper dictTypeMapper;
    
    @Override
    public SysDictType save(SysDictType dictType) {
        if(Objects.isNull(dictType.getDictId())){
            dictType.setDictId(IdUtil.getSnowflakeNextIdStr());
        }
        return dictTypeMapper.save(dictType);
    }
    
    @Override
    public Optional<SysDictType> findById(String dictId) {
        SysDictType dictType = dictTypeMapper.findByDictId(dictId);
        return Optional.ofNullable(dictType);
    }
    
    @Override
    public List<SysDictType> findAll() {
        return dictTypeMapper.findAll();
    }
    
    @Override
    public List<SysDictType> findByStatus(String status) {
        return dictTypeMapper.findByStatusOrderByDictIdAsc(status);
    }
    
    @Override
    public void deleteById(String dictId) {
        SysDictType dictType = dictTypeMapper.findByDictId(dictId);
        if (dictType != null && dictType.getId() != null) {
            dictTypeMapper.deleteById(dictType.getId());
        }
    }
    
    @Override
    public void deleteAllById(List<String> dictIds) {
        for (String dictId : dictIds) {
            deleteById(dictId);
        }
    }
}

