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
public class SimulationOrderSummaryResponse {

    private String approvalCompetencyName;
    private String orderClassification;
    private Double purchaseValue;
    private String parentSupplierName;
    private String calculationBasisName;
    private Long regularTermInDaysQuantity;
    private Long newTermInDaysQuantity;
    private Long deltaTermInDaysQuantity;
    private LocalDate regularPaymentDate;
    private LocalDate negotiatedPaymentDate;
    private Double cashFlowImpact;
    private Integer budgetType;
    private Double informedBudgetValue;
    private Double totalBudgetValue;
    private Double gain;
    private String budgetReason;
    private Integer stockDaysPurchase;
    private Integer dailyFinalStock;
    private Integer dailyCDStock;
    private Integer dailyGridStock;
}