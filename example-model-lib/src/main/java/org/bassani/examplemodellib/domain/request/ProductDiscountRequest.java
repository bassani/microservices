package org.bassani.examplemodellib.domain.request;

import br.com.example.purchasesimulatormodellib.enums.DiscountTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDiscountRequest {

    @NotNull
    @Positive
    private Long id;

    @NotBlank
	private String name;

	private DiscountTypeEnum type; // 1 Adicionar | 2 Substituir

    @PositiveOrZero
    @NotNull
	private BigDecimal value;
}
