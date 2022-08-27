package org.bassani.examplemodellib.domain.request.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class OrderTypeRequestParams {

    @ApiModelProperty(value = "Busca em todos os campos", example = "1 | Negociação")
    private String query;

    @ApiModelProperty(value = "Código do tipo de pedido", example = "10")
    private Long id;

    @ApiModelProperty(value = "Nome do tipo de pedido", example = "PBM")
    private String name;
}
