package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponse {

	@ApiModelProperty(value = "Id do Perfil", example = "1")
	private Long id;
	
	@ApiModelProperty(value = "Nome do Perfil", example = "Analista")
	private String name;
	
}