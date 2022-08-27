package org.bassani.examplemodellib.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AntecipationTypeSimulationRequest {

	@ApiModelProperty(value = "ID do parametro de simulação", example = "1")
	@NotNull
	private Long parameterSimulation;
	
	@ApiModelProperty(value = "Data de antecipação", example = "01/01/2021")
	@NotNull
	private LocalDate antecipation;
}
