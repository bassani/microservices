package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Builder
@Data
public class GeneralAddressResponse implements Serializable {

	private static final long serialVersionUID = 8251483369559608477L;

	@ApiModelProperty(value = "Código do Endereço", example = "6")
	public Long id;

	@ApiModelProperty(value = "Código do Fornecedor", example = "1001")
	private Long supplierId;
	
	@ApiModelProperty(value = "Endereço", example = "AVENIDA BENEDITO ANDRADE")
	public String address;

	@ApiModelProperty(value = "Número", example = "55")
	private String number;

	@ApiModelProperty(value = "Complemento", example = "SL 10")
	private String complement;

	@ApiModelProperty(value = "Bairro", example = "CENTRO")
	private String district;

	@ApiModelProperty(value = "Cidade", example = "SÃO PAULO")
	private String city;
	
	@ApiModelProperty(value = "CEP", example = "02415001")
	private String zipCode;

	@ApiModelProperty(value = "Estado", example = "SP")
	private String state;
}