package org.bassani.examplebff.mock;

import org.bassani.examplemodellib.domain.request.SimNewPaymentTermGeneralRequest;

public class SimNewPaymentTermGeneralRequestMock {

    public static SimNewPaymentTermGeneralRequest simNewPaymentTermGeneralRequest(){
        return SimNewPaymentTermGeneralRequest
                .builder()
                .newPaymentTermCode(123L)
                .newPaymentTermDescription("myAmazingDescription")
                .build();
    }

}
