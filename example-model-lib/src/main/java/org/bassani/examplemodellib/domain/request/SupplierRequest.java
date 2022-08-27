package org.bassani.examplemodellib.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SupplierRequest implements Serializable {

    @ApiModelProperty(value = "Código do Fornecedor", example = "1418")
    @Positive
    @NotNull
    private Long id;

    @ApiModelProperty(value = "Nome do Fornecedor", example = "PONTO FRIO")
    @Size(max = 50)
    @NotBlank
    private String name;

    @ApiModelProperty(value = "Código do fornecedor pai", example = "1418")
    @Positive
    private Long parentSupplierId;

    @ApiModelProperty(value = "Nome do fornecedor pai", example = "PONTO FRIO")
    private String parentSupplierName;
}
