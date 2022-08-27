package org.bassani.examplemodellib.domain.response;

import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.ClassificationEntity;
import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.SimulationStatusEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SimulationPendingApprovalResponse {

    @ApiModelProperty(value = "Data da simulação")
    private LocalDate registerDate;

    @ApiModelProperty(value = "Data da expiração")
    private LocalDateTime expirationDate;

    @ApiModelProperty(value = "Data de envio para aprovação")
    private LocalDate startedApprovalDate;

    @ApiModelProperty(value = "Id da simulação")
    private Long simulationId;

    @ApiModelProperty(value = "Código do usuário da simulação (operador)", example = "16")
    private Long operatorId;

    @ApiModelProperty(value = "Usuário da simulação")
    private String operatorName;

    @ApiModelProperty(value = "Classificação")
    private ClassificationEntity classification;

    @ApiModelProperty(value = "Fornecedor Pai")
    private List<SupplierSimParamResponse> suppliers;

    @ApiModelProperty(value = "Valor Total")
    private Double totalValue;

    @ApiModelProperty(value = "Status da Simulação")
    private SimulationStatusEntity status;

    @ApiModelProperty(value = "Keycloak id usuário criador da simulação")
    private String keycloakUserId;

    @ApiModelProperty(value = "Usuário criador da simulação")
    private String simulationUserName;

}
