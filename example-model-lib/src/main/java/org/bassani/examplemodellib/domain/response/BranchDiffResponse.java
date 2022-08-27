package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BranchDiffResponse implements Serializable {

    private static final long serialVersionUID = -6959230508222001475L;

    @ApiModelProperty(value = "ID do produto", example = "1")
    private Long productId;

    @ApiModelProperty(value = "ID do Centro de Distribuição", example = "900")
    private Long distributionCenterId;

    @ApiModelProperty(value = "Número da diferença produto na filial", example = "100")
    private Long branchDifference;
}
