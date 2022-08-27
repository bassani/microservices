package org.bassani.examplemodellib.domain.request;

import br.com.example.purchasesimulatormodellib.constraints.IfEqualLongRequireNonNull;
import br.com.example.purchasesimulatormodellib.enums.SalesByPeriodEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@IfEqualLongRequireNonNull(sourceField = "id", equalsTo = {1L}, notNullField = "days")
public class SimCalculationBasisRequest implements Serializable {

    @ApiModelProperty(value = "Código da base de cálculo", example = "1")
    @Positive
    @NotNull
    private Long id;

    @ApiModelProperty(value = "Dias da base de cálculo", example = "7")
    private SalesByPeriodEnum days;
}