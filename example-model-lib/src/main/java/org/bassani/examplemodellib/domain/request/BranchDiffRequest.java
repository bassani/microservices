package org.bassani.examplemodellib.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BranchDiffRequest implements Serializable {
    private static final long serialVersionUID = 6120621118895313601L;

    @ApiModelProperty(value = "Código do Centro de Distribuição", example = "900")
    @Positive(message = "Centro de Distribuição não pode ser negativo")
    @NotNull
    private Long distributionCenterId;

    @ApiModelProperty(value = "Lista de Código do Produto", example = "[2,331]")
    @NotEmpty
    private List<Long> productIds;
}
