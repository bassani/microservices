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
public class SimulationApprovalFlowRequest implements Serializable {

    private static final long serialVersionUID = 3111420024120615846L;

    @NotNull(message = Constants.VALID_ANNOTATION_NOT_NULL)
    @ApiModelProperty(value = "ID da Simulação", example = "1")
    private Long simulationId;

    @NotNull(message = Constants.VALID_ANNOTATION_NOT_NULL)
    @ApiModelProperty(value = "ID do Status da Simulação", example = "1")
    private Long simulationStatusId;

    @ApiModelProperty(value = "Id do Operador", example = "1")
    private Long operatorId;

    @NotNull(message = Constants.VALID_ANNOTATION_NOT_NULL)
    @ApiModelProperty(value = "Id do Usuário no KeyCloak", example = "9669b7c7-4fea-480c-84db-11537e0110b2")
    private String keycloakUserId;

    @NotNull(message = Constants.VALID_ANNOTATION_NOT_NULL)
    @ApiModelProperty(value = "ID da Area", example = "1")
    private Long areaId;

    @ApiModelProperty(value = "ID do Status do Fluxo de Aprovação da Simulação", example = "1")
    private Long simulationApprovalFlowStatusId;

    @NotNull(message = Constants.VALID_ANNOTATION_NOT_NULL)
    @ApiModelProperty(value = "ID do Perfil", example = "1")
    private Long profileId;

    @ApiModelProperty(value = "Motivo caso reprovado", example = "1")
    private String reason;

}
