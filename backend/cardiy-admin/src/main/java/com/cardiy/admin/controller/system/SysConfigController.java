package com.cardiy.admin.controller.system;

import com.cardiy.admin.domain.SysConfig;
import com.cardiy.admin.service.ISysConfigService;
import com.cardiy.admin.util.MongoQueryUtil;
import com.cardiy.common.api.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 参数配置
 */
@RestController
@RequestMapping("/api/admin/system/config")
public class SysConfigController {
    
    @Autowired
    private ISysConfigService configService;
    
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 获取参数配置列表
     */
    @GetMapping("/list")
    public Result<Page<SysConfig>> list(
            @RequestParam(value = "configName", required = false) String configName,
            @RequestParam(value = "configKey", required = false) String configKey,
            @RequestParam(value = "configType", required = false) String configType,
            @RequestParam(value = "current", defaultValue = "1") int current,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(current - 1, size, Sort.by(Sort.Direction.DESC, "createTime"));
        
        // 构建查询条件
        List<Criteria> criteriaList = new ArrayList<>();
        if (configName != null && !configName.isEmpty()) {
            Criteria configNameCriteria = MongoQueryUtil.buildLikeCriteria("configName", configName);
            if (configNameCriteria != null) {
                criteriaList.add(configNameCriteria);
            }
        }
        if (configKey != null && !configKey.isEmpty()) {
            Criteria configKeyCriteria = MongoQueryUtil.buildLikeCriteria("configKey", configKey);
            if (configKeyCriteria != null) {
                criteriaList.add(configKeyCriteria);
            }
        }
        if (configType != null && !configType.isEmpty()) {
            Criteria configTypeCriteria = MongoQueryUtil.buildEqualCriteria("configType", configType);
            if (configTypeCriteria != null) {
                criteriaList.add(configTypeCriteria);
            }
        }
        
        Criteria criteria = MongoQueryUtil.combineCriteria(criteriaList);
        Query query = criteriaList.isEmpty() ? new Query() : new Query(criteria);
        Page<SysConfig> page = MongoQueryUtil.queryWithPage(mongoTemplate, query, SysConfig.class, pageable);
        
        return Result.success(page);
    }

    /**
     * 根据参数编号获取详细信息
     */
    @GetMapping(value = "/{configId}")
    public Result<SysConfig> getInfo(@PathVariable("configId") String configId) {
        return configService.findById(configId)
            .map(Result::success)
            .orElse(Result.error("参数配置不存在"));
    }

    /**
     * 根据参数键名查询参数值
     */
    @GetMapping(value = "/configKey/{configKey}")
    public Result<SysConfig> getConfigKey(@PathVariable("configKey") String configKey) {
        return configService.findByConfigKey(configKey)
            .map(Result::success)
            .orElse(Result.error("参数配置不存在"));
    }

    /**
     * 新增参数配置
     */
    @PostMapping
    public Result<Void> add(@RequestBody SysConfig config) {
        configService.save(config);
        return Result.success();
    }

    /**
     * 修改参数配置
     */
    @PutMapping
    public Result<Void> edit(@RequestBody SysConfig config) {
        configService.save(config);
        return Result.success();
    }

    /**
     * 删除参数配置
     */
    @DeleteMapping("/{configIds}")
    public Result<Void> remove(@PathVariable String[] configIds) {
        configService.deleteAllById(List.of(configIds));
        return Result.success();
    }
}

