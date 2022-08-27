package org.bassani.examplemodellib.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManufacturerRequest {

	@ApiModelProperty(value = "Código do Fabricante", example = "6")
	@Positive
	private Long id;

	@ApiModelProperty(value = "Nome do Fabricante", example = "ACHE")
	@Size(max = 50)
	private String name;

    @ApiModelProperty(value = "Códigos dos fornecedores pai", example = "8, 9, 10, 123")
    private List<Long> parentSupplierIds;

    @ApiModelProperty(value = "Nome do fornecedor pai", example = "6")
    private String parentSupplierName;

    @ApiModelProperty(value = "Código ou Nome do Fabricante", example = "1 | ACHE")
	private String query;

    @ApiModelProperty(value = "Se Fabricante pai = 1, senão = 0", example = "1 | 0")
    private Integer parent;

    @ApiModelProperty(value = "Se Flag compra = 1 identifica se o fornecedor entrara no processo automatico de envio de pedidos", example = "1 | 0")
    private boolean isBillingSupplier;

    @ApiModelProperty(value = "Lista de parametros para a busca de fornecedores de faturamento",
            example = "{'distributionCenterId' : 908, 'manufacterId' : 6}")
    private List<BillingSupplierParamRequest> billingSupplierSearchParams;

    public ManufacturerRequest(String query, boolean isBillingSupplier) {
        this.query = query;
        this.isBillingSupplier = isBillingSupplier;
    }

    public ManufacturerRequest(List<BillingSupplierParamRequest> billingSupplierSearchParams, boolean isBillingSupplier) {
        this.billingSupplierSearchParams = billingSupplierSearchParams;
        this. isBillingSupplier = isBillingSupplier;
    }

    public ManufacturerRequest(Long id, String name, List<Long> parentSupplierIds, String parentSupplierName,
                               String query, Integer parent) {
        this.id = id;
        this.name = name;
        this.parentSupplierIds = parentSupplierIds;
        this.parentSupplierName = parentSupplierName;
        this.query = query;
        this.parent = parent;
    }

    public ManufacturerRequest(Long id, String name, List<Long> parentSupplierIds, String parentSupplierName,
                               String query, Integer parent, boolean isBillingSupplier) {
        this.id = id;
        this.name = name;
        this.parentSupplierIds = parentSupplierIds;
        this.parentSupplierName = parentSupplierName;
        this.query = query;
        this.parent = parent;
        this.isBillingSupplier = isBillingSupplier;
    }
}
