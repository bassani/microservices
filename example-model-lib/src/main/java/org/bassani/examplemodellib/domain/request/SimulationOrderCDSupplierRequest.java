package org.bassani.examplemodellib.domain.request;

import br.com.example.purchasesimulatormodellib.util.Constants;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SimulationOrderCDSupplierRequest implements Serializable {
    private static final long serialVersionUID = -406030682806226687L;

    @NotNull(message = Constants.VALID_ANNOTATION_NOT_NULL)
    @ApiModelProperty(value = "Centro de Distribuição")
    private DistributionCenterRequest distributionCenter;

    @NotNull(message = Constants.VALID_ANNOTATION_NOT_NULL)
    @ApiModelProperty(value = "Fornecedor")
    private SupplierRequest supplierRequest;

    @NotNull(message = Constants.VALID_ANNOTATION_NOT_NULL)
    @ApiModelProperty(value = "Fornecedor de Faturamento")
    private BillingSupplierRequest billingSupplier;

    @NotNull(message = Constants.VALID_ANNOTATION_NOT_NULL)
    @ApiModelProperty(value = "Indica se é para emitir pedidos deste CD/Fornecedor", example = "TRUE-FALSE")
    private Boolean flagDispatch;

    @NotNull(message = Constants.VALID_ANNOTATION_NOT_NULL)
    @ApiModelProperty(value = "Valor total do pedido", example = "10000.00")
    private BigDecimal totalValue;


}
