package com.cardiy.car.controller.admin;

import com.cardiy.car.domain.CarModel;
import com.cardiy.car.service.ICarModelService;
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
 * 车型管理 Controller（管理端）
 */
@RestController
@RequestMapping("/api/admin/car/model")
public class CarModelAdminController {
    
    @Autowired
    private ICarModelService carModelService;
    
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 获取车型列表（分页）
     */
    @GetMapping("/list")
    public Result<Page<CarModel>> list(
            @RequestParam(value = "brand", required = false) String brand,
            @RequestParam(value = "series", required = false) String series,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "current", defaultValue = "1") int current,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(current - 1, size, Sort.by(Sort.Direction.DESC, "createTime"));
        
        // 构建查询条件
        List<Criteria> criteriaList = new ArrayList<>();
        if (brand != null && !brand.isEmpty()) {
            Criteria brandCriteria = MongoQueryUtil.buildLikeCriteria("brand", brand);
            if (brandCriteria != null) {
                criteriaList.add(brandCriteria);
            }
        }
        if (series != null && !series.isEmpty()) {
            Criteria seriesCriteria = MongoQueryUtil.buildLikeCriteria("series", series);
            if (seriesCriteria != null) {
                criteriaList.add(seriesCriteria);
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
        Page<CarModel> page = MongoQueryUtil.queryWithPage(mongoTemplate, query, CarModel.class, pageable);
        
        return Result.success(page);
    }

    /**
     * 根据车型ID获取详细信息
     */
    @GetMapping(value = "/{id}")
    public Result<CarModel> getInfo(@PathVariable("id") String id) {
        return carModelService.findById(id)
            .map(Result::success)
            .orElse(Result.error("车型不存在"));
    }

    /**
     * 新增车型
     */
    @PostMapping
    public Result<CarModel> add(@RequestBody CarModel model) {
        CarModel saved = carModelService.save(model);
        return Result.success(saved);
    }

    /**
     * 修改车型
     */
    @PutMapping
    public Result<CarModel> edit(@RequestBody CarModel model) {
        CarModel saved = carModelService.save(model);
        return Result.success(saved);
    }

    /**
     * 删除车型
     */
    @DeleteMapping("/{ids}")
    public Result<Void> remove(@PathVariable String[] ids) {
        for (String id : ids) {
            carModelService.deleteById(id);
        }
        return Result.success();
    }
}

