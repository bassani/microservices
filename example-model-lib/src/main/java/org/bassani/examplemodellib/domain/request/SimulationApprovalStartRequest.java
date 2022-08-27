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
public class SimulationApprovalStartRequest implements Serializable {

    private static final long serialVersionUID = 840120578611071982L;

    @ApiModelProperty(value = "Id do Operador", example = "1")
    private Long operatorId;

    @NotNull(message = Constants.VALID_ANNOTATION_NOT_NULL)
    @ApiModelProperty(value = "Id do Operador", example = "{123-321}")
    private String keycloakUserId;

    @NotNull(message = Constants.VALID_ANNOTATION_NOT_NULL)
    @ApiModelProperty(value = "ID da Area", example = "1")
    private Long areaId;

    @NotNull(message = Constants.VALID_ANNOTATION_NOT_NULL)
    @ApiModelProperty(value = "ID do Perfil", example = "1")
    private Long profileId;

}
