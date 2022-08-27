package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class PurchaseOrderTypeResponse {

    @ApiModelProperty(value = "ID do tipo de pedido de compra", example = "123")
    Integer purchaseOrderTypeId;

    @ApiModelProperty(value = "Nome do tipo de pedido de compra", example = "Normal - Antecipado - Bonificação - Multicanal")
    String purchaseOrderTypeName;

}
