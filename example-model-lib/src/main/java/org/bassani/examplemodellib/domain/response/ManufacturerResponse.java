package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class ManufacturerResponse {

	@ApiModelProperty(value = "Código do Fabricante", example = "6")
	private Long id;

	@ApiModelProperty(value = "Nome do Fabricante", example = "ACHE")
	private String name;

    @ApiModelProperty(value = "Razão Social do Fabricante", example = "ACHE DO BRASIL")
    private String description;
	
	private String cnpjNumber;

	private String stateRegistrationNr;

	private Long orderFrequencyDaysQt;

	private Long securityMarginDaysQt;

	private Long breakOrderQt;

	private Double discountPc;

	private Boolean isDeleted;

	private Boolean isEdi;

	private Boolean isRevenueRegistrationStatus;

	private Boolean isBalanceDelivery;

	private String supplierEanId;

	private Long supplierTypeId;

	private Long paymentTermsId;

	private Long layoutId;

    private Long parentSupplierId;

    private String parentSupplierName;
}