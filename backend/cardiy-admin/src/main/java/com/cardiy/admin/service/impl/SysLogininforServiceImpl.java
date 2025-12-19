package com.cardiy.admin.service.impl;

import cn.hutool.core.util.IdUtil;
import com.cardiy.admin.domain.SysLogininfor;
import com.cardiy.admin.mapper.SysLogininforMapper;
import com.cardiy.admin.service.ISysLogininforService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 系统访问日志 业务层处理
 */
@Service
public class SysLogininforServiceImpl implements ISysLogininforService {

    @Autowired
    private SysLogininforMapper logininforMapper;

    @Override
    public SysLogininfor save(SysLogininfor logininfor) {
        logininfor.setInfoId(IdUtil.getSnowflakeNextIdStr());
        return logininforMapper.save(logininfor);
    }

    @Override
    public Optional<SysLogininfor> findById(String infoId) {
        SysLogininfor logininfor = logininforMapper.findByInfoId(infoId);
        return Optional.ofNullable(logininfor);
    }

    @Override
    public Page<SysLogininfor> findAll(Pageable pageable) {
        return logininforMapper.findAll(pageable);
    }

    @Override
    public Page<SysLogininfor> findByLoginTimeBetween(LocalDateTime startTime, LocalDateTime endTime, Pageable pageable) {
        return logininforMapper.findByLoginTimeBetween(startTime, endTime, pageable);
    }

    @Override
    public void deleteById(String infoId) {
        SysLogininfor logininfor = logininforMapper.findByInfoId(infoId);
        if (logininfor != null && logininfor.getId() != null) {
            logininforMapper.deleteById(logininfor.getId());
        }
    }

    @Override
    public void deleteAllById(List<String> infoIds) {
        for (String infoId : infoIds) {
            deleteById(infoId);
        }
    }
}

