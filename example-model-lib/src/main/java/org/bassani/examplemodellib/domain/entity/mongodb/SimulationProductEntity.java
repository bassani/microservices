package org.bassani.examplemodellib.domain.entity.mongodb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Document
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class SimulationProductEntity {

    @Id
    private String id;
    private Long distributionCenterId;
    private String distributionCenterName;
    private Long supplierId;
    private String supplierName;
    private Long parentSupplierId;
    private String parentSupplierName;
    private Long simulationId;
    private LocalDate simulationDate;
    private Long leadTime;
    private Long frequencyTime;
    private Long additionalFrequencyTime;
    private Long productId;
    private String productName;
    private String productSupplierId;
    private String productEAN;
    private String physicalCurve;
    private Long calculationBasis;
    private String calculationBasisName;
    private Long calculationBasisInDays;
    private Long saleDay;
    private Long vb;
    private Long storeStock;
    private Long branchDiff;
    private Long branchDiffByStockSum;
    private Long extraDemand;
    private Long stockLocked;
    private Long distributionCenterStock;
    private Long pendencies;
    private Long unitMultiplier;
    private BigDecimal roundingPercentSuggestion;
    private Double defaultProductUnitPrice;
    private Double defaultProductDiscount;
    private Double appliedProductDiscount;
    private Double totalAmountWithNegotiatedDiscount;
    private Long orderedQty;
    private Double totalAmountWithRegularDiscount;
    private Double gain;
    private Double percentageGain;
    private Long dailyCDStock;
    private Long dailyFinalStock;
    private Long dailyGridStock;
    private Long regularTermId;
    private String regularTermDescription;
    private String newPaymentTermTypeDescription;
    private Long newTermId;
    private String newTermDescription;
    private Long regularTermInDaysQuantity;
    private Long newTermInDaysQuantity;
    private Long deltaTermInDaysQuantity;
    private Long purchaseDays;
    private Integer subGroupId;
    private Long stockDaysPurchase;
    private Integer budgetType;
    private Double budgetValue;
    private String budgetReason;
    private Long finalStock;
    private Long minQuantityProductDc;
    private BigDecimal sumDailyCmv;
    private Long billingSupplierId;
    private String billingSupplierName;
    private Double billingMinOrderValue;
}