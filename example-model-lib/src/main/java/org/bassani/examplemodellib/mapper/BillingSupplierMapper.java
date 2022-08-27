package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.projection.BillingSupplierProjection;
import br.com.example.purchasesimulatormodellib.domain.response.BillingSupplierResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BillingSupplierMapper {

    BillingSupplierMapper INSTANCE = Mappers.getMapper(BillingSupplierMapper.class);

    static BillingSupplierMapper billingSupplierCategoryMapper() {
        return INSTANCE;
    }

    @Mapping(source = "billingSupplierId", target = "id")
    BillingSupplierResponse projectionToResponse(BillingSupplierProjection projection);

}
