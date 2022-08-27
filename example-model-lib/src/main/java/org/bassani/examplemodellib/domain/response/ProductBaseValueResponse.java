package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class ProductBaseValueResponse implements Serializable {

    private static final long serialVersionUID = -2616532864320580241L;

    @ApiModelProperty(value = "ID do produto", example = "1")
    Long productId;

    @ApiModelProperty(value = "ID da filial/centro distribuição", example = "1")
    Long branchId;

    @ApiModelProperty(value = "Valor base máximo", example = "3")
    Long maxBaseValue;
}