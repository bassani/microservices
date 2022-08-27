package org.bassani.examplebff.mock;

import org.bassani.examplemodellib.domain.request.SimNewPaymentTermDCRequest;

import java.util.List;

public class SimNewPaymentTermDCRequestMock {

    public static SimNewPaymentTermDCRequest simNewPaymentTermDCRequest_ONE() {
        return SimNewPaymentTermDCRequest.builder()
                .newPaymentTermCode(1L)
                .newPaymentTermDescription("myAmazingDescription1")
                .daysQuantityPayment(10L)
                .build();
    }

    public static SimNewPaymentTermDCRequest simNewPaymentTermDCRequest_TWO() {
        return SimNewPaymentTermDCRequest.builder()
                .newPaymentTermCode(2L)
                .newPaymentTermDescription("myAmazingDescription2")
                .daysQuantityPayment(20L)
                .build();
    }

    public static SimNewPaymentTermDCRequest simNewPaymentTermDCRequest_THREE() {
        return SimNewPaymentTermDCRequest.builder()
                .newPaymentTermCode(3L)
                .newPaymentTermDescription("myAmazingDescription3")
                .daysQuantityPayment(30L)
                .build();
    }

    public static List<SimNewPaymentTermDCRequest> simNewPaymentTermDCRequest_List() {
        return List.of(simNewPaymentTermDCRequest_ONE(), simNewPaymentTermDCRequest_TWO(),
                simNewPaymentTermDCRequest_THREE());
    }

}
