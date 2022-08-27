package org.bassani.examplemodellib.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest implements Serializable {

    @ApiModelProperty(value = "Código da categoria", example = "7381")
    @Positive
    @NotNull
    private Long id;

    @ApiModelProperty(value = "Descrição da categoria do produto", example = "SUPLEMENTOS")
    @NotBlank
    private String name;
}
