package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class BranchPurchaseResponse {

	@ApiModelProperty(value = "ID da Filial", example = "1")
	Integer branchId;

	@ApiModelProperty(value = "ID do CD regional", example = "1")
	Integer regionalDistributionCenterId;

	@ApiModelProperty(value = "Pode realizar compras?", example = "true")
	Boolean canPurchase;
}
