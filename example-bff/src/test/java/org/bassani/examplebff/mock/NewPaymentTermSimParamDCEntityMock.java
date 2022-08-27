package org.bassani.examplebff.mock;

import org.bassani.examplemodellib.domain.entity.dbjor.NewPaymentTermSimParamDCEntity;

import java.util.List;

public class NewPaymentTermSimParamDCEntityMock {


    public static NewPaymentTermSimParamDCEntity getNewPaymentTermSimParamDCEntity_ONE() {
        return NewPaymentTermSimParamDCEntity.builder()
                .daysQuantityPayment(100L)
                .newPaymentTermCode(10L)
                .newPaymentTermDescription("myAmazingNPTDC 1")
                .distributionCenterId(900L)
                .build();
    }

    public static NewPaymentTermSimParamDCEntity getNewPaymentTermSimParamDCEntity_TWO() {
        return NewPaymentTermSimParamDCEntity.builder()
                .daysQuantityPayment(200L)
                .newPaymentTermCode(20L)
                .newPaymentTermDescription("myAmazingNPTDC 2")
                .distributionCenterId(905L)
                .build();
    }

    public static List<NewPaymentTermSimParamDCEntity> getNewPaymentTermSimParamDCEntity_List() {
        return List.of(getNewPaymentTermSimParamDCEntity_ONE(), getNewPaymentTermSimParamDCEntity_TWO());
    }
}
