package org.bassani.examplemodellib.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseBiRequest implements Serializable {

    private static final long serialVersionUID = -8666813555453372720L;

    @ApiModelProperty(value = "Lista de Código do Produto", example = "[2,331]")
    @NotEmpty
    private List<Long> productIds;
}
