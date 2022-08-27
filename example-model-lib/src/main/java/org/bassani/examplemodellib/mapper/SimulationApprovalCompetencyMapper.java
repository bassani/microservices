package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.SimulationApprovalCompetencyEntity;
import br.com.example.purchasesimulatormodellib.domain.request.SimulationApprovalCompetencyRequest;
import br.com.example.purchasesimulatormodellib.domain.response.SimulationApprovalCompetencyResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SimulationApprovalCompetencyMapper {

    SimulationApprovalCompetencyMapper INSTANCE = Mappers.getMapper(SimulationApprovalCompetencyMapper.class);

    static SimulationApprovalCompetencyMapper simulationApprovalCompetencyMapper() {
        return INSTANCE;
    }

    SimulationApprovalCompetencyEntity requestToEntity(SimulationApprovalCompetencyRequest request);

    SimulationApprovalCompetencyResponse entityToResponse(SimulationApprovalCompetencyEntity entity);
}
