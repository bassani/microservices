package org.bassani.examplemodellib.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest implements Serializable {

    private static final long serialVersionUID = -7478082045682210658L;

    @ApiModelProperty(value = "Matrícula do Usuário", example = "2612250")
    private Long registrationNumber;

    @ApiModelProperty(value = "Id do usuário no Keycloak", example = "9669b7c7-4fea-480c-84db-11537e0110b2")
    private String keycloakUserId;

    @ApiModelProperty(value = "Nome do usuário", example = "Amanda")
    private String firstName;

    @ApiModelProperty(value = "Sobrenome do usuário", example = "Silva")
    private String lastName;

    @ApiModelProperty(value = "Nome Completo", example = "Silva")
    private String fullName;

    @ApiModelProperty(value = "Código da Área", example = "123")
    private Long areaCode;

    @ApiModelProperty(value = "Id do cargo/grupo no KeyCloak", example = "123")
    private String groupId;

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
}
