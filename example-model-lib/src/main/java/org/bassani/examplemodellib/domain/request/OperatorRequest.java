package org.bassani.examplemodellib.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OperatorRequest implements Serializable {

    @ApiModelProperty(value = "Código do operador", example = "123")
    private Long code;

    @ApiModelProperty(value = "KeycloakUserId", example = "9669b7c7-4fea-480c-84db-11537e0110b2")
    private String keycloakUserId;

    @ApiModelProperty(value = "Nome do operador", example = "Amanda")
    private String name;

    @ApiModelProperty(value = "Código da Área", example = "123")
    private Long areaCode;

    @ApiModelProperty(value = "Código do Cargo", example = "123")
    private Long positionCode;

    @ApiModelProperty(value = "Email do operador", example = "operador@rd.com.br")
    private String email;

    @ApiModelProperty(value = "Telefone do operador", example = "11989891234")
    private String phoneNumber;

    @ApiModelProperty(value = "Perfil de acesso do operador", example = "1")
    private String accessProfile;

    @ApiModelProperty(value = "Status da conta do operador", example = "true")
    private String activationStatus;

    @ApiModelProperty(value = "Data de retorno de férias do operador", example = "2022-01-31")
    private String vacationReturnDate;

    public boolean filterByCode() {
        return code != null;
    }

    public boolean filterByName() {
        return name != null && !name.isEmpty();
    }

    public boolean filterByAreaCode() {
        return areaCode != null;
    }

    public boolean filterByPositionCode() {
        return positionCode != null;
    }

    public boolean filterByEmail() {
        return email != null && !email.isEmpty();
    }

}
