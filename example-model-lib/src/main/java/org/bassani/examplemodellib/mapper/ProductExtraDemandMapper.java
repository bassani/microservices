package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.entity.redis.ProductExtraDemandHash;
import br.com.example.purchasesimulatormodellib.domain.entity.redis.key.HashProductExtraDemandKey;
import br.com.example.purchasesimulatormodellib.domain.request.ExtraDemandRequest;
import br.com.example.purchasesimulatormodellib.domain.response.ExtraDemandResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper
public interface ProductExtraDemandMapper {

	ProductExtraDemandMapper INSTANCE = Mappers.getMapper(ProductExtraDemandMapper.class);

	static ProductExtraDemandMapper productExtraDemandMapper() {
		return INSTANCE;
	}

	@Mapping(expression = "java(this.mapHashProductExtraDemandKey(distributionCenterId, response.getProductId()))", target = "hashKey")
	@Mapping(expression = "java(response)", target = "purchaseExtraDemandResponse")
	ProductExtraDemandHash toHash(Long distributionCenterId, ExtraDemandResponse response);

	default HashProductExtraDemandKey mapHashProductExtraDemandKey(Long distributionCenterId, Long productId) {
		return HashProductExtraDemandKey.builder().productId(productId).distributionCenterId(distributionCenterId)
				.build();
	}

    @Mapping(source = "productIds", target = "productIds")
    ExtraDemandRequest toRequest(Long distributionCenterId, Set<Long> productIds);

	HashProductExtraDemandKey toHashKey(Long distributionCenterId, Long productId);
}
