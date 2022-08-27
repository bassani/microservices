package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Builder
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class CalculationBasisOptionResponse {
    @ApiModelProperty(value = "Dias da base de cálculo", example = "1")
    private Integer days;

    @ApiModelProperty(value = "Nome do detalhamento da base de cálculo", example = "Semanal")
    private String name;

    @ApiModelProperty(value = "Código do tipo da base de cálculo", example = "1")
    private Long parent;
}
