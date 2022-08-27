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
public class CategoryResponse {
    @ApiModelProperty(value = "Código da categoria", example = "7381")
    @Positive @NotNull
    private Long id;
    @ApiModelProperty(value = "Código da categoria master", example = "4")
    @Positive @NotNull
    private Long masterCategoryId;
    @ApiModelProperty(value = "Descrição da subcategoria do produto", example = "SUPLEMENTOS")
    @NotNull
    private String name;
}
