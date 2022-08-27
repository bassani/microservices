package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class OrderTypeResponse {

    @ApiModelProperty(value = "Código do tipo de pedido", example = "10")
    private Long id;

    @ApiModelProperty(value = "Nome do tipo de pedido", example = "10")
    private String name;
}
