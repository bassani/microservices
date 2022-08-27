package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class CalculationBasisResponse {

	@ApiModelProperty(value = "Id", example = "10")
	private Integer id;
	
	@ApiModelProperty(value = "Nome da base de cálculo", example = "ICMS")
	private String name;
	
	@ApiModelProperty(value = "Flag de ativo", example = "TRUE")
	private Boolean active;
	
	@ApiModelProperty(value = "Data de criação", example = "01/01/2021")
	private LocalDate registerDate;
	
	@ApiModelProperty(value = "Código operador de criação", example = "101")
    private Long registerOperator;

	@ApiModelProperty(value = "Data de atualização", example = "01/01/2021")
    private LocalDateTime updateDate;

	@ApiModelProperty(value = "Código operador de atualização", example = "101")
    private Long updateOperator;
}
