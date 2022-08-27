package org.bassani.examplemodellib.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class PurchaseBaseValueRequest {

	@ApiModelProperty(value = "Lita de Produtos", example = "1, 2, 3")
	private List<Long> productIds;

	@ApiModelProperty(value = "Centro de Distribuição", example = "1444")
	private Long distributionCenterId;

}
