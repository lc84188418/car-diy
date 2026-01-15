package com.cardiy.admin.mapper;

import com.cardiy.admin.domain.CommonUrlLogEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommonUrlLogMapper extends MongoRepository<CommonUrlLogEntity, String> {
}
