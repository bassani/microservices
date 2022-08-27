package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.entity.direct.ManufacturerEntity;
import br.com.example.purchasesimulatormodellib.domain.entity.direct.ScheduleSupplierEntity;
import br.com.example.purchasesimulatormodellib.domain.response.BillingSupplierParamResponse;
import br.com.example.purchasesimulatormodellib.domain.response.BillingSupplierResponse;
import br.com.example.purchasesimulatormodellib.domain.response.BillingSupplierToPurchaseResponse;
import br.com.example.purchasesimulatormodellib.domain.response.DistributionCenterResponse;
import br.com.example.purchasesimulatormodellib.domain.response.ManufactureParamResponse;
import br.com.example.purchasesimulatormodellib.domain.response.ManufacturerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface ManufacturerMapper {
	ManufacturerMapper INSTANCE = Mappers.getMapper( ManufacturerMapper.class );

	static ManufacturerMapper manufacturerMapper() { return INSTANCE;}

    @Mapping(target = "parentSupplierId", source = "parent.id")
    @Mapping(target = "parentSupplierName", source = "parent.name")
	ManufacturerResponse entityToResponse(ManufacturerEntity entity);

	ManufacturerEntity responseToEntity(ManufacturerResponse response);

    BillingSupplierToPurchaseResponse entityToPurchaseResponse(ManufacturerEntity entity);

    default List<BillingSupplierParamResponse> toBillingSupplierParamResponse(ManufacturerEntity entity) {
        List<BillingSupplierParamResponse> responses = new ArrayList<>();
        BillingSupplierResponse billingSupplierResponse =
                BillingSupplierResponse.builder().id(entity.getId()).billingSupplierName(entity.getName()).build();

        for(ScheduleSupplierEntity scheduleSupplier: entity.getScheduleSuppliers()) {
            billingSupplierResponse.setMinOrderValue(scheduleSupplier.getDistributionCenterSupplier().getOrderMinVl());

            DistributionCenterResponse distributionCenterResponse = DistributionCenterResponse.builder()
                    .id(scheduleSupplier.getDistributionCenter().getId())
                    .name(scheduleSupplier.getDistributionCenter().getName()).build();
            ManufactureParamResponse manufactureParamResponse = ManufactureParamResponse.builder()
                    .id(scheduleSupplier.getManufacturer().getId())
                    .name(scheduleSupplier.getManufacturer().getName()).build();

            BillingSupplierParamResponse response = BillingSupplierParamResponse.builder()
                    .billingSupplierResponse(billingSupplierResponse)
                    .distributionCenterResponse(distributionCenterResponse)
                    .manufactureParamResponse(manufactureParamResponse)
                    .build();
            responses.add(response);
        }

        return responses;
    }


}