package org.bassani.examplemodellib.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuantityPendingOrdersRequest implements Serializable {

	private static final long serialVersionUID = 1L;
		
	@ApiModelProperty(name = "Centro de Distribuição", required = true, example = "22")
	@NotNull
	@Positive(message = "Centro de Distribuição não pode ser negativo")
	private Long distributionCenterId;

	@ApiModelProperty(name = "Código de produto", required = true, example = "258")
	@NotNull
	private List<Long> productIds;
}
