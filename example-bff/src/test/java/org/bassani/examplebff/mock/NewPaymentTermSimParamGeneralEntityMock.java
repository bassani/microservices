package org.bassani.examplebff.mock;

import org.bassani.examplemodellib.domain.entity.dbjor.NewPaymentTermSimParamGeneralEntity;

public class NewPaymentTermSimParamGeneralEntityMock {

    public static NewPaymentTermSimParamGeneralEntity getNewPaymentTermSimParamGeneralEntity(){
        return NewPaymentTermSimParamGeneralEntity.builder()
                .daysQuantityPayment(100L)
                .newPaymentTermCode(10L)
                .newPaymentTermDescription("myamazingDescription")
                .build();
    }
}
