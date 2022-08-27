package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;

public class OrderTypeParamResponse {
    @ApiModelProperty(value = "Id", example = "10")
    private Long id;

    @ApiModelProperty(value = "Código do Tipo de pedido", example = "10")
    private Long orderTypeId;

    @ApiModelProperty(value = "Nome do tipo de pedido", example = "Negociação")
    private String name;
}
