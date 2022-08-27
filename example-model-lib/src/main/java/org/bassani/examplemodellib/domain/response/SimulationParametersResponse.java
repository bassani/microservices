package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class SimulationParametersResponse {

	@ApiModelProperty(value = "Id", example = "10")
	private Long id;
	
	@ApiModelProperty(value = "Código da categoria", example = "11")
	private List<CategorySimParamResponse> categoryId;
	
	@ApiModelProperty(value = "Código da sub-ccategoria", example = "12")
	private List<SubCategorySimParamResponse> subCategoryId;
	
	@ApiModelProperty(value = "Tipo de simulação")
	private Long simulationType;
	
	@ApiModelProperty(value = "Tipo de pedido de compra", example = "13")
	private OrderTypeResponse purchaseOrderType;
	
	@ApiModelProperty(value = "Classificação de pedido de compra")
	private Long classification;
	
	@ApiModelProperty(value = "Código da condição de pagamento", example = "14")
	private Long paymentTermId;
	
	@ApiModelProperty(value = "Observação")
	private String note;
	
	@ApiModelProperty(value = "Base de cálculo")
	private Long calculationBasis;
	
	@ApiModelProperty(value = "QT dia de venda", example = "15")
	private Long saleDay;
	
	@ApiModelProperty(value = "Flag apenas inativos", example = "TRUE / FALSE")
	private Boolean inactive;
	
	@ApiModelProperty(value = "Promo Pack")
	private String promoPack;
	
	@ApiModelProperty(value = "Cotados")
	private String quoted;
	
	@ApiModelProperty(value = "Código do operador", example = "16")
	private Long operatorId;
	
	@ApiModelProperty(value = "Data de cadastro")
	private LocalDate registerDate;
	
	@ApiModelProperty(value = "Data do tipo antecipação")
	private AntecipationTypeSimulationResponse antecipationType;
	
	@ApiModelProperty(value = "Lista de Centro de Distribuição")
	private List<DistributionCenterSimParamResponse> distributionCenters;
	
	@ApiModelProperty(value = "Lista de Fabricantes")
	private List<SupplierSimParamResponse> suppliers;
	
	@ApiModelProperty(value = "Lista de Produtos")
	private List<ProductDiscountResponse> products;
	
	@ApiModelProperty(value = "Status da Simulação")
	private Long status;

	public Boolean isActive(){
		return !inactive;
	}
}