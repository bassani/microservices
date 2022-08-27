package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SupplierSimParamResponse {

	@ApiModelProperty(value = "Id", example = "10")
	private Long id;
	
	@ApiModelProperty(value = "Id do Fornecedor", example = "12")
	private Long supplierId;

    @ApiModelProperty(value = "Nome do Fornecedor", example = "ACHE")
    private String supplierName;

    @ApiModelProperty(value = "Id do Fornecedor Pai", example = "12")
    private Long parentSupplierId;

    @ApiModelProperty(value = "Nome do Fornecedor Pai", example = "ACHE")
    private String parentSupplierName;
}