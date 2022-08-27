package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.entity.direct.CurveParameterEntity;
import br.com.example.purchasesimulatormodellib.domain.response.CurveParameterResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CurveParameterMapper {

	CurveParameterMapper INSTANCE = Mappers.getMapper(CurveParameterMapper.class);

    static CurveParameterMapper curveParameterMapper() {
        return INSTANCE;
    }
    
    @Mapping(source = "key.curvedProduct", target = "curvedProduct")
    @Mapping(source = "key.distributionCenter.id", target = "distributionCenter")
    @Mapping(source = "key.subGroup", target = "subGroup")
    CurveParameterResponse entityToResponse(CurveParameterEntity entity);
}
