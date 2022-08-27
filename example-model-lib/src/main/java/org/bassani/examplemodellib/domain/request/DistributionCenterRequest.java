package org.bassani.examplemodellib.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DistributionCenterRequest implements Serializable {

    @ApiModelProperty(value = "ID do Centro de Distribuição", example = "900")
    @Positive
    @NotNull
    private Long id;

    @ApiModelProperty(value = "Nome do Centro de Distribuição", example = "CD EMBU")
    @NotBlank
    private String name;

    public boolean filterByDistributionCenterId() {
        return id != null;
    }

    public boolean filterByDistributionCenterName() {
        return name != null;
    }
}
