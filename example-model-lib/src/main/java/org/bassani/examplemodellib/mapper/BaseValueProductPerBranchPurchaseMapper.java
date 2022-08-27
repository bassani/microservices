package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.entity.direct.BaseValueProductPerBranchPurchaseEntity;
import br.com.example.purchasesimulatormodellib.domain.projection.BaseValueProductPerBranchPurchaseProjection;
import br.com.example.purchasesimulatormodellib.domain.response.BaseValueProductPerBranchPurchaseResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BaseValueProductPerBranchPurchaseMapper {

	BaseValueProductPerBranchPurchaseMapper INSTANCE = Mappers.getMapper(BaseValueProductPerBranchPurchaseMapper.class);

    static BaseValueProductPerBranchPurchaseMapper baseValueProductPerBranchPurchaseMapper() {return INSTANCE;}

    BaseValueProductPerBranchPurchaseResponse projectionToResponse(BaseValueProductPerBranchPurchaseProjection projection);
    
    BaseValueProductPerBranchPurchaseResponse entityToResponse(BaseValueProductPerBranchPurchaseEntity entity);
    
    BaseValueProductPerBranchPurchaseEntity responseToEntity(BaseValueProductPerBranchPurchaseResponse response);
        
}
