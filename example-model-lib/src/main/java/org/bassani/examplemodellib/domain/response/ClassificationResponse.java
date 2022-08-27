package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class ClassificationResponse {
    @Positive
    @NotNull
    @ApiModelProperty(value = "Código do classificação", example = "10")
    private Long id;
 
    @ApiModelProperty(value = "Nome da classificação", example = "PBM")
    @NotBlank
	@Size(max = 50)
	private String name;
	
    @ApiModelProperty(value = "Estado da classificação do pedido de compra", 
			example = "habilitado-desabilitado")
	@NotBlank
	private String active;
	
	@ApiModelProperty(value = "Data de cadastro da classificação do pedido de compra")
	private LocalDateTime registerDate;
	
	@ApiModelProperty(value = "Operador de cadastrou a classificação do pedido de compra", example = "10")
	private Long registrationOperator;
	
	@ApiModelProperty(value = "Data de atualização da classificação do pedido de compra")
	private LocalDateTime updateDate;
	
	@ApiModelProperty(value = "Operado que atualizou a classificação do pedido de compra", example = "10")
	private Integer updateOperator;
	
	@ApiModelProperty(value = "Descrição da classificação do pedido de compra")
	@Size(max = 100)
	private String description;
}
