package org.bassani.examplemodellib.domain.projection;

public interface PaymentTermInstallmentProjection {

	Long getConditionPaymentCode();
	
	String getConditionPaymentDescription();

    Long getDaysQuantityPayment();
}
