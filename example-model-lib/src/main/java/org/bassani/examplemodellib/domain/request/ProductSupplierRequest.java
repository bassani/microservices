package org.bassani.examplemodellib.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSupplierRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7001531607254147920L;

	@NotNull
	private List<Long> supplierIds;

	@NotNull
	private List<Long> categoryIds;

	@NotNull
	private List<Long> subCategoryIds;
}
