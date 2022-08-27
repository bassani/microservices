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

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SimNewPaymentTermGeneralRequest implements Serializable {

    private static final long serialVersionUID = 819037410088710135L;

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
