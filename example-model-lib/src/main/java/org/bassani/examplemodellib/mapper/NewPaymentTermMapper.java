package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.NewPaymentTermSimParamDCEntity;
import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.NewPaymentTermSimParamEntity;
import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.NewPaymentTermSimParamGeneralEntity;
import br.com.example.purchasesimulatormodellib.domain.request.SimNewPaymentTermDCRequest;
import br.com.example.purchasesimulatormodellib.domain.request.SimNewPaymentTermGeneralRequest;
import br.com.example.purchasesimulatormodellib.domain.request.SimNewPaymentTermRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface NewPaymentTermMapper {

    NewPaymentTermMapper INSTANCE = Mappers.getMapper(NewPaymentTermMapper.class);

    static NewPaymentTermMapper paymentTermsMapper() {
        return INSTANCE;
    }

    /*
     *  SimNewPaymentTermRequest -> NewPaymentTermSimParamEntity
     */

    @Mapping(target = "type", source = "newPaymentTermType.entity")
    NewPaymentTermSimParamEntity requesttoEntity(SimNewPaymentTermRequest simNewPaymentTermRequest);

    NewPaymentTermSimParamGeneralEntity requestToEntity(SimNewPaymentTermGeneralRequest simNewPaymentTermGeneralRequest);

    @Mapping(target = "distributionCenterId", source = "distributionCenter")
    NewPaymentTermSimParamDCEntity listRequestToListEntity(SimNewPaymentTermDCRequest simNewPaymentTermDCRequest);

    List<NewPaymentTermSimParamDCEntity> listRequestToListEntity(List<SimNewPaymentTermDCRequest> simNewPaymentTermDCRequestList);

    /*
     *   NewPaymentTermSimParamEntity -> SimNewPaymentTermRequest
     */
    @Mapping(target = "newPaymentTermType", expression = "java(NewPaymentTermTypeEnum.find(newPaymentTermSimParamEntity.getType().getId()))")
    SimNewPaymentTermRequest entityToRequest(NewPaymentTermSimParamEntity newPaymentTermSimParamEntity);

    SimNewPaymentTermGeneralRequest entityToRequest(NewPaymentTermSimParamGeneralEntity newPaymentTermSimParamGeneralEntity);

    @Mapping(target = "distributionCenter", source = "distributionCenterId")
    SimNewPaymentTermDCRequest listEntityToListRequest(NewPaymentTermSimParamDCEntity newPaymentTermSimParamDCEntity);

    List<SimNewPaymentTermDCRequest> listListEntityToListRequest(List<NewPaymentTermSimParamDCEntity> simNewPaymentTermDCRequestList);

}
