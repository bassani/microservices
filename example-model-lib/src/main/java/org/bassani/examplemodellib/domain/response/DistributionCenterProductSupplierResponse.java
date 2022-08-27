package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@Builder
public class DistributionCenterProductSupplierResponse {

	@ApiModelProperty(value = "", example = "")
	private Long supplierId;

	private Long productId;

	private Long distributionCenterId;

	private Long roundingId;

	private Long operatorId;

	private BigDecimal listPriceValue;

	private Long boardingCxQt;

	private Long palletCmdQt;

	private Long palletQt;

	private Long regionalSupplyUnitQt;

	private Long displayCxQt;

	private Long roundingPc;

	private Double financialDescPc;

	private Double commercialDescPc;

	private Long display1CxPc;

	private Long displayCxPc;

	private Long boarding1CxPc;

	private Long boardingCxPc;

	private Long palletCmdPc;

	private Long palletPc;

	private Date lastOrderDate;

	private Date updateDate;

	private Date registerDate;
}
