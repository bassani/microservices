package org.bassani.examplemodellib.domain.dto;

import br.com.example.purchasesimulatormodellib.enums.DiscountTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDiscountDTO implements Serializable {

    private static final long serialVersionUID = -8532068738410691734L;

    @NotNull
    @Positive
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private DiscountTypeEnum type;

    @PositiveOrZero
    @NotNull
    private BigDecimal value;
}
