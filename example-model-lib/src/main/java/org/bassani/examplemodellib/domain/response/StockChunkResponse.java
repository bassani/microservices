package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StockChunkResponse {

    @ApiModelProperty(value = "Produto", name = "productId", dataType = "Long", example = "8888")
    private Long idProduct;

    @ApiModelProperty(value = "Quantidade de estoque", name = "stockQuantity", dataType = "Long", example = "100")
    private Long quantity;

    @ApiModelProperty(value = "Filial", name = "branch", dataType = "Integer", example = "1041")
    private Long idbranch;

    @ApiModelProperty(value = "Id do Tipo", name = "idType", dataType = "Integer", example = "1041")
    private Long idType;

    @ApiModelProperty(value = "Descrição do Tipo", name = "typeDescription", dataType = "Integer", example = "1041")
    private String typeDescription;

}