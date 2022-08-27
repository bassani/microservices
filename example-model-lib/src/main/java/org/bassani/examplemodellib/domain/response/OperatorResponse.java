package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class OperatorResponse {
	
	@ApiModelProperty(value = "Código do operador", example = "123")
    private Long code;

    @ApiModelProperty(value = "Nome do operador", example = "Amanda")
    private String name;

    @ApiModelProperty(value = "Id do operador no KeyCloak", example = "553046d0-6365-46ca-999a-8c6a27c87cb8")
    private String keyCloakUserId;

	@ApiModelProperty(value = "Código da Área", example = "123")
    private Long areaCode;

    @ApiModelProperty(value = "Código do Cargo", example = "123")
    private Long positionCode;

    @ApiModelProperty(value = "Email do operador", example = "operador@rd.com.br")
    private String email;
    
}
