package org.bassani.examplemodellib.rabbit;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimulationCompositionMessage implements EventMessage {

	private static final long serialVersionUID = 4116422284476590914L;

	Long simulationId;
	Long productId;
	String productDescription;
	BigDecimal productCommercialDiscountPc;
	Long distributionCenterId;
	String distributionCenterName;
	Long supplierId;
	String supplierName;
	Long categoryId;
	Long subCategoryId;
	Long branchStockQuantity;
	Long sumMaxBaseValue;
	Long branchDiff;
	Long branchDiffByStockSum;
	Long extraDemandQuantity;
	Long stockLockQuantity;
	Long stockDistributionCenterQuantity;
	Long frequencyTime;
	Long leadTime;
	Long additionalFrequencyTime;
	Long anticipationDays;
	Long purchaseDays;
    BigDecimal totalValue;
	Long billingMultiplyQuantity;
	BigDecimal roudingPercentSugestion;
	Long pendingProductQuantity;
	Long minQuantityProductDc;
    Long parentSupplierId;
    String parentSupplierName;
	Integer subGroupId;
	String supplierProductId;
	Long ean;
	String productCurve;
	Double averageCost;
	BigDecimal userDiscountValue;
	Integer userDiscountType;
    Long regularTermId;
    String regularTermDescription;
    Long regularTermInDaysQuantity;
    Integer budgetType;
    BigDecimal budgetValue;
    String budgetReason;
    Boolean isInactiveProduct;
    BigDecimal sumDailyCmv;
    Long billingSupplierId;
    String billingSupplierName;
    Double billingMinOrderValue;


    @JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	LocalDate productCreateDate;
}
