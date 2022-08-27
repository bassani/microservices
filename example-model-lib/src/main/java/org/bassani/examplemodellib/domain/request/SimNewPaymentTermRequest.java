package org.bassani.examplemodellib.domain.request;

import br.com.example.purchasesimulatormodellib.constraints.AtMostOneNotNull;
import br.com.example.purchasesimulatormodellib.enums.NewPaymentTermTypeEnum;
import br.com.example.purchasesimulatormodellib.util.Constants;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@AtMostOneNotNull({"newPaymentTermGeneral", "newPaymentTermDCs"})
public class SimNewPaymentTermRequest implements Serializable {

    private static final long serialVersionUID = 8992759786761531614L;

    @NotNull(message = Constants.VALID_ANNOTATION_NOT_NULL)
    @ApiModelProperty(value = "Tipo do Novo Prazo de Pagamento", example = "1 Geral ou 2 Por CD")
    private NewPaymentTermTypeEnum newPaymentTermType;

    @ApiModelProperty(value = "Novo Prazo de Pagamento Geral", example = "1 Geral ou 2 Por CD")
    private SimNewPaymentTermGeneralRequest newPaymentTermGeneral;

    @ApiModelProperty(value = "Novo Prazo de Pagamento Por Centro de Distribuição", example = "1 Geral ou 2 Por CD")
    private List<SimNewPaymentTermDCRequest> newPaymentTermDCs;

}