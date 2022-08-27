package org.bassani.examplemodellib.domain.entity.pk;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@EqualsAndHashCode
public class DistributionCenterSupplierPrimaryKey implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long supplierId;

	private Long regionalId;
}
