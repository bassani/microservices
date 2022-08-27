package org.bassani.examplemodellib.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CalculationBasisRequest {
    
	@ApiModelProperty(value = "Campo de busca em todos os campos", example = "1 | Negociação")
    private String query;
    
    @ApiModelProperty(value = "Código da base de cálculo", example = "10")
    @Positive
    private Long id;
    
    @ApiModelProperty(value = "Nome da base de cálculo", example = "PBM")
    @NotBlank
	@Size(max = 50)
	private String name;
	
	@ApiModelProperty(value = "Estado da base de cálculo", example = "TRUE-FALSE")
	@NotBlank
	private String active;

	@ApiModelProperty(value = "Operador de cadastrou a base cálculo", example = "10")
	@NotNull
	private Long registrationOperator;
}