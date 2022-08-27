package org.bassani.examplemodellib.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSupplierResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5236278710261459065L;

	private Long productId;

	private String productDescription;

}
