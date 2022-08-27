package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MsProductResponse {
	
	@ApiModelProperty(value = "ID do Produto", example = "74333")
	private Long productId;

	@ApiModelProperty(value = "Código do produto no provedor", example = "74333")
	private String productProviderCode;
	
	@ApiModelProperty(value = "ID do SubGrupo", example = "74333")
	private Integer subGrouId;
	
	@ApiModelProperty(value = "Nome do SubGrupo", example = "74333")
	private String subGroupName;
	
	@ApiModelProperty(value = "EAN", example = "74333")
	private Long ean;
}
