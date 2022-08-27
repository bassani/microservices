package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.entity.direct.BranchPurchaseEntity;
import br.com.example.purchasesimulatormodellib.domain.response.BranchPurchaseResponse;
import br.com.example.purchasesimulatormodellib.domain.response.BranchesByDistributionCenterResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

@Mapper
public interface BranchPurchaseMapper {

    BranchPurchaseMapper INSTANCE = Mappers.getMapper(BranchPurchaseMapper.class);

    static BranchPurchaseMapper branchPurchaseMapper() {return INSTANCE;}

    BranchPurchaseResponse entityToResponse(BranchPurchaseEntity entity);

    BranchPurchaseEntity responseToEntity(BranchPurchaseResponse response);

    @Mapping(target = "distributionCenterId", source = "key")
    @Mapping(target = "branchIds", source = "value")
    BranchesByDistributionCenterResponse entryToResponse(Map.Entry<Long, List<Long>> entry);
}
