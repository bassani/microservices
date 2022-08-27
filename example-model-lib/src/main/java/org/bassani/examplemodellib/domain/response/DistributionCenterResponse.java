package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DistributionCenterResponse implements Serializable {

    private static final long serialVersionUID = -3309253461907591038L;

    @ApiModelProperty(value = "ID do Centro de Distribuição", example = "900")
    private Long id;

    @ApiModelProperty(value = "Nome do Centro de Distribuição", example = "CD SÃO PAULO")
    private String name;

}

