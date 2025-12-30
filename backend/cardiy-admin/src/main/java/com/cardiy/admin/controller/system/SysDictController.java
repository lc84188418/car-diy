package com.cardiy.admin.controller.system;

import com.cardiy.admin.domain.SysDictData;
import com.cardiy.admin.domain.SysDictType;
import com.cardiy.admin.service.ISysDictDataService;
import com.cardiy.admin.service.ISysDictTypeService;
import com.cardiy.common.api.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字典管理
 */
@RestController
@RequestMapping("/api/admin/system/dict")
public class SysDictController {

    @Autowired
    private ISysDictTypeService dictTypeService;

    @Autowired
    private ISysDictDataService dictDataService;

    /**
     * 获取字典类型列表
     */
    @GetMapping("/type/list")
    public Result<List<SysDictType>> listType(@RequestParam(value = "dictName", required = false) String dictName,
                                              @RequestParam(value = "status", required = false) String status) {
        List<SysDictType> list;
        if (status != null && !status.isEmpty()) {
            list = dictTypeService.findByStatus(status);
        } else {
            list = dictTypeService.findAll();
        }
        return Result.success(list);
    }

    /**
     * 根据字典类型编号获取详细信息
     */
    @GetMapping("/type/{dictId}")
    public Result<SysDictType> getTypeInfo(@PathVariable("dictId") String dictId) {
        return dictTypeService.findById(dictId)
                .map(Result::success)
                .orElse(Result.error("字典类型不存在"));
    }

    /**
     * 新增字典类型
     */
    @PostMapping("/type")
    public Result<Void> addType(@RequestBody SysDictType dictType) {
        dictTypeService.save(dictType);
        return Result.success();
    }

    /**
     * 修改字典类型
     */
    @PutMapping("/type")
    public Result<Void> editType(@RequestBody SysDictType dictType) {
        dictTypeService.save(dictType);
        return Result.success();
    }

    /**
     * 删除字典类型
     */
    @DeleteMapping("/type/{dictIds}")
    public Result<Void> removeType(@PathVariable String[] dictIds) {
        dictTypeService.deleteAllById(List.of(dictIds));
        return Result.success();
    }

    /**
     * 根据字典类型获取字典数据列表
     */
    @GetMapping("/data/list")
    public Result<List<SysDictData>> listData(SysDictData dictData) {
        List<SysDictData> list = dictDataService.findByEntity(dictData);
        return Result.success(list);
    }

    /**
     * 根据字典编码获取详细信息
     */
    @GetMapping("/data/{dictCode}")
    public Result<SysDictData> getDataInfo(@PathVariable("dictCode") String dictCode) {
        return dictDataService.findById(dictCode)
                .map(Result::success)
                .orElse(Result.error("字典数据不存在"));
    }

    /**
     * 新增字典数据
     */
    @PostMapping("/data")
    public Result<Void> addData(@RequestBody SysDictData dictData) {
        dictDataService.save(dictData);
        return Result.success();
    }

    /**
     * 修改字典数据
     */
    @PutMapping("/data")
    public Result<Void> editData(@RequestBody SysDictData dictData) {
        dictDataService.save(dictData);
        return Result.success();
    }

    /**
     * 删除字典数据
     */
    @DeleteMapping("/data/{dictCodes}")
    public Result<Void> removeData(@PathVariable String[] dictCodes) {
        dictDataService.deleteAllById(List.of(dictCodes));
        return Result.success();
    }
}

