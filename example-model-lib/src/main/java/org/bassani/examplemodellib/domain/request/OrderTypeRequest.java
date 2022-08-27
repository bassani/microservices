package org.bassani.examplemodellib.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderTypeRequest implements Serializable {

    @ApiModelProperty(value = "Código do tipo de pedido", example = "10")
    @Positive
    @NotNull
    private Long id;

    @ApiModelProperty(value = "Nome do tipo de pedido", example = "PBM")
    @NotBlank
    private String name;
}
