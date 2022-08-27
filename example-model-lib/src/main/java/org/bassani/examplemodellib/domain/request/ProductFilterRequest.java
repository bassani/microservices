package org.bassani.examplemodellib.domain.request;

import br.com.example.purchasesimulatormodellib.enums.TemporaryInactiveEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductFilterRequest implements Serializable {

    @NotNull
	private Boolean onlyActive;

	private Boolean promoPack;
	
	private Boolean quoted;

    @NotNull
    @Valid
	private TemporaryInactiveEnum temporaryInactiveCode;

    @JsonIgnore
	public Boolean isInactive() {
		return !onlyActive;
	}
}
