package org.bassani.examplemodellib.domain.response;

import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.ClassificationEntity;
import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.SimulationStatusEntity;
import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.SimulationTypeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SimulationFollowUpResponse {

    @ApiModelProperty(value = "Id", example = "10")
    private Long id;

    @ApiModelProperty(value = "Data de cadastro")
    private LocalDateTime registerDate;

    @ApiModelProperty(value = "Código do operador", example = "16")
    private Long operatorId;

    @ApiModelProperty(value = "Nome do operador", example = "16")
    private String operatorName;

    @ApiModelProperty(value = "Tipo de simulação")
    private SimulationTypeEntity simulationType;

    @ApiModelProperty(value = "Classificação de pedido de compra")
    private ClassificationEntity classification;

    @ApiModelProperty(value = "Lista de Fabricantes")
    private List<SupplierSimParamResponse> suppliers;

    @ApiModelProperty(value = "Valor da Compra")
    private Double totalAmountWithNegotiatedDiscount;

    @ApiModelProperty(value = "Quantidade Total")
    private Integer orderedQty;

    @ApiModelProperty(value = "Status da Simulação")
    private SimulationStatusEntity status;

    @ApiModelProperty(value = "Keycloak id do aprovador final")
    private String keycloakFinalApproverId;

    @ApiModelProperty(value = "Aprovador final")
    private String finalApproverName;

    @ApiModelProperty(value = "Keycloak id usuário criador da simulação")
    private String keycloakUserId;

    @ApiModelProperty(value = "Usuário criador da simulação")
    private String simulationUserName;


}
