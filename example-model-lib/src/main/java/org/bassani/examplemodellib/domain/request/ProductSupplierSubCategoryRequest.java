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

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductSupplierSubCategoryRequest implements Serializable {

    private static final long serialVersionUID = 8217286762213828352L;

    @ApiModelProperty(value = "Lista de ID´s de Fornecedor", example = "[7381, 32112,...]")
    @NotEmpty(message = Constants.VALID_ANNOTATION_NOT_EMPTY_LIST)
    private List<Long> supplierIds;

    @ApiModelProperty(value = "Lista de ID´s de Categorias", example = "[7381, 32112,...]")
    @NotEmpty(message = Constants.VALID_ANNOTATION_NOT_EMPTY_LIST)
    private List<Long> categoriesIds;

}
