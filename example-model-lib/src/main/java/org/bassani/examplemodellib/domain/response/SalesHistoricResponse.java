package org.bassani.examplemodellib.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalesHistoricResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long distributionCenterId;

	private Long productId;

	private Long saleQuantity;

	private Long saleValue;

}
