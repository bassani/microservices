package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SubCategorySimParamResponse {

	@ApiModelProperty(value = "ID do parametro de simulação", example = "1")
	private Long parameterSimulation;
	
	@ApiModelProperty(value = "Código da sub-categoria", example = "2")
	private Long subCategoryId;
}
