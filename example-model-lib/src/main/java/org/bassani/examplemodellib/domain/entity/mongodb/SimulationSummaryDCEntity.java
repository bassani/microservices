package org.bassani.examplemodellib.domain.entity.mongodb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@org.springframework.data.mongodb.core.mapping.Document
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SimulationSummaryDCEntity {
    @Id
    private Long simulationId;
    private LocalDate simulationDate;
    private Long distributionCenterId;
    private String distributionCenterName;
    private Long parentSupplierId;
    private String parentSupplierName;
    private Long supplierId;
    private String supplierName;
    private Integer calculationBasis;
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
