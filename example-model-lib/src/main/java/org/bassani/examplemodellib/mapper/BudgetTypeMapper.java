package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.BudgetTypeEntity;
import br.com.example.purchasesimulatormodellib.domain.request.BudgetTypeRequest;
import br.com.example.purchasesimulatormodellib.domain.response.BudgetTypeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BudgetTypeMapper {

    BudgetTypeMapper INSTANCE = Mappers.getMapper(BudgetTypeMapper.class );

    static BudgetTypeMapper budgetTypeMapper() { return INSTANCE;}

    BudgetTypeResponse entityToResponse(BudgetTypeEntity entity);

    BudgetTypeEntity requestToEntity(BudgetTypeRequest request);
}