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
import static br.com.example.purchasesimulatormodellib.domain.entity.mongodb.operation.CommonOperationFactory.multiply;
import static br.com.example.purchasesimulatormodellib.domain.entity.mongodb.operation.CommonOperationFactory.round;
import static br.com.example.purchasesimulatormodellib.domain.entity.mongodb.operation.CommonOperationFactory.roundHalfDown;
import static br.com.example.purchasesimulatormodellib.domain.entity.mongodb.operation.CommonOperationFactory.subtract;

public class CashCycleCalculationOperation {

    private CashCycleCalculationOperation() { throw new UnsupportedOperationException();}

    public static UnwindOperation unwindOperation = Aggregation.unwind("$simulationId");

    private static Document defaultProductUnitPriceSumIsZero = equalTo("$defaultProductUnitPriceSum", 0);

    //Calcular Media Ponderada do Lead Time - TE
    private static Document multiplyLeadTimeWithDefaultProductUnitPrice = multiply("$leadTime",
            ConvertOperators.ToDouble.toDouble("$totalAmountWithNegotiatedDiscount"));
    private static Document devLeadTimeWeightedAVG = divide("$totalLeadTimeWithPrice", "$defaultProductUnitPriceSum");
    private static Document leadTimeWeightedAVG = conditional(defaultProductUnitPriceSumIsZero, 0, devLeadTimeWeightedAVG);
    private static Document leadTimeWeightedAVGRound = round(leadTimeWeightedAVG, 1);

    //Calcular Media Ponderada da Frequência Compra - TF
    private static Document multiplyFrequencyTimeWithDefaultProductUnitPrice = multiply("$frequencyTime",
            ConvertOperators.ToDouble.toDouble("$totalAmountWithNegotiatedDiscount"));
    private static Document divFrequencyTimeWeightedAVG = divide("$totalFrequencyTimeWithPrice",
            ConvertOperators.ToDouble.toDouble("$defaultProductUnitPriceSum"));
    private static Document frequencyTimeWeightedAVG = conditional(defaultProductUnitPriceSumIsZero, 0, divFrequencyTimeWeightedAVG);
    private static Document frequencyTimeWeightedAVGRound = round(frequencyTimeWeightedAVG, 1);

    //Calcular TF Adicional - additionalFrequencyTime
    private static Document multiplyAdditionalFrequencyTimeWithProductUnitPrice = multiply("$additionalFrequencyTime"
            , ConvertOperators.ToDouble.toDouble("$totalAmountWithNegotiatedDiscount"));
    private static Document divAdditionalFrequencyTimeWeightedAVG = divide("$totalAdditionalFrequencyTimeWithProductUnitPrice" , "$defaultProductUnitPriceSum");
    private static Document additionalFrequencyTimeWeightedAVG = conditional(defaultProductUnitPriceSumIsZero, 0, divAdditionalFrequencyTimeWeightedAVG);
    private static Document additionalFrequencyTimeWeightedAVGRound = round(additionalFrequencyTimeWeightedAVG, 1);

    //Calcular Parâmetros regular - Pond.
    private static Document regularParamWeightAVG = roundHalfDown(add(leadTimeWeightedAVGRound, frequencyTimeWeightedAVGRound
            , additionalFrequencyTimeWeightedAVGRound));

    //Calcular Dias Compra - Pond.
    private static Document multiplyPurchaseDaysWithProductUnitPrice = multiply("$stockDaysPurchase", ConvertOperators.ToDouble.toDouble("$totalAmountWithNegotiatedDiscount"));
    private static Document divPurchaseDaysWeightAVG = divide("$totalPurchaseDaysWithProductUnitPrice",
            "$defaultProductUnitPriceSum");
    private static Document purchaseDaysWeightAVG = conditional(defaultProductUnitPriceSumIsZero, 0, divPurchaseDaysWeightAVG);
    private static Document purchaseDaysWeightAVGRound = round(purchaseDaysWeightAVG, 1);

    //Calcular Delta Frequencia
    private static Document deltaFrequency = subtract(regularParamWeightAVG,purchaseDaysWeightAVGRound);

    //Calcular Delta Prazo
    private static Document newTermInDaysQuantityNonNull = conditional(equalTo("$newTermInDaysQuantity", null),0,
            "$newTermInDaysQuantity");

    private static Document deltaTerm = conditional(
            equalTo(newTermInDaysQuantityNonNull, 0),
            0,  subtract("$newTermInDaysQuantity", "$regularTermInDaysQuantity"));

    //Calcular Impacto Ciclo Dias
    private static Document impactCycleDays = subtract(deltaTerm, deltaFrequency);

    //Calcular Impacto Ciclo em Reais (precisa do CMV diario)
    private static Document impactCycleReals = round(multiply(impactCycleDays, ConvertOperators.ToDouble.toDouble("$sumDailyCmv")),2);

    public static GroupOperation cashCycleAggregation = Aggregation
            .group("simulationId")
            .first("$simulationId").as("simulationId")
            .first("$simulationDate").as("simulationDate")
            .addToSet("$distributionCenterId").as("distributionCenterId")
            .addToSet("$distributionCenterName").as("distributionCenterName")
            .first("$regularTermInDaysQuantity").as("regularTermInDaysQuantity")
            .first("$newTermInDaysQuantity").as("newTermInDaysQuantity")
            .first("$sumDailyCmv").as("sumDailyCmv")
            .sum("totalAmountWithNegotiatedDiscount").as("defaultProductUnitPriceSum")
            .sum(ConvertOperators.ToDouble.toDouble(multiplyLeadTimeWithDefaultProductUnitPrice)).as("totalLeadTimeWithPrice")
            .sum(ConvertOperators.ToDouble.toDouble(multiplyFrequencyTimeWithDefaultProductUnitPrice)).as("totalFrequencyTimeWithPrice")
            .sum(ConvertOperators.ToDouble.toDouble(multiplyAdditionalFrequencyTimeWithProductUnitPrice)).as("totalAdditionalFrequencyTimeWithProductUnitPrice")
            .sum(ConvertOperators.ToDouble.toDouble(multiplyPurchaseDaysWithProductUnitPrice)).as("totalPurchaseDaysWithProductUnitPrice");

    public static ProjectionOperation projectionOperation = Aggregation.project(
            "simulationId"
            ,"simulationDate"
            ,"totalLeadTimeWithPrice"
            ,"totalFrequencyTimeWithPrice"
            ,"totalAdditionalFrequencyTimeWithProductUnitPrice"
            ,"regularTermInDaysQuantity"
            ,"defaultProductUnitPriceSum")
            .and(ConvertOperators.ToInt.toInt(newTermInDaysQuantityNonNull)).as("newTermInDaysQuantity")
            .and(ConvertOperators.ToDouble.toDouble(leadTimeWeightedAVGRound)).as("leadTimeWeightedAVGRound")
            .and(ConvertOperators.ToDouble.toDouble(frequencyTimeWeightedAVGRound)).as("frequencyTimeWeightedAVGRound")
            .and(ConvertOperators.ToDouble.toDouble(additionalFrequencyTimeWeightedAVGRound)).as("additionalFrequencyTimeWeightedAVGRound")
            .and(ConvertOperators.ToDouble.toDouble(regularParamWeightAVG)).as("regularParamWeightAVG")
            .and(ConvertOperators.ToDouble.toDouble(purchaseDaysWeightAVGRound)).as("purchaseDaysWeightAVGRound")
            .and(ConvertOperators.ToDouble.toDouble(deltaFrequency)).as("deltaFrequency")
            .and(ConvertOperators.ToDouble.toDouble(deltaTerm)).as("deltaTerm")
            .and(ConvertOperators.ToDouble.toDouble(impactCycleDays)).as("impactCycleDays")
            .and(ConvertOperators.ToDouble.toDouble(impactCycleReals)).as("impactCycleReals");

}
