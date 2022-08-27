package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CurveParameterResponse {

	@ApiModelProperty(value = "Curva do produto", example = "A")
	private String curvedProduct;
	
	@ApiModelProperty(value = "Código do centro de distribuição", example = "905")
	private Long distributionCenter;
	
	@ApiModelProperty(value = "Código do subgrupo", example = "1")
	private Long subGroup;
	
	@ApiModelProperty(value = "Caixa Fechada", example = "10")
	private Long closedBox;
	
	@ApiModelProperty(value = "Fator de segurança", example = "95")
	private Long safetyFactor;
}
