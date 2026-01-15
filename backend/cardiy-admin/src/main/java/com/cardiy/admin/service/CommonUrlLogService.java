package com.cardiy.admin.service;

import com.cardiy.admin.domain.CommonUrlLogEntity;

import java.util.Set;

public interface CommonUrlLogService {
    /**
     * @desc:批量更新数量
     * @author: liuchun
     * @createTime: 2024/9/23 1:42
     * @param: [set]
     * @version: V1.29.0
     * @return: void
     **/
    void updateBatch(Set<CommonUrlLogEntity> set);
}
