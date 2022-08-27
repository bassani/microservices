package org.bassani.examplemodellib.domain.entity.pk;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class GrossProfitMarginPrimaryKey implements Serializable {
    private static final long serialVersionUID = 639973328383747127L;
    private Long periodMonthCode;
    private Long productId;
    private Long supplierId;
}
