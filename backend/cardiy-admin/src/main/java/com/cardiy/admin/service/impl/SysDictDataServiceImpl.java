package com.cardiy.admin.service.impl;

import cn.hutool.core.util.IdUtil;
import com.cardiy.admin.domain.SysDictData;
import com.cardiy.admin.mapper.SysDictDataMapper;
import com.cardiy.admin.service.ISysDictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 字典数据 业务层处理
 */
@Service
public class SysDictDataServiceImpl implements ISysDictDataService {
    
    @Autowired
    private SysDictDataMapper dictDataMapper;
    
    @Override
    public SysDictData save(SysDictData dictData) {
        if(Objects.isNull(dictData.getDictCode())){
            dictData.setDictCode(IdUtil.getSnowflakeNextIdStr());
        }
        return dictDataMapper.save(dictData);
    }
    
    @Override
    public Optional<SysDictData> findById(String dictCode) {
        SysDictData dictData = dictDataMapper.findByDictCode(dictCode);
        return Optional.ofNullable(dictData);
    }
    
    @Override
    public List<SysDictData> findAll() {
        return dictDataMapper.findAll();
    }
    
    @Override
    public List<SysDictData> findByDictType(String dictType) {
        return dictDataMapper.findByDictTypeOrderByDictSortAsc(dictType);
    }
    
    @Override
    public List<SysDictData> findByDictTypeAndStatus(String dictType, String status) {
        return dictDataMapper.findByDictTypeAndStatusOrderByDictSortAsc(dictType, status);
    }
    
    @Override
    public void deleteById(String dictCode) {
        SysDictData dictData = dictDataMapper.findByDictCode(dictCode);
        if (dictData != null && dictData.getId() != null) {
            dictDataMapper.deleteById(dictData.getId());
        }
    }
    
    @Override
    public void deleteAllById(List<String> dictCodes) {
        for (String dictCode : dictCodes) {
            deleteById(dictCode);
        }
    }
}

