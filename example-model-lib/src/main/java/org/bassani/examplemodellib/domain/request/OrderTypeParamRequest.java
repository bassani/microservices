package org.bassani.examplemodellib.domain.request;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class OrderTypeParamRequest {
    @ApiModelProperty(value = "ID do parametro de simulação", example = "1")
    @NotNull
    private Long simulationId;

    @ApiModelProperty(value = "Código do Tipo pedido", example = "23")
    @NotNull
    private Long orderTypeId;

    @ApiModelProperty(value = "Nome da Tipo pedido", example = "Negociação")
    @NotNull
    private Long orderTypeName;
}
