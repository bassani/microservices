package org.bassani.examplemodellib.domain.dto;

import br.com.example.purchasesimulatormodellib.enums.BudgetTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BudgetDTO implements Serializable {

    private static final long serialVersionUID = 9170104176079610964L;

    @NotNull
    private BudgetTypeEnum type;

    @NotNull
    @Positive
    private BigDecimal value;

    @NotBlank
    @Size(max = 50)
    private String reason;
}
