package org.bassani.examplemodellib.domain.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@Builder
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class CalculationBasisOptionGroupResponse {
    @ApiModelProperty(value = "Código do tipo da base de cálculo", example = "1")
    private Long id;

    @ApiModelProperty(value = "Nome do tipo base de cálculo", example = "Forecast")
    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @EqualsAndHashCode.Exclude
    @ApiModelProperty(value = "Opções da base de cálculo", example = "")
    private List<CalculationBasisOptionResponse> options;
}
