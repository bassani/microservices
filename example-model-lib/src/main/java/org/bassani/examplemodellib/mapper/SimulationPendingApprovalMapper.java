package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.dto.SimulationPendingApprovalDTO;
import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.SimulationApprovalFlowEntity;
import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.SimulationParametersEntity;
import br.com.example.purchasesimulatormodellib.domain.projection.SimulationPendingApprovalProjection;
import br.com.example.purchasesimulatormodellib.domain.response.SimulationPendingApprovalResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface SimulationPendingApprovalMapper {
    SimulationPendingApprovalMapper INSTANCE = Mappers.getMapper(SimulationPendingApprovalMapper.class);

    static SimulationPendingApprovalMapper simulationPendingApprovalMapper() {
        return INSTANCE;
    }

    @Mapping(target = "totalValue", ignore = true)
    @Mapping(target = "simulationUserName", ignore = true)
    @Mapping(target = "operatorName", ignore = true)
    @Mapping(target="simulationId", source="id")
    @Mapping(target="startedApprovalDate", expression="java(this.getStartedApprovalDate(entity.getApprovalFlow()))")
    SimulationPendingApprovalResponse entityToResponse(SimulationParametersEntity entity);

    SimulationParametersEntity responseToEntity(SimulationPendingApprovalResponse response);

    SimulationPendingApprovalDTO projectionToDto(SimulationPendingApprovalProjection projection);

    default LocalDate getStartedApprovalDate(List<SimulationApprovalFlowEntity> approvalFlow) {
        return approvalFlow.get(0).getDtCreated().toLocalDate();
    }
}
