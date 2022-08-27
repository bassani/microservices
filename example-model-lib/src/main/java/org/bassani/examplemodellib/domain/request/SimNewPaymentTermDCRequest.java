package org.bassani.examplemodellib.domain.request;

import br.com.example.purchasesimulatormodellib.util.Constants;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SimNewPaymentTermDCRequest implements Serializable {

    private static final long serialVersionUID = 5611141509934634941L;

    @Positive
    @NotNull(message = Constants.VALID_ANNOTATION_NOT_NULL)
    @ApiModelProperty(value = "ID do Centro de Distribuicao", example = "900")
    private Long distributionCenter;

    @Positive
    @NotNull(message = Constants.VALID_ANNOTATION_NOT_NULL)
    @ApiModelProperty(value = "ID do Novo Prazo de Pagamento", example = "1")
    private Long newPaymentTermCode;

    @NotBlank(message = Constants.VALID_ANNOTATION_NOT_EMPTY_FIELD)
    @ApiModelProperty(value = "Descrição do Novo Prazo de Pagamento", example = "120 DD")
    private String newPaymentTermDescription;

    @Positive
    @NotNull(message = Constants.VALID_ANNOTATION_NOT_NULL)
    @ApiModelProperty(value = "Quantidade de Dias do Novo Prazo de Pagamento", example = "120")
    private Long daysQuantityPayment;

}
