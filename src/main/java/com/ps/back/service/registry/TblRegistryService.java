package com.ps.back.service.registry;

import com.ps.back.model.pojos.consume.ConsumeJsonLong;
import com.ps.back.model.pojos.response.ResponseJsonRegistry;

public interface TblRegistryService {
    ResponseJsonRegistry createOrUpdateRegistryByCveUser(ConsumeJsonLong consume);
}
