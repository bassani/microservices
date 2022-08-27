package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class OperatorSupplierResponse {

	@ApiModelProperty(value = "", example = "")
	private Long supplierId;

	private Long operatorId;

	private Long regionalId;

	private Long manufacturerId;
}
