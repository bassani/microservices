package org.bassani.examplemodellib.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class ClassificationRequest implements Serializable {

	private static final long serialVersionUID = -4278783804154491958L;

	@ApiModelProperty(value = "Campo de busca em todos os campos", example = "1 | Negociação")
    private String query;
    
    @ApiModelProperty(value = "Código do classificação", example = "10")
    @Positive
    private Long id;
    
    @ApiModelProperty(value = "Nome da classificação", example = "PBM")
    @Size(max = 50)
	//	Não permitir cadastrar números ou caracteres especiais;
	@Pattern(regexp = "[a-z-A-Z ]+$")
	private String name;

	@ApiModelProperty(value = "Estado da classificação do pedido de compra", example = "habilitado-desabilitado")
	@NotBlank
	private String active;

	@ApiModelProperty(value = "Operador de cadastrou a classificação do pedido de compra", example = "10")
	@NotNull
	private Long registrationOperator;

	@ApiModelProperty(value = "Descrição da classificação do pedido de compra")
	@Size(max = 100)
	private String description;

}
