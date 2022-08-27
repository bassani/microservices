package org.bassani.examplemodellib.domain.request;

import br.com.example.purchasesimulatormodellib.enums.SimulationTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParameterSimulationRequest {

	@ApiModelProperty(value = "Id", example = "10")
	@Positive
	private Long id;
	
	@ApiModelProperty(value = "Código da categoria", example = "11")
	private List<Long> categoryIds;
	
	@ApiModelProperty(value = "Código da sub-categoria", example = "12")
	private List<Long> subCategoryIds;
	
	@ApiModelProperty(value = "Tipo de simulação")
	@NotNull
	private SimulationTypeEnum simulationType;
	
	@ApiModelProperty(value = "Código do tipo de pedido de compra", example = "13")
	@NotNull
	private Long purchaseOrderTypeId;
	
	@ApiModelProperty(value = "Classificação de pedido de compra")
	@NotNull
	private Long classification;
	
	@ApiModelProperty(value = "Código da condição de pagamento", example = "14")
	@NotNull
	private Long paymentTermId;
	
	@ApiModelProperty(value = "Observação")
	@NotBlank
	private String note;
	
	@ApiModelProperty(value = "Base de cálculo")
	@NotNull
	private Long calculationBasis;
	
	@ApiModelProperty(value = "QT dia de venda", example = "7 / 15 / 30 / 60")
	private Long idSaleDay;
	
	@ApiModelProperty(value = "Flag apenas inativos", example = "TRUE / FALSE")
	@NotNull
	private Boolean inactive;
	
	@ApiModelProperty(value = "Promo Pack")
	@NotNull
	private Boolean promoPack;
	
	@ApiModelProperty(value = "Cotados")
	@NotNull
	private Boolean quoted;
	
	@ApiModelProperty(value = "Código do operador", example = "16")
	@NotNull
	private Long operatorId;
	
	@ApiModelProperty(value = "Data de cadastro")
	private LocalDate registerDate;
}