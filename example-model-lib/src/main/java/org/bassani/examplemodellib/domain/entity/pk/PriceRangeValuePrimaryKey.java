package org.bassani.examplemodellib.domain.entity.pk;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@EqualsAndHashCode
public class PriceRangeValuePrimaryKey implements Serializable {
    Integer productId;
    Integer priceRangeId;
}
