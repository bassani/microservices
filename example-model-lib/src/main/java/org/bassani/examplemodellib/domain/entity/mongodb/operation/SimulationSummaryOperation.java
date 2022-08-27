package org.bassani.examplemodellib.domain.entity.mongodb.operation;

import org.bson.Document;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.ConvertOperators;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;

import static br.com.example.purchasesimulatormodellib.domain.entity.mongodb.operation.CommonOperationFactory.add;
import static br.com.example.purchasesimulatormodellib.domain.entity.mongodb.operation.CommonOperationFactory.conditional;
import static br.com.example.purchasesimulatormodellib.domain.entity.mongodb.operation.CommonOperationFactory.divide;
import static br.com.example.purchasesimulatormodellib.domain.entity.mongodb.operation.CommonOperationFactory.equalTo;
import static br.com.example.purchasesimulatormodellib.domain.entity.mongodb.operation.CommonOperationFactory.isNumber;
import static br.com.example.purchasesimulatormodellib.domain.entity.mongodb.operation.CommonOperationFactory.multiply;
import static br.com.example.purchasesimulatormodellib.domain.entity.mongodb.operation.CommonOperationFactory.round;
import static br.com.example.purchasesimulatormodellib.domain.entity.mongodb.operation.CommonOperationFactory.roundHalfUp;
import static br.com.example.purchasesimulatormodellib.domain.entity.mongodb.operation.CommonOperationFactory.subtract;
import static br.com.example.purchasesimulatormodellib.domain.entity.mongodb.operation.CommonOperationFactory.toInt;

public class SimulationSummaryOperation {

    private SimulationSummaryOperation() { throw new UnsupportedOperationException();}

    public static UnwindOperation unwindOperation = Aggregation.unwind("$distributionCenterId");
    private static Document calcBasisInDaysIsZero = equalTo("$calculationBasisInDaysSum", 0);
    private static Document calcTotalAmountWithRegularDiscountIsZero = equalTo("$totalAmountWithRegularDiscount", 0);
    private static Document divGain = divide("$gain", "$totalAmountWithRegularDiscount");
    private static Document divideGain = conditional(calcTotalAmountWithRegularDiscountIsZero, 0, divGain);
    private static Document calcPercentageGain = round(divideGain, 4);

    //Cobertura de Estoque Atual CD
    private static Document sum = add("$branchDiffSum", "$extraDemandSum", "$stockLockedSum");
    private static Document subtractDC = subtract("$distributionCenterStockSum", sum);
    private static Document divStock = divide(subtractDC, "$calculationBasisInDaysSum");
    private static Document divideStock = conditional(calcBasisInDaysIsZero, 0, divStock);
    private static Document currentStock = round(divideStock, 0);

    //Cobertura de Estoque Após a Compra
    private static Document totalStockAndOrdered = add("$finalStockSum", "$orderedQty");
    private static Document divFinalStock = divide(totalStockAndOrdered, "$calculationBasisInDaysSum");
    private static Document subtractLeadTime = subtract(divFinalStock, "$leadTime");
    private static Document finalStockAfterPurchase = round(subtractLeadTime, 0);
    private static Document resultFinalStockAfterPurchase = conditional(calcBasisInDaysIsZero, 0, finalStockAfterPurchase);

    //Cobertura de Estoque Rede
    private static Document gridStockAndOrdered = add(totalStockAndOrdered , "$storeStockSum");
    private static Document divGridStock = divide(gridStockAndOrdered, "$calculationBasisInDaysSum");
    private static Document subtractGridLeadTime = subtract(divGridStock, "$leadTime");
    private static Document gridStock = round(subtractGridLeadTime, 0);
    private static Document resultGridStock = conditional(calcBasisInDaysIsZero, 0, gridStock);

    private static Document divStockDaysPurchase = divide("$orderedQty", "$calculationBasisInDaysSum");
    private static Document divideStockDaysPurchase = conditional(calcBasisInDaysIsZero, 0, divStockDaysPurchase);
    private static Document calcStockDaysPurchase = round(divideStockDaysPurchase, 0);

    private static Document budgetTypeInformedInMoney = equalTo("$budgetType", 1);
    private static Document multiplyBudget = multiply("$budgetValue", "$totalAmountWithNegotiatedDiscount");
    private static Document calcDivBudgetValue = divide(multiplyBudget, 100);
    private static Document informedBudgetValue = round("$budgetValue", 4);

    private static Document divideTotalBudgetValue = conditional(budgetTypeInformedInMoney,
            informedBudgetValue, calcDivBudgetValue);
    private static Document totalBudgetValue = round(divideTotalBudgetValue, 2);

    private static Document totalAmountWithNegotiatedDiscountEqualZero = equalTo("$totalAmountWithNegotiatedDiscount"
            , 0);
    private static Document multiplyQtNewTermDaysWithNegotiatedDiscount =
            multiply("$newTermInDaysQuantity","$totalAmountWithNegotiatedDiscount");
    private static Document calcWeightedAvgNewTermDays = toInt(roundHalfUp(divide("$newTermInDaysQuantity",
            conditional(totalAmountWithNegotiatedDiscountEqualZero, 1, "$totalAmountWithNegotiatedDiscount"))));
    private static Document weightedAvgNewTermDays = conditional(
            isNumber("$isNewTermInformed"), calcWeightedAvgNewTermDays, 0);

    private static Document multiplyRegularTermDaysWithNegotiatedDiscount =
            multiply("$regularTermInDaysQuantity","$totalAmountWithNegotiatedDiscount");
    private static Document weightedAvgRegularTermDays = toInt(roundHalfUp(divide("$regularTermInDaysQuantity",
            conditional(totalAmountWithNegotiatedDiscountEqualZero, 1, "$totalAmountWithNegotiatedDiscount"))));
    private static Document calcDeltaTermForWeightedAvgTerms =
            conditional(
                    equalTo("$newTermInDaysQuantity", 0),
                    0,  subtract(calcWeightedAvgNewTermDays, weightedAvgRegularTermDays));


    public static GroupOperation summaryAggregation = Aggregation
            .group("simulationId")
            .first("$simulationId").as("simulationId")
            .first("$simulationDate").as("simulationDate")
            .addToSet("$distributionCenterId").as("distributionCenterId")
            .addToSet("$distributionCenterName").as("distributionCenterName")
            .first("$supplierId").as("supplierId")
            .first("$supplierName").as("supplierName")
            .first("$parentSupplierId").as("parentSupplierId")
            .first("$parentSupplierName").as("parentSupplierName")
            .first("$calculationBasis").as("calculationBasis")
            .first("$purchaseDays").as("purchaseDays")
            .first("$budgetType").as("budgetType")
            .first(ConvertOperators.ToDouble.toDouble("$budgetValue")).as("budgetValue")
            .first("$budgetReason").as("budgetReason")
            .sum(ConvertOperators.ToDouble.toDouble(multiplyRegularTermDaysWithNegotiatedDiscount)).as("regularTermInDaysQuantity")
            .sum(ConvertOperators.ToDouble.toDouble(multiplyQtNewTermDaysWithNegotiatedDiscount)).as("newTermInDaysQuantity")
            .avg("$newTermInDaysQuantity").as("isNewTermInformed")
            .avg("$leadTime").as("leadTime")
            .avg("$frequencyTime").as("frequencyTime")
            .sum(ConvertOperators.ToInt.toInt("$orderedQty")).as("orderedQty")
            .sum(ConvertOperators.ToDouble.toDouble("$gain")).as("gain")
            .sum(ConvertOperators.ToDouble.toDouble("$totalAmountWithRegularDiscount")).as("totalAmountWithRegularDiscount")
            .sum(ConvertOperators.ToDouble.toDouble("$totalAmountWithNegotiatedDiscount")).as("totalAmountWithNegotiatedDiscount")
            .sum(ConvertOperators.ToDouble.toDouble("$distributionCenterStock")).as("distributionCenterStockSum")
            .sum(ConvertOperators.ToDouble.toDouble("$branchDiff")).as("branchDiffSum")
            .sum(ConvertOperators.ToDouble.toDouble("$extraDemand")).as("extraDemandSum")
            .sum(ConvertOperators.ToDouble.toDouble("$stockLocked")).as("stockLockedSum")
            .sum(ConvertOperators.ToDouble.toDouble("$calculationBasisInDays")).as("calculationBasisInDaysSum")
            .sum(ConvertOperators.ToDouble.toDouble("$storeStock")).as("storeStockSum")
            .sum(ConvertOperators.ToDouble.toDouble("$pendencies")).as("pendingStockSum")
            .sum(ConvertOperators.ToLong.toLong("$stockDaysPurchase")).as("stockDaysPurchaseSum")
            .sum(ConvertOperators.ToLong.toLong("$finalStock")).as("finalStockSum");

    public static ProjectionOperation projectionOperation = Aggregation.project(
            "simulationId",
            "simulationDate",
            "distributionCenterId",
            "distributionCenterName",
            "supplierId",
            "supplierName",
            "parentSupplierId",
            "parentSupplierName",
            "calculationBasis",
            "purchaseDays",
            "leadTime",
            "frequencyTime",
            "totalAmountWithRegularDiscount",
            "totalAmountWithNegotiatedDiscount",
            "gain",
            "orderedQty",
            "calculationBasisInDaysSum",
            "budgetType",
            "budgetReason")
            .and(ConvertOperators.ToInt.toInt(calcDeltaTermForWeightedAvgTerms)).as("deltaTermInDaysQuantity")
            .and(ConvertOperators.ToInt.toInt(weightedAvgRegularTermDays)).as("regularTermInDaysQuantity")
            .and(ConvertOperators.ToInt.toInt(weightedAvgNewTermDays)).as("newTermInDaysQuantity")
            .and(ConvertOperators.ToDouble.toDouble(informedBudgetValue)).as("informedBudgetValue")
            .and(ConvertOperators.ToDouble.toDouble(totalBudgetValue)).as("totalBudgetValue")
            .and(ConvertOperators.ToDouble.toDouble(calcPercentageGain)).as("percentageGain")
            .and(ConvertOperators.ToInt.toInt(currentStock)).as("dailyCDStock")
            .and(ConvertOperators.ToInt.toInt(resultFinalStockAfterPurchase)).as("dailyFinalStock")
            .and(ConvertOperators.ToInt.toInt(resultGridStock)).as("dailyGridStock")
            .and(ConvertOperators.ToInt.toInt(calcStockDaysPurchase)).as("stockDaysPurchase");


}
