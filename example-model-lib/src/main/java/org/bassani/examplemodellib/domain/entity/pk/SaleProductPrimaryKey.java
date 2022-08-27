package org.bassani.examplemodellib.domain.entity.pk;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
public class SaleProductPrimaryKey implements Serializable {
    Integer productId;
    LocalDate date;
    Integer regionalDistributionCenterId;
}
