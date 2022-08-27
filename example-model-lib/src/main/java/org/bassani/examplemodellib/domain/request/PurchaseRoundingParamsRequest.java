package org.bassani.examplemodellib.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class PurchaseRoundingParamsRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(name = "distributionCenterId", value = "Código do CD", example = "900", required = true)
	@Positive
	@NotNull
	private Long distributionCenterId;

	@ApiModelProperty(name = "supplierId", value = "Código do Fornecedor", example = "123321", required = true)
	@Positive
	@NotNull
	private Long supplierId;

	@ApiModelProperty(name = "productIds", value = "Códigos dos produtos", example = "[2, 331, 100145]", required = true)
	@NotNull
	private List<Long> productIds;

}
