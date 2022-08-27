package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.entity.mongodb.SimulationProductEntity;
import br.com.example.purchasesimulatormodellib.domain.response.SimulationProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Collection;

@Mapper
public interface SimulationProductMapper {

    SimulationProductMapper INSTANCE = Mappers.getMapper(SimulationProductMapper.class);

    static SimulationProductMapper simulationProductMapper() {
        return INSTANCE;
    }

    @Mapping(target = "totalValue", source = "totalAmountWithNegotiatedDiscount")
    SimulationProductResponse entityToResponse(SimulationProductEntity entity);

    Collection<SimulationProductResponse> entityListToResponseList(Collection<SimulationProductEntity> entity);

}