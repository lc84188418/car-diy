package com.cardiy.car.service.impl;

import com.cardiy.car.domain.CarModel;
import com.cardiy.car.mapper.CarModelMapper;
import com.cardiy.car.service.ICarModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarModelServiceImpl implements ICarModelService {
    
    @Autowired
    private CarModelMapper carModelMapper;
    
    @Override
    public CarModel save(CarModel model) {
        return carModelMapper.save(model);
    }
    
    @Override
    public Optional<CarModel> findById(String id) {
        return carModelMapper.findById(id);
    }
    
    @Override
    public List<CarModel> findAll() {
        return carModelMapper.findAll();
    }
    
    @Override
    public List<CarModel> findByStatus(String status) {
        return carModelMapper.findByStatusOrderByCreateTimeDesc(status);
    }
    
    @Override
    public void deleteById(String id) {
        carModelMapper.deleteById(id);
    }
}

