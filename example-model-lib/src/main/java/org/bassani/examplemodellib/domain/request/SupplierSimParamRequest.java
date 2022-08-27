package org.bassani.examplemodellib.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Builder
@NoArgsConstructor
@Data
@AllArgsConstructor
public class SupplierSimParamRequest {

    @ApiModelProperty(value = "Código da Simulação", example = "10")
    @Positive
    @NotNull
    private Long id;
    
	@ApiModelProperty(value = "Id do Fornecedor", example = "12")
	@Positive
	@NotNull
	private Long supplierId;
}