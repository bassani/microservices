package org.bassani.examplebff.mock;

import org.bassani.examplemodellib.domain.request.SimNewPaymentTermRequest;
import org.bassani.examplemodellib.enums.NewPaymentTermTypeEnum;

public class SimNewPaymentTermRequestMock {

    public static SimNewPaymentTermRequest simNewPaymentTermRequestGeneral() {
        return SimNewPaymentTermRequest.builder()
                .newPaymentTermType(NewPaymentTermTypeEnum.TIPO_NOVO_PRAZO_GERAL)
                .newPaymentTermGeneral(SimNewPaymentTermGeneralRequestMock.simNewPaymentTermGeneralRequest())
                .build();
    }

    public static SimNewPaymentTermRequest simNewPaymentTermRequestDC() {
        return SimNewPaymentTermRequest.builder()
                .newPaymentTermType(NewPaymentTermTypeEnum.TIPO_NOVO_PRAZO_POR_CD)
                .newPaymentTermDCs(SimNewPaymentTermDCRequestMock.simNewPaymentTermDCRequest_List())
                .build();
    }

}
