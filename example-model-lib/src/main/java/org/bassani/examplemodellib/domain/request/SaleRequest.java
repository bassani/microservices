package org.bassani.examplemodellib.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@Data
public class SaleRequest {

    @Positive
    @NotNull
	private Long id;

    @NotBlank
	private String name;
}
