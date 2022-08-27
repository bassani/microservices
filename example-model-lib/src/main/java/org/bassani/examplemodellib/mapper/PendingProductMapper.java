package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.entity.redis.PurchasePendingProductHash;
import br.com.example.purchasesimulatormodellib.domain.entity.redis.key.HashPendingProductsKey;
import br.com.example.purchasesimulatormodellib.domain.request.QuantityPendingOrdersRequest;
import br.com.example.purchasesimulatormodellib.domain.response.QuantityPendingOrdersResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper
public interface PendingProductMapper {

	PendingProductMapper INSTANCE = Mappers.getMapper(PendingProductMapper.class);

	static PendingProductMapper pendingProductMapper() {
		return INSTANCE;
	}

	@Mapping(expression = "java(this.mapHashPendingProductsKey(distributionCenterId, response.getProductId()))", target = "hashKey")
	@Mapping(expression = "java(response)", target = "purchasePendingProductsResponse")
	PurchasePendingProductHash toHash(Long distributionCenterId, QuantityPendingOrdersResponse response);

	default HashPendingProductsKey mapHashPendingProductsKey(Long distributionCenterId, Long productId) {
		return HashPendingProductsKey.builder().productId(productId).distributionCenterId(distributionCenterId).build();
	}

	@Mapping(source = "productIds", target = "productIds")
	@Mapping(source = "distributionCenterId", target = "distributionCenterId")
	QuantityPendingOrdersRequest toRequest(Long distributionCenterId, Set<Long> productIds);

	HashPendingProductsKey toHashKey(Long distributionCenterId, Long productId);
}
