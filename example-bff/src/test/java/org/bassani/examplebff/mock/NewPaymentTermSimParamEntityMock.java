package org.bassani.examplebff.mock;

import org.bassani.examplemodellib.domain.entity.dbjor.NewPaymentTermSimParamEntity;
import org.bassani.examplemodellib.enums.NewPaymentTermTypeEnum;

public class NewPaymentTermSimParamEntityMock {


    public static NewPaymentTermSimParamEntity getNewPaymentTermSimParamEntity_General() {
        return NewPaymentTermSimParamEntity.builder()
                .type(NewPaymentTermTypeEnum.TIPO_NOVO_PRAZO_GERAL.getEntity())
                .newPaymentTermGeneral(NewPaymentTermSimParamGeneralEntityMock.getNewPaymentTermSimParamGeneralEntity())
                .build();
    }

    public static NewPaymentTermSimParamEntity getNewPaymentTermSimParamEntity_DC() {
        return NewPaymentTermSimParamEntity.builder()
                .type(NewPaymentTermTypeEnum.TIPO_NOVO_PRAZO_POR_CD.getEntity())
                .newPaymentTermDCs(NewPaymentTermSimParamDCEntityMock.getNewPaymentTermSimParamDCEntity_List())
                .build();
    }

}
