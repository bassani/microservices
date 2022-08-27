package org.bassani.examplemodellib.domain.request;

import br.com.example.purchasesimulatormodellib.util.Constants;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SimulationApprovalCompleteRequest implements Serializable {

    private static final long serialVersionUID = 386683135923681377L;

    @ApiModelProperty(value = "Id do Operador", example = "1")
    private Long operatorId;

    @ApiModelProperty(value = "Id do Usuário no KeyCloak", example = "9669b7c7-4fea-480c-84db-11537e0110b2")
    private String keycloakUserId;

    @ApiModelProperty(value = "ID da Area", example = "1")
    private Long areaId;

    @ApiModelProperty(value = "ID do Perfil", example = "1")
    private Long profileId;

    @NotNull(message = Constants.VALID_ANNOTATION_NOT_NULL)
    @ApiModelProperty(value = "Aprovado ou Reprovado", example = "true")
    private Boolean approved;

    @ApiModelProperty(value = "Motivo caso reprovado", example = "1")
    private String reason;

}
