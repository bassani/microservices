package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.entity.redis.BranchDiffHash;
import br.com.example.purchasesimulatormodellib.domain.entity.redis.key.HashBranchDiffKey;
import br.com.example.purchasesimulatormodellib.domain.projection.BranchDiffProjection;
import br.com.example.purchasesimulatormodellib.domain.request.BranchDiffRequest;
import br.com.example.purchasesimulatormodellib.domain.response.BranchDiffResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper
public interface BranchDiffMapper {

    BranchDiffMapper INSTANCE = Mappers.getMapper(BranchDiffMapper.class);

    static BranchDiffMapper branchDiffMapper(){
        return INSTANCE;
    }

    BranchDiffResponse toResponse(BranchDiffProjection projection);
    
    @Mapping(expression = "java(this.mapHashBranchDiffKey(distributionCenterId, response.getProductId()))", target = "hashKey")
	@Mapping(expression = "java(response)", target = "branchDiffResponse")
	BranchDiffHash toHash(Long distributionCenterId, BranchDiffResponse response);

	default HashBranchDiffKey mapHashBranchDiffKey(Long distributionCenterId, Long productId) {
		return HashBranchDiffKey.builder().productId(productId).distributionCenterId(distributionCenterId).build();
	}

	BranchDiffRequest toRequest(Long distributionCenterId, Set<Long> productIds);

	HashBranchDiffKey toHashKey(Long distributionCenterId, Long productId);
}
