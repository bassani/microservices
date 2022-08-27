package org.bassani.examplemodellib.domain.request;

import br.com.example.purchasesimulatormodellib.util.Constants;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSupplierCategoryRequest implements Serializable {

    private static final long serialVersionUID = 2302806823916764760L;

    @ApiModelProperty(value = "Lista de ID´s de Fornecedor", example = "[7381, 32112,...]")
    @NotEmpty(message = Constants.VALID_ANNOTATION_NOT_EMPTY_LIST)
    private List<Long> supplierIds;

}
