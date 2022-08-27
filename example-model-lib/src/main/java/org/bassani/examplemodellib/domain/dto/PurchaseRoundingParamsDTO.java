package org.bassani.examplemodellib.domain.dto;

import br.com.example.purchasesimulatormodellib.domain.projection.PurchaseRoundingParamsProjection;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class PurchaseRoundingParamsDTO implements Serializable {

	private static final long serialVersionUID = 6564908116255125808L;

	Long productId;

	Long qtyMultipleBilling;

	BigDecimal percentageRoundingSuggestion;

	public PurchaseRoundingParamsDTO(PurchaseRoundingParamsProjection projection) {
		setProductId(projection.getProductId());
		setQtyMultipleBilling(projection.getQtyMultipleBilling());
		setPercentageRoundingSuggestion(projection.getPercentageRoundingSuggestion());
	}

}
