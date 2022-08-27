package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.entity.direct.PaymentTermEntity;
import br.com.example.purchasesimulatormodellib.domain.response.PaymentTermResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PaymentTermMapper {

	PaymentTermMapper ISNTANCE = Mappers.getMapper(PaymentTermMapper.class);

	static PaymentTermMapper paymentTermsMapper() {
		return ISNTANCE;
	}

	PaymentTermResponse entityToResponse(PaymentTermEntity entity);

	PaymentTermEntity responseToEntity(PaymentTermResponse response);




}
