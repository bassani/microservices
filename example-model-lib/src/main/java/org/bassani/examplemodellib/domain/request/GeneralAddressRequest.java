package org.bassani.examplemodellib.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneralAddressRequest implements Serializable {

	private static final long serialVersionUID = -284075491310230805L;

	@ApiModelProperty(value = "Código do Fornecedor", example = "10")
	@Positive
	private Long supplierId;
}
