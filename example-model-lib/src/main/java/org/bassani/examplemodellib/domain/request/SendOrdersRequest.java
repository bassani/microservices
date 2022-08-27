package org.bassani.examplemodellib.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SendOrdersRequest {

    @NotEmpty
    @ApiModelProperty(value = "Lista de numeros de pedidos",  example = "[98103, 98104]")
    private List<Long> orderNumbers;

    @NotBlank
    @ApiModelProperty(value = "Id do keycloak do usuario fez o envio dos pedidos")
    private String keycloakUserId;
}
