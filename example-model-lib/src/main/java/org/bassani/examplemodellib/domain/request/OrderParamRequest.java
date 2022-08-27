package org.bassani.examplemodellib.domain.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderParamRequest implements Serializable {

    private static final long serialVersionUID = -3267932669118833284L;

    @ApiModelProperty(value = "Data inicial do filtro de emissão de pedido")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate initialDate;

    @ApiModelProperty(value = "Data final do filtro de emissao do pedido")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate finalDate;

    @ApiModelProperty(value = "Id do pedido de compra")
    private Long orderId;

    @ApiModelProperty(value = "Número do pedido de compra")
    private Long orderNumber;

    @ApiModelProperty(value = "Lista de usuários do pedido de compra")
    private List<Long> operatorIds;

    @ApiModelProperty(value = "Lista de fabricantes")
    private List<Long> supplierIds;

    @ApiModelProperty(value = "Lista de fornecedores pai")
    private List<Long> parentSupplierIds;

    @ApiModelProperty(value = "Lista de categorias")
    private List<Long> categoryIds;

}
