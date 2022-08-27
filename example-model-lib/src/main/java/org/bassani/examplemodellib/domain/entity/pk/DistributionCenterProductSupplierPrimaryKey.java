package org.bassani.examplemodellib.domain.entity.pk;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class DistributionCenterProductSupplierPrimaryKey implements Serializable {
    private static final long serialVersionUID = -4111648484723561769L;
    private Long supplierId;
    private Long productId;
    private Long distributionCenterId;
}
