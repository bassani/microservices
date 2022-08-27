package org.bassani.examplemodellib.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SimulationSummaryDCResponse {
    private Long simulationId;
    private LocalDate simulationDate;
    private Long distributionCenterId;
    private String distributionCenterName;
    private Long supplierId;
    private String supplierName;
    private Long parentSupplierId;
    private String parentSupplierName;
    private Integer calculationBasis;
    private Integer calculationBasisInDaysSum;
    private Integer purchaseDays;
    private Long regularTermInDaysQuantity;
    private Long newTermInDaysQuantity;
    private Long deltaTermInDaysQuantity;
    private Integer leadTime;
    private Integer frequencyTime;
    private Double totalAmountWithRegularDiscount;
    private Double totalAmountWithNegotiatedDiscount;
    private Double gain;
    private Double percentageGain;
    private Integer orderedQty;
    private Integer dailyCDStock;
    private Integer dailyFinalStock;
    private Integer dailyGridStock;
    private Integer stockDaysPurchase;
}
