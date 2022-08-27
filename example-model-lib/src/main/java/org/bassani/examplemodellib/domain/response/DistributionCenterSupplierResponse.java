package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class DistributionCenterSupplierResponse {

	@ApiModelProperty(value = "", example = "")
	private Long supplierId;

	private Long regionalId;

	private Long deliveryDaysQt;

	private Long frequencyMonDaysQt;

	private Long frequencyTueDaysQt;

	private Long frequencyWedDaysQt;

	private Long frequencyThuDaysQt;

	private Long frequencyFriDaysQt;

	private BigDecimal orderMinVl;
}
