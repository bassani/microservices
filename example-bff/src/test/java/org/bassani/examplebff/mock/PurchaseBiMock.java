package org.bassani.examplebff.mock;

import org.bassani.examplemodellib.domain.request.PurchaseBiRequest;
import org.bassani.examplemodellib.domain.response.PurchaseBiChartResponse;
import org.bassani.examplemodellib.domain.response.PurchaseBiResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.bassani.examplemodellib.util.Mocks.asJson;

public class PurchaseBiMock {

    private static final LocalDate localDate = LocalDate.of(2021, 03, 30);
    private static final Pageable DEFAULT_PAGEABLE = PageRequest.of(0, 10);

    public static PurchaseBiResponse mockedPurchaseSimulatorBiResponse() {
        return PurchaseBiResponse.builder()
                .periodDayDate(localDate)
                .productCode(6l)
                .branchType(3l)
                .cmv(BigDecimal.valueOf(548000))
                .stockValue(BigDecimal.valueOf(1982528.50))
                .build();
    }

    public static List<PurchaseBiResponse> mockedListPurchaseSimulatorBiResponse() {
        PurchaseBiResponse purchaseBiResponse = PurchaseBiResponse.builder()
                .periodDayDate(localDate)
                .productCode(6l)
                .branchType(3l)
                .cmv(BigDecimal.valueOf(548000))
                .stockValue(BigDecimal.valueOf(1982528.50))
                .build();
        return List.of(purchaseBiResponse);

    }

    public static PurchaseBiResponse mockedPurchaseSimulatorBiResponseWithoutData() {
        return null;
    }

    public static String mockedPurchaseSimulatorBiResponseAsJson() {
        return asJson(mockedPurchaseSimulatorBiResponse());
    }

    public static String mockedPurchaseSimulatorBiResponseAsJsonWithoutData() {
        return asJson(mockedPurchaseSimulatorBiResponseWithoutData());
    }

    public static PurchaseBiRequest mockedPurchaseBiRequest() {
        return PurchaseBiRequest.builder().productIds(List.of(123l, 452l, 189l)).build();
    }

    public static List<PurchaseBiChartResponse> mockedListPurchaseSimulatorBiChartResponse() {
        PurchaseBiChartResponse purchaseBiResponse = PurchaseBiChartResponse.builder()
                .month(localDate)
                .stockDays(BigDecimal.valueOf(6l))
                .cycle(BigDecimal.valueOf(3l))
                .cmv(BigDecimal.valueOf(548000))
                .stockValue(BigDecimal.valueOf(1982528.50))
                .build();
        return List.of(purchaseBiResponse);
    }

    public static PurchaseBiChartResponse mockedPurchaseSimulatorBiChartResponse() {
        return PurchaseBiChartResponse.builder()
                .month(localDate)
                .stockDays(BigDecimal.valueOf(6l))
                .cycle(BigDecimal.valueOf(3l))
                .cmv(BigDecimal.valueOf(548000))
                .stockValue(BigDecimal.valueOf(1982528.50))
                .build();
    }
}
