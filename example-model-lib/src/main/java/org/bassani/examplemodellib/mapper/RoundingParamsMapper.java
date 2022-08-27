package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.entity.redis.PurchaseRoundingParamsHash;
import br.com.example.purchasesimulatormodellib.domain.entity.redis.key.HashRoundingParamsKey;
import br.com.example.purchasesimulatormodellib.domain.request.PurchaseRoundingParamsRequest;
import br.com.example.purchasesimulatormodellib.domain.response.PurchaseRoundingParamsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper
public interface RoundingParamsMapper {

	RoundingParamsMapper INSTANCE = Mappers.getMapper(RoundingParamsMapper.class);

	static RoundingParamsMapper roundingParamsMapper() {
		return INSTANCE;
	}

	@Mapping(expression = "java(this.mapHashRoundingParamsKey(distributionCenterId, response.getProductId()))", target = "hashKey")
	@Mapping(expression = "java(response)", target = "purchaseRoundingParamsResponse")
	PurchaseRoundingParamsHash toHash(Long distributionCenterId, PurchaseRoundingParamsResponse response);

	default HashRoundingParamsKey mapHashRoundingParamsKey(Long distributionCenterId, Long productId) {
		return HashRoundingParamsKey.builder().productId(productId).distributionCenterId(distributionCenterId).build();
	}

	PurchaseRoundingParamsRequest toRequest(Long distributionCenterId, Set<Long> productIds);

	HashRoundingParamsKey toHashKey(Long distributionCenterId, Long productId);
}
