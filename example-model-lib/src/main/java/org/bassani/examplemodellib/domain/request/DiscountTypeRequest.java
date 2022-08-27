package org.bassani.examplemodellib.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class DiscountTypeRequest {
    
	@ApiModelProperty(value = "Campo de busca em todos os campos", example = "1 | Negociação")
    private String query;
    
    @ApiModelProperty(value = "Código do tipo de desconto", example = "10")
    @Positive
    private Long id;
    
    @ApiModelProperty(value = "Nome do tipo de desconto", example = "PBM")
    @NotBlank
	@Size(max = 50)
	private String name;
	
	@ApiModelProperty(value = "Estado do tipo de desconto", example = "TRUE-FALSE")
	@NotBlank
	private String active;

	@ApiModelProperty(value = "Operador de cadastrou o tipo de desconto", example = "10")
	@NotNull
	private Long registrationOperator;
}