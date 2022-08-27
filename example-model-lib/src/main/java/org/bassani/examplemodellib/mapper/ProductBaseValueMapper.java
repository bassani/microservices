package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.entity.direct.ProductBaseValueEntity;
import br.com.example.purchasesimulatormodellib.domain.entity.redis.BaseValueProductPerBranchPurchaseHash;
import br.com.example.purchasesimulatormodellib.domain.entity.redis.key.HashBaseValueProductPerBranchPurchaseKey;
import br.com.example.purchasesimulatormodellib.domain.request.PurchaseBaseValueRequest;
import br.com.example.purchasesimulatormodellib.domain.response.BaseValueProductPerBranchPurchaseResponse;
import br.com.example.purchasesimulatormodellib.domain.response.ProductBaseValueResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper
public interface ProductBaseValueMapper {

	ProductBaseValueMapper INSTANCE = Mappers.getMapper(ProductBaseValueMapper.class);

	static ProductBaseValueMapper productBaseValueMapper() {
		return INSTANCE;
	}

	ProductBaseValueResponse entityToResponse(ProductBaseValueEntity entity);

	ProductBaseValueEntity responseToEntity(ProductBaseValueResponse response);

	@Mapping(expression = "java(this.mapHashBaseValueProductPerBranchPurchaseValueKey(response.getProductId(), response.getDistributionCenterId()))", target = "hashKey")
	@Mapping(expression = "java(response)", target = "baseValueProductPerBranchPurchase")
	BaseValueProductPerBranchPurchaseHash responseToHash(BaseValueProductPerBranchPurchaseResponse response);

	default HashBaseValueProductPerBranchPurchaseKey mapHashBaseValueProductPerBranchPurchaseValueKey(Long productId, Long distributionCenterId) {
		return HashBaseValueProductPerBranchPurchaseKey.builder().productId(productId).distributionCenterId(distributionCenterId).build();
	}

	PurchaseBaseValueRequest toRequest(Long distributionCenterId, Set<Long> productIds);

	HashBaseValueProductPerBranchPurchaseKey toHashKey(Long distributionCenterId, Long productId);
}
