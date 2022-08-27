package org.bassani.examplemodellib.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalesHistoricRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(name = "salesDays", required = true)
	@NotNull
	private Long salesDays;

	@ApiModelProperty(name = "productId", required = true)
	@NotNull
	private Long productId;

	@ApiModelProperty(name = "distributionCenterId", required = true)
	@NotNull
	private Long distributionCenterId;
}
