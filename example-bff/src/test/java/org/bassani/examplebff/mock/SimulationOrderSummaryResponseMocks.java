package org.bassani.examplebff.mock;

import org.bassani.examplemodellib.domain.response.SimulationOrderSummaryResponse;

import java.time.LocalDate;

public class SimulationOrderSummaryResponseMocks {

    private SimulationOrderSummaryResponseMocks() {
    }

    public static SimulationOrderSummaryResponse validSimulationOrderSummaryResponse() {
        return SimulationOrderSummaryResponse.builder()
                .approvalCompetencyName("Coordenador")
                .orderClassification("Antecipação")
                .purchaseValue(755378.2152)
                .parentSupplierName("ELI LILLY")
                .calculationBasisName("Últ. 60 dias")
                .regularTermInDaysQuantity(30L)
                .newTermInDaysQuantity(31L)
                .deltaTermInDaysQuantity(32L)
                .regularPaymentDate(LocalDate.now())
                .negotiatedPaymentDate(LocalDate.now())
                .cashFlowImpact(0.0)
                .budgetType(1)
                .informedBudgetValue(1.0)
                .totalBudgetValue(12.0)
                .gain(12500.25)
                .budgetReason("contrapartida?")
                .stockDaysPurchase(66)
                .dailyFinalStock(67)
                .dailyCDStock(68)
                .dailyGridStock(77)
                .build();
    }

}