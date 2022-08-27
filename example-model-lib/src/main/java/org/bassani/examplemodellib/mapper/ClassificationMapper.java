package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.csv.ClassificationCsv;
import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.ClassificationEntity;
import br.com.example.purchasesimulatormodellib.domain.request.ClassificationRequest;
import br.com.example.purchasesimulatormodellib.domain.response.ClassificationResponse;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClassificationMapper {
    ClassificationMapper INSTANCE = Mappers.getMapper(ClassificationMapper.class);

    static ClassificationMapper classificationTypeMapper() {
        return INSTANCE;
    }

    @Mapping(source = "registrationOperator", target = "registerOperator")
    ClassificationEntity responseToEntity(ClassificationResponse response);

    ClassificationCsv entityToCsv(ClassificationEntity entity);

    @Mapping(target = "id", ignore = true)
    @InheritInverseConfiguration
    ClassificationEntity csvToEntity(ClassificationCsv csvRow);
    
    @Mapping(expression = "java(entity.getStatus())", target = "active")
    @Mapping(source = "registerOperator", target = "registrationOperator")
    ClassificationResponse entityToResponse(ClassificationEntity entity);

    @Mapping(target = "updateOperator", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    @Mapping(target = "registerDate", ignore = true)
    @Mapping(expression = "java(requestStatusStringToEntityBoolean(request.getActive()))", target = "active")
    @Mapping(target = "registerOperator", source = "registrationOperator")
    ClassificationEntity requestToEntity(ClassificationRequest request);

    default boolean requestStatusStringToEntityBoolean(String val) {
        return val.equalsIgnoreCase("habilitado");
    }
}
