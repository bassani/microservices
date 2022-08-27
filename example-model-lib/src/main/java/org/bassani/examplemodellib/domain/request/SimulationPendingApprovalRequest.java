package org.bassani.examplemodellib.domain.request;

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
public class SimulationPendingApprovalRequest implements Serializable {

    private static final long serialVersionUID = 7816411800522998284L;

    @NotNull(message = Constants.VALID_ANNOTATION_NOT_NULL)
    @ApiModelProperty(value = "Data inicial do filtro de simulação pendente de aprovação")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate initialDate;

    @ApiModelProperty(value = "Data final do filtro de simulação pendente de aprovação")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate finalDate;

    @ApiModelProperty(value = "Id da simulação")
    private Long simulationId;

    @ApiModelProperty(value = "Lista de usuários de simulações")
    private List<Long> operatorIds;

    @ApiModelProperty(value = "Lista de fabricantes")
    private List<Long> supplierIds;

    @ApiModelProperty(value = "Lista de fornecedores pai")
    private List<Long> parentSupplierIds;

    @ApiModelProperty(value = "Lista de categorias")
    private List<Long> categoryIds;

    @ApiModelProperty(value = "Keycloak ids dos usuários criadores das simulações")
    private List<String> keycloakUserIds;

}
