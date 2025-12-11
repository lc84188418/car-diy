package com.cardiy.car.controller.admin;

import com.cardiy.car.domain.CarModelOption;
import com.cardiy.car.service.ICarModelOptionService;
import com.cardiy.car.util.MongoQueryUtil;
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
 * 车型配置选项管理 Controller（管理端）
 */
@RestController
@RequestMapping("/api/admin/car/option")
public class CarModelOptionAdminController {
    
    @Autowired
    private ICarModelOptionService optionService;
    
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 获取配置选项列表（分页）
     */
    @GetMapping("/list")
    public Result<Page<CarModelOption>> list(
            @RequestParam(value = "modelId", required = false) String modelId,
            @RequestParam(value = "optionType", required = false) String optionType,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "current", defaultValue = "1") int current,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(current - 1, size, Sort.by(Sort.Direction.ASC, "sortOrder"));
        
        // 构建查询条件
        List<Criteria> criteriaList = new ArrayList<>();
        if (modelId != null && !modelId.isEmpty()) {
            Criteria modelIdCriteria = MongoQueryUtil.buildEqualCriteria("modelId", modelId);
            if (modelIdCriteria != null) {
                criteriaList.add(modelIdCriteria);
            }
        }
        if (optionType != null && !optionType.isEmpty()) {
            Criteria optionTypeCriteria = MongoQueryUtil.buildEqualCriteria("optionType", optionType);
            if (optionTypeCriteria != null) {
                criteriaList.add(optionTypeCriteria);
            }
        }
        if (status != null && !status.isEmpty()) {
            Criteria statusCriteria = MongoQueryUtil.buildEqualCriteria("status", status);
            if (statusCriteria != null) {
                criteriaList.add(statusCriteria);
            }
        }
        
        Criteria criteria = MongoQueryUtil.combineCriteria(criteriaList);
        Query query = criteriaList.isEmpty() ? new Query() : new Query(criteria);
        Page<CarModelOption> page = MongoQueryUtil.queryWithPage(mongoTemplate, query, CarModelOption.class, pageable);
        
        return Result.success(page);
    }

    /**
     * 根据配置选项ID获取详细信息
     */
    @GetMapping(value = "/{id}")
    public Result<CarModelOption> getInfo(@PathVariable("id") String id) {
        return optionService.findById(id)
            .map(Result::success)
            .orElse(Result.error("配置选项不存在"));
    }

    /**
     * 新增配置选项
     */
    @PostMapping
    public Result<CarModelOption> add(@RequestBody CarModelOption option) {
        CarModelOption saved = optionService.save(option);
        return Result.success(saved);
    }

    /**
     * 修改配置选项
     */
    @PutMapping
    public Result<CarModelOption> edit(@RequestBody CarModelOption option) {
        CarModelOption saved = optionService.save(option);
        return Result.success(saved);
    }

    /**
     * 删除配置选项
     */
    @DeleteMapping("/{ids}")
    public Result<Void> remove(@PathVariable String[] ids) {
        optionService.deleteAllById(List.of(ids));
        return Result.success();
    }
}

