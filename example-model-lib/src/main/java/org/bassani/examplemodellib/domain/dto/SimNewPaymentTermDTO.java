package org.bassani.examplemodellib.domain.dto;

import br.com.example.purchasesimulatormodellib.enums.NewPaymentTermTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SimNewPaymentTermDTO implements Serializable {

    private static final long serialVersionUID = 8135523206160037031L;

    private NewPaymentTermTypeEnum newPaymentTermType;

    private Long distributionCenter;
    private Long newPaymentTermCode;
    private String newPaymentTermDescription;
    private Long daysQuantityPayment;

}
