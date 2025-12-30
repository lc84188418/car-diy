package com.cardiy.admin.service;

import com.cardiy.admin.domain.SysDictData;

import java.util.List;
import java.util.Optional;

/**
 * 字典数据 业务层
 */
public interface ISysDictDataService {

    SysDictData save(SysDictData dictData);

    Optional<SysDictData> findById(String dictCode);

    List<SysDictData> findAll();

    List<SysDictData> findByDictType(String dictType);

    List<SysDictData> findByDictTypeAndStatus(String dictType, String status);

    List<SysDictData> findByEntity(SysDictData dictData);

    void deleteById(String dictCode);

    void deleteAllById(List<String> dictCodes);

}

