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
public class SimulationTypeRequest {
    
	@ApiModelProperty(value = "Campo de busca em todos os campos", example = "1 | Negociação")
    private String query;
    
    @ApiModelProperty(value = "Código do tipo de simulação", example = "10")
    @Positive
    private Long id;
    
    @ApiModelProperty(value = "Nome do tipo de simulação", example = "PBM")
    @NotBlank
	@Size(max = 50)
	private String name;
	
	@ApiModelProperty(value = "Estado do tipo de simulação", example = "TRUE-FALSE")
	@NotBlank
	private String active;

	@ApiModelProperty(value = "Operador de cadastrou o tipo de simulação", example = "10")
	@NotNull
	private Long registrationOperator;
}