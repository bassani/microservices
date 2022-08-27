package org.bassani.examplemodellib.domain.dto;

import br.com.example.purchasesimulatormodellib.enums.DiscountTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneralDiscountDTO implements Serializable {

    private static final long serialVersionUID = 870895979149827485L;

    @NotNull
    private DiscountTypeEnum type;

    @PositiveOrZero
    @NotNull
    private BigDecimal value;
}
