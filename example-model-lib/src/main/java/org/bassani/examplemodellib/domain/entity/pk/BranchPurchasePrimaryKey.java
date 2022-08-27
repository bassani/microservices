package org.bassani.examplemodellib.domain.entity.pk;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@EqualsAndHashCode
public class BranchPurchasePrimaryKey implements Serializable {
    Integer branchId;
    Integer regionalDistributionCenterId;
}
