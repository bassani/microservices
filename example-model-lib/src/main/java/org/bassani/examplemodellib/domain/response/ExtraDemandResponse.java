package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExtraDemandResponse implements Serializable {

    private static final long serialVersionUID = -5451804151876646583L;

    @ApiModelProperty(value = "ID do produto", example = "1")
    private Long productId;

    //TODO Mudar para Long
    @ApiModelProperty(value = "Número da demanda extra do produto", example = "100")
    private Integer extraDemand;
}
