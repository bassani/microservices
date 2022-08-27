package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubCategoryResponse {
    @ApiModelProperty(value = "Código da subcategoria", example = "7381")
    @Positive @NotNull
    private Long id;
    @ApiModelProperty(value = "Código da categoria", example = "3")
    @Positive @NotNull
    private Long categoryId;
    @ApiModelProperty(value = "Código da categoria exposição", example = "4")
    @Positive @NotNull
    private Long exhibitionCategoryId;
    @ApiModelProperty(value = "Descrição da subcategoria do produto", example = "SUPLEMENTOS")
    @NotNull
    private String name;
}
