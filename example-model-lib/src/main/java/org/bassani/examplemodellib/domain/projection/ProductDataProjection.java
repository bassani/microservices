package org.bassani.examplemodellib.domain.projection;

import java.time.LocalDate;

public interface ProductDataProjection {
	Long getProductId();
	String getProductDescription();
	Long getCategoryId();
	Long getSubCategoryId();
	Integer getIsInactive();
	Integer getIsKitPromo();
	LocalDate getDateCreated();
	Double getCommercialDiscountPc();
	String getProductProviderCode();
	Integer getSubGroupId();
	Long getEan();
	String getCurveFis();
    Long getTemporaryInactiveCode();
}
