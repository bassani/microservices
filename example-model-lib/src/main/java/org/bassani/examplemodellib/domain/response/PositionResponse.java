package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PositionResponse {

    @ApiModelProperty(value = "Id do cargo/grupo no KeyCloak", example = "ac2c906e-7dfc-4b29-9471-36abc91f2992")
    private String id;

    @ApiModelProperty(value = "Nome do cargo/grupo", example = "Coordenador")
    private String name;

}
