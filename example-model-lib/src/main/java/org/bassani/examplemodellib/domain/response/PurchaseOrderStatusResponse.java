package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@RedisHash(value = "purchase:order:status")
public class PurchaseOrderStatusResponse implements Serializable {

    private static final long serialVersionUID = -3425399478135524693L;

    @ApiModelProperty(value = "ID do Status do Pedido", example = "2")
    Integer purchaseOrderStatusId;

    @ApiModelProperty(value = "Nome do Status do Pedido", example = "PEDIDO DE COMPRA EMITIDO")
    String purchaseOrderStatusName;

    @ApiModelProperty(value = "Flag de Status ativo", example = "1")
    Boolean activeFlag;
    
    @ApiModelProperty(value = "Flag de transmissao", example = "1")
    Boolean transmitFlag;

}
