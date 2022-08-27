package org.bassani.examplemodellib.domain.request;

import br.com.example.purchasesimulatormodellib.enums.TemporaryInactiveEnum;
import br.com.example.purchasesimulatormodellib.util.Constants;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class TotalQuantityProductsRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(name = "distributionCenter", required = true)
	@NotEmpty @NotNull(message = Constants.VALID_ANNOTATION_NOT_NULL)
	private List<Long> distributionCenterIds;

	@ApiModelProperty(name = "supplierId")
	private List<Long> supplierIds;

	@ApiModelProperty(name = "isInactive", required = true)
	@NotNull(message = Constants.VALID_ANNOTATION_NOT_NULL)
	private Boolean isInactive;

	@ApiModelProperty(name = "isKitPromo")
	private Boolean isKitPromo;

	@ApiModelProperty(name = "categoryId")
	private List<Long> categoryIds;

	@ApiModelProperty(name = "subCategoryId")
	private List<Long> subCategoryIds;

    @ApiModelProperty(name = "temporaryInactiveCode", required = true)
    @NotNull
    private TemporaryInactiveEnum temporaryInactiveCode;

}
