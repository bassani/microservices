package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.entity.redis.AnticipationProductPurchaseDaysHash;
import br.com.example.purchasesimulatormodellib.domain.entity.redis.key.HashAnticipationProductPurchaseDaysKey;
import br.com.example.purchasesimulatormodellib.domain.projection.PurchaseDaysProjection;
import br.com.example.purchasesimulatormodellib.domain.response.PurchaseDaysResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface PurchaseDaysMapper {

	PurchaseDaysMapper INSTANCE = Mappers.getMapper(PurchaseDaysMapper.class);

	static PurchaseDaysMapper purchaseDaysMapper() {
		return INSTANCE;
	}

	@Mapping(expression = "java(this.mapHashProductPurchaseDayKey(distributionCenterId, supplierId, response.getProductId()))", target = "hashKey")
	@Mapping(expression = "java(response)", target = "searchPurchaseDaysResponse")
	AnticipationProductPurchaseDaysHash toHash(Long distributionCenterId, Long supplierId,
			PurchaseDaysResponse response);

	default HashAnticipationProductPurchaseDaysKey mapHashProductPurchaseDayKey(Long distributionCenterId,
			Long supplierId, Long productId) {
		return HashAnticipationProductPurchaseDaysKey.builder().productId(productId)
				.distributionCenterId(distributionCenterId).supplierId(supplierId).build();
	}

	HashAnticipationProductPurchaseDaysKey toHashKey(Long distributionCenterId, Long supplierId, Long productId);

    PurchaseDaysResponse toResponse(PurchaseDaysProjection project);
}
