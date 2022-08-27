package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ScheduleSupplierResponse {

	@ApiModelProperty(value = "", example = "")
	private Long supplierScheduleId;

	private Long breakDaysQt;

	private Long dayWeek;

	private Boolean isPurchase;

	private Long supplierId;

	private Long regionalId;

	private Long manufacturerId;

	private Long typePurchaseOrderId;
}
