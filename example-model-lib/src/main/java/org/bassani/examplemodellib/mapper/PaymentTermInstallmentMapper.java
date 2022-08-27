package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.projection.PaymentTermInstallmentProjection;
import br.com.example.purchasesimulatormodellib.domain.response.PaymentTermInstallmentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PaymentTermInstallmentMapper {

    PaymentTermInstallmentMapper INSTANCE = Mappers.getMapper(PaymentTermInstallmentMapper.class);

    static PaymentTermInstallmentMapper paymentTermInstallmentMapper() {
        return INSTANCE;
    }

    @Mapping(source = "conditionPaymentCode", target = "id")
    PaymentTermInstallmentResponse projectionToResponse(PaymentTermInstallmentProjection projection);

}
