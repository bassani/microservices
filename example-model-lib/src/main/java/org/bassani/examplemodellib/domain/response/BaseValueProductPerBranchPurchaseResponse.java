package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@Builder
public class BaseValueProductPerBranchPurchaseResponse implements Serializable {

    private static final long serialVersionUID = -1909180860787837183L;

    @ApiModelProperty(value = "ID do produto", example = "1")
    private Long productId;

    @ApiModelProperty(value = "ID Centro de Distribuição", example = "1")
    private Long distributionCenterId;

    @ApiModelProperty(value = "Soma do valor base", example = "1")
    private Long sumMaxBaseValue;

}
