package org.bassani.examplemodellib.domain.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class MenuResponse {

    @ApiModelProperty(value = "Código do Menu", example = "10")
    @Positive
    @NotNull
    private Integer id;

    @ApiModelProperty(value = "Descrição do Menu", example = "Aprovação | Envio")
    @Size(max = 50)
    @NotNull
    private String name;

    @ApiModelProperty(value = "Nome do ícone", example = "cog")
    @Size(max = 100)
    @NotNull
    private String icon;

    @ToString.Exclude
    @ApiModelProperty(value = "Itens do menu")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<MenuItemResponse> items;
}
