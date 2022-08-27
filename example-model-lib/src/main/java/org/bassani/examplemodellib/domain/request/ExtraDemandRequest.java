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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExtraDemandRequest implements Serializable {

    private static final long serialVersionUID = -242035549049038760L;

    @ApiModelProperty(value = "ID do centro de distribuição", example = "1")
    @Positive
    @NotNull
    private Long distributionCenterId;

    @ApiModelProperty(value = "IDs dos produtos", example = "1")
    @NotEmpty
    private List<Long> productIds;
}
