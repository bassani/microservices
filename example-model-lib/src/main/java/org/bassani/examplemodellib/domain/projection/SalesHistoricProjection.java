package org.bassani.examplemodellib.domain.projection;

public interface SalesHistoricProjection {
	
	Long getDistributionCenterId();

	Long getProductId();

	Long getSaleQuantity();

	Long getSaleValue();
}
