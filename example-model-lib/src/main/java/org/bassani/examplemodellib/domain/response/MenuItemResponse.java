package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@Builder
public class MenuItemResponse {

    @ApiModelProperty(value = "Código do item do menu", example = "10")
    @Positive
    @NotNull
    private Integer id;

    @ApiModelProperty(value = "Descrição do item do menu", example = "Pendente de aprovação")
    @NotNull
    @Size(max = 50)
    private String name;

    @ApiModelProperty(value = "URL do item do menu", example = "/approval-pending")
    @Size(max = 50)
    private String url;

    @ApiModelProperty(value = "Item do menu ativo?", example = "true")
    @NotNull
    private Boolean active;

    @ApiModelProperty(value = "Identificador do ícone do item de menu", example = "favicon.ico")
    @Size(max = 50)
    private String icon;
}
