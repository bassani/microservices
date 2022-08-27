package org.bassani.examplemodellib.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubCategoryRequest implements Serializable {

    @ApiModelProperty(value = "Código da subcategoria", example = "7381")
    @Positive
    @NotNull
    private Long id;

    @ApiModelProperty(value = "Descrição da subcategoria do produto", example = "SUPLEMENTOS")
    @NotBlank
    private String name;

    @ApiModelProperty(value = "Id da Categoria do produto", example = "100")
    @Positive
    @NotNull
    private Long categoryId;
}
