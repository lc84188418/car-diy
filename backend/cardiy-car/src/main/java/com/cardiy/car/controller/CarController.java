package com.cardiy.car.controller;

import com.cardiy.car.domain.CarModel;
import com.cardiy.car.domain.CarModelOption;
import com.cardiy.car.mapper.CarModelOptionMapper;
import com.cardiy.car.service.ICarModelService;
import com.cardiy.common.api.Result;
import com.cardiy.common.dto.CarModelDTO;
import com.cardiy.common.dto.ConfigPlanDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CarController {

    @Autowired
    private ICarModelService carModelService;
    
    @Autowired
    private CarModelOptionMapper carModelOptionMapper;
    
    private static final List<ConfigPlanDTO> PLANS = new ArrayList<>();

    @GetMapping("/models")
    public Result<List<CarModelDTO>> listModels() {
        // 从MongoDB查询所有正常状态的车型
        List<CarModel> models = carModelService.findByStatus("0");
        
        // 转换为DTO并填充配置选项
        List<CarModelDTO> result = models.stream().map(model -> {
            CarModelDTO dto = new CarModelDTO();
            BeanUtils.copyProperties(model, dto);
            
            // 查询该车型的所有配置选项
            List<CarModelOption> options = carModelOptionMapper.findByModelIdAndStatusOrderBySortOrderAsc(
                model.getId(), "0");
            
            // 按类型分组
            CarModelDTO.Options opt = new CarModelDTO.Options();
            opt.setPaintColors(convertOptions(options, "paintColor"));
            opt.setRims(convertOptions(options, "rim"));
            opt.setTires(convertOptions(options, "tire"));
            opt.setCalipers(convertOptions(options, "caliper"));
            
            dto.setOptions(opt);
            return dto;
        }).collect(Collectors.toList());
        
        return Result.success(result);
    }
    
    private List<CarModelDTO.SimpleOption> convertOptions(List<CarModelOption> options, String type) {
        return options.stream()
            .filter(opt -> type.equals(opt.getOptionType()))
            .map(opt -> {
                CarModelDTO.SimpleOption simple = new CarModelDTO.SimpleOption();
                simple.setCode(opt.getCode());
                simple.setName(opt.getName());
                simple.setPreviewColor(opt.getPreviewColor());
                simple.setImageUrl(opt.getImageUrl());
                simple.setDescription(opt.getDescription());
                return simple;
            })
            .collect(Collectors.toList());
    }

    @PostMapping("/plans")
    public Result<ConfigPlanDTO> savePlan(@RequestBody ConfigPlanDTO dto) {
        if (dto.getId() == null || dto.getId().isEmpty()) {
            dto.setId(UUID.randomUUID().toString());
        }
        PLANS.add(dto);
        return Result.success(dto);
    }
}
