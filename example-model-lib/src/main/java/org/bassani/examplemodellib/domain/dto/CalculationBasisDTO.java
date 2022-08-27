package org.bassani.examplemodellib.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CalculationBasisDTO implements Serializable {

    private static final long serialVersionUID = -179402194186393369L;

    private Long distributionCenterId;
    private Long productId;
    private LocalDate productCreateDate;
    private Long salesDay;
    private Boolean isInactiveProduct;

}
