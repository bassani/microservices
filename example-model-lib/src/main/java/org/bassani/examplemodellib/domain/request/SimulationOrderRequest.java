package org.bassani.examplemodellib.domain.request;

import br.com.example.purchasesimulatormodellib.enums.TaxOperationTypeEnum;
import br.com.example.purchasesimulatormodellib.util.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SimulationOrderRequest implements Serializable {
    private static final long serialVersionUID = 7319258981445329944L;

    @ApiModelProperty(value = "Id da simulacao")
    private Long simulationId;

    @ApiModelProperty(value = "Id da operacao fiscal")
    private TaxOperationTypeEnum taxOperation;


    @NotNull(message = Constants.VALID_ANNOTATION_NOT_NULL)
    @ApiModelProperty(value = "Data do envio da emissao do pedido")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate orderDispatchDate;

    @ApiModelProperty(value = "Razao para nao enviar todos os pedidos")
    private String reasonPartialOrdering;

    @ApiModelProperty(value = "Tipo da simulacao")
    private Long purchaseOrderTypeId;

    @ApiModelProperty(value = "Id do operador")
    private Long operatorId;

    @ApiModelProperty(value = "Lista com os CD e Supllier da simulacao")
    private List<SimulationOrderCDSupplierRequest> simulationOrderCDSupplierRequest;
}
