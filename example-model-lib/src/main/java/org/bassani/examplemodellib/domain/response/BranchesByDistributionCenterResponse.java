package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BranchesByDistributionCenterResponse implements Serializable {

    private static final long serialVersionUID = -786827411805844001L;

    @ApiModelProperty(value = "ID do centro de distribuição", example = "1")
    private Long distributionCenterId;

    @ApiModelProperty(value = "IDs das filiais", example = "[1, 2]")
    private List<Long> branchIds;
}