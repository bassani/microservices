package org.bassani.examplemodellib.domain.entity.mongodb.operation;

import org.bson.Document;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.ConvertOperators;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;

import java.util.Arrays;

import static br.com.example.purchasesimulatormodellib.domain.entity.mongodb.operation.CommonOperationFactory.conditional;
import static br.com.example.purchasesimulatormodellib.domain.entity.mongodb.operation.CommonOperationFactory.isNumber;

public class SimulationSummaryDCOperation {

    private SimulationSummaryDCOperation() { throw new UnsupportedOperationException();}

    private static Document calcBasisInDaysIsZero = new Document("$eq", Arrays.asList(
            "$calculationBasisInDaysSum", 0));
    private static Document calcTotalAmountWithRegularDiscountIsZero = new Document("$eq",
            Arrays.asList("$totalAmountWithRegularDiscount", 0));
    private static Document divGain = new Document("$divide",Arrays.asList("$gain", "$totalAmountWithRegularDiscount"));
    private static Document divideGain = new Document("$cond", Arrays.asList(calcTotalAmountWithRegularDiscountIsZero, 0, divGain));
    private static Document calcPercentageGain = new Document("$round", Arrays.asList(divideGain, 4));
    private static Document newTermInDaysQuantity = conditional(
            isNumber("$newTermInDaysQuantity"), "$newTermInDaysQuantity", 0);

    //Cobertura de Estoque Atual CD
    private static Document sum = new Document("$add",Arrays.asList("$branchDiffSum", "$extraDemandSum", "$stockLockedSum"));
    private static Document subtractDC = new Document("$subtract",Arrays.asList("$distributionCenterStockSum", sum));
    private static Document divStock = new Document("$divide",Arrays.asList(subtractDC, "$calculationBasisInDaysSum"));
    private static Document roundCurrentStock = new Document("$round", Arrays.asList(divStock, 0));
    private static Document resultCurrentlyStock = new Document("$cond", Arrays.asList(calcBasisInDaysIsZero, 0, roundCurrentStock));

    //Cobertura de Estoque Após a Compra
    private static Document totalStockAndOrdered = new Document("$add",Arrays.asList("$finalStockSum", "$orderedQty"));
    private static Document divFinalStock = new Document("$divide",Arrays.asList(totalStockAndOrdered, "$calculationBasisInDaysSum"));
    private static Document subtractLeadTime = new Document("$subtract",Arrays.asList(divFinalStock, "$leadTime"));
    private static Document roundFinalStockAfterPurchase = new Document("$round", Arrays.asList(subtractLeadTime, 0));
    private static Document resultFinalStockAfterPurchase = new Document("$cond", Arrays.asList(calcBasisInDaysIsZero, 0, roundFinalStockAfterPurchase));

    //Cobertura de Estoque Rede
    private static Document gridStockAndOrdered = new Document("$add",Arrays.asList(totalStockAndOrdered , "$storeStockSum"));
    private static Document divGridStock = new Document("$divide",Arrays.asList(gridStockAndOrdered, "$calculationBasisInDaysSum"));
    private static Document subtractGridLeadTime = new Document("$subtract",Arrays.asList(divGridStock, "$leadTime"));
    private static Document roundGridStock = new Document("$round", Arrays.asList(subtractGridLeadTime, 0));
    private static Document resultGridStock = new Document("$cond", Arrays.asList(calcBasisInDaysIsZero, 0, roundGridStock));

    private static Document divStockDaysPurchase = new Document("$divide",
            Arrays.asList("$orderedQty", "$calculationBasisInDaysSum"));
    private static Document divideStockDaysPurchase = new Document("$cond", Arrays.asList(calcBasisInDaysIsZero, 0,
            divStockDaysPurchase));
    private static Document calcStockDaysPurchase = new Document("$round", Arrays.asList(divideStockDaysPurchase, 0));

    public static GroupOperation summaryAggregationDC = Aggregation
            .group("simulationId","distributionCenterId","supplierId")
            .first("$simulationId").as("simulationId")
            .first("$simulationDate").as("simulationDate")
            .first("$distributionCenterId").as("distributionCenterId")
            .first("$distributionCenterName").as("distributionCenterName")
            .first("$supplierId").as("supplierId")
            .first("$supplierName").as("supplierName")
            .first("$parentSupplierId").as("parentSupplierId")
            .first("$parentSupplierName").as("parentSupplierName")
            .first("$calculationBasis").as("calculationBasis")
            .first("$purchaseDays").as("purchaseDays")
            .first("$billingSupplierId").as("billingSupplierId")
            .first("$billingSupplierName").as("billingSupplierName")
            .first("$billingMinOrderValue").as("billingMinOrderValue")
            .first(ConvertOperators.ToLong.toLong("$deltaTermInDaysQuantity")).as("deltaTermInDaysQuantity")
            .avg(ConvertOperators.ToLong.toLong("$regularTermInDaysQuantity")).as("regularTermInDaysQuantity")
            .avg(ConvertOperators.ToLong.toLong(newTermInDaysQuantity)).as("newTermInDaysQuantity")
            .first("$leadTime").as("leadTime")
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
            "regularTermInDaysQuantity",
            "newTermInDaysQuantity",
            "deltaTermInDaysQuantity",
            "orderedQty",
            "totalAmountWithRegularDiscount",
            "totalAmountWithNegotiatedDiscount",
            "gain",
            "calculationBasisInDaysSum",
            "billingSupplierId",
            "billingSupplierName",
            "billingMinOrderValue")
            .and(ConvertOperators.ToDouble.toDouble(calcPercentageGain)).as("percentageGain")
            .and(ConvertOperators.ToInt.toInt(resultCurrentlyStock)).as("dailyCDStock")
            .and(ConvertOperators.ToInt.toInt(resultFinalStockAfterPurchase)).as("dailyFinalStock")
            .and(ConvertOperators.ToInt.toInt(resultGridStock)).as("dailyGridStock")
            .and(ConvertOperators.ToInt.toInt(calcStockDaysPurchase)).as("stockDaysPurchase")
            .andExclude("_id").andExpression("concat(substr(simulationId,0,-1),'-', substr(distributionCenterId,0,-1),'-',substr(supplierId,0,-1))").as("_id");

}
