package com.cardiy.car.service.impl;

import com.cardiy.car.domain.CarModelOption;
import com.cardiy.car.mapper.CarModelOptionMapper;
import com.cardiy.car.service.ICarModelOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 车型配置选项服务实现
 */
@Service
public class CarModelOptionServiceImpl implements ICarModelOptionService {
    
    @Autowired
    private CarModelOptionMapper optionMapper;
    
    @Override
    public CarModelOption save(CarModelOption option) {
        return optionMapper.save(option);
    }
    
    @Override
    public Optional<CarModelOption> findById(String id) {
        return optionMapper.findById(id);
    }
    
    @Override
    public List<CarModelOption> findAll() {
        return optionMapper.findAll();
    }
    
    @Override
    public List<CarModelOption> findByModelId(String modelId) {
        return optionMapper.findAll().stream()
            .filter(opt -> modelId.equals(opt.getModelId()))
            .toList();
    }
    
    @Override
    public List<CarModelOption> findByModelIdAndOptionType(String modelId, String optionType) {
        return optionMapper.findAll().stream()
            .filter(opt -> modelId.equals(opt.getModelId()) && optionType.equals(opt.getOptionType()))
            .toList();
    }
    
    @Override
    public List<CarModelOption> findByOptionType(String optionType) {
        return optionMapper.findAll().stream()
            .filter(opt -> optionType.equals(opt.getOptionType()))
            .toList();
    }
    
    @Override
    public List<CarModelOption> findByStatus(String status) {
        return optionMapper.findAll().stream()
            .filter(opt -> status.equals(opt.getStatus()))
            .toList();
    }
    
    @Override
    public void deleteById(String id) {
        optionMapper.deleteById(id);
    }
    
    @Override
    public void deleteAllById(List<String> ids) {
        optionMapper.deleteAllById(ids);
    }
}

