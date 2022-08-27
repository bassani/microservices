package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SendOrdersResponse {

    @ApiModelProperty(value = "Id da simulacao")
    private Long simulationId;

    @ApiModelProperty(value = "Descricao do status que indica o resultado do envio dos pedidos")
    private String statusName;

    @ApiModelProperty(value = "Id do keycloak do usuario fez o envio dos pedidos")
    private String keycloakUserId;
}
