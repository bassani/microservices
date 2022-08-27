package org.bassani.examplemodellib.domain.request;

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

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SimulationFollowUpRequest {

    @ApiModelProperty(value = "Data inicial do filtro de simulação")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate initialDate;

    @ApiModelProperty(value = "Data final do filtro de simulação")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate finalDate;

    @ApiModelProperty(value = "Usuário da simulação", example = "16")
    private Long operatorId;

    @ApiModelProperty(value = "Tipo de simulação")
    private Long simulationTypeId;

    @ApiModelProperty(value = "Classificação de pedido de compra")
    private Long classificationId;

    @ApiModelProperty(value = "Lista de Fabricantes")
    private List<Long> supplierIds;

    @ApiModelProperty(value = "Fornecedor pai")
    private List<Long> parentSupplierId;

    @ApiModelProperty(value = "Status da Simulação")
    private Long statusId;

    @ApiModelProperty(value = "Keycloak ids dos usuários criadores das simulações")
    private List<String> keycloakUserIds;

    public boolean filterByoperatorId() {
        return operatorId != null;
    }

}
