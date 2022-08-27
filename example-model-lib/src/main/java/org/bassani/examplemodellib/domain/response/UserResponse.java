package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class UserResponse implements Serializable {

    private static final long serialVersionUID = 8702306632730349046L;

    @ApiModelProperty(value = "Matrícula do Usuário", example = "2612250")
    private Long registrationNumber;

    @ApiModelProperty(value = "Id do usuário no Keycloak", example = "9669b7c7-4fea-480c-84db-11537e0110b2")
    private String keyCloakUserId;

    @ApiModelProperty(value = "Nome do usuário", example = "Amanda")
    private String firstName;

    @ApiModelProperty(value = "Sobrenome do usuário", example = "Silva")
    private String lastName;

    @ApiModelProperty(value = "Código da Área", example = "123")
    private Long areaCode;

    @ApiModelProperty(value = "Email do usuário", example = "operador@rd.com.br")
    private String email;

    @ApiModelProperty(value = "Telefone do usuário", example = "11994792736")
    private String phoneNumber;

    @ApiModelProperty(value = "Perfil de acesso", example = "123")
    private Long accessProfile;

    @ApiModelProperty(value = "Status de ativação", example = "true")
    private boolean activationStatus;

    @ApiModelProperty(value = "Data de retorno das férias", example = "01/01/2021")
    private LocalDate vacationReturnDate;

    public String getFullName() {
        return !StringUtils.isEmpty(lastName) ? firstName + " " + lastName : firstName;
    }
}
