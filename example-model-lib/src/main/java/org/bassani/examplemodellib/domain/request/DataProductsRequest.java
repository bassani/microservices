package org.bassani.examplemodellib.domain.request;

import br.com.example.purchasesimulatormodellib.enums.TemporaryInactiveEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataProductsRequest implements Serializable {

	private static final long serialVersionUID = 1L;
		
	@ApiModelProperty(name = "supplierId", required = true)
	private Long supplierId;
	
	@ApiModelProperty(name = "isInactive", required = true)
	private Boolean isInactive;

    @ApiModelProperty(name = "temporaryInactiveCode", required = true)
    @NotNull
    private TemporaryInactiveEnum temporaryInactiveCode;

	@ApiModelProperty(name = "isKitPromo", required = true)
	private Boolean isKitPromo;

	@ApiModelProperty(name = "categoryId")
	private List<Long> categoryIds;

	@ApiModelProperty(name = "subCategoryId")
	private List<Long> subCategoryIds;
	
	@ApiModelProperty(name = "distributionCenterId", required = true)
	@NotNull
	private Long distributionCenterId;

}
