package org.bassani.examplebff.mock;

import org.bassani.examplemodellib.domain.dto.GeneralDiscountDTO;
import org.bassani.examplemodellib.domain.dto.ProductDiscountDTO;
import org.bassani.examplemodellib.domain.dto.SimulationDTO;
import org.bassani.examplemodellib.domain.request.BillingSupplierRequest;
import org.bassani.examplemodellib.domain.request.CategoryRequest;
import org.bassani.examplemodellib.domain.request.DistributionCenterRequest;
import org.bassani.examplemodellib.domain.request.OrderTypeRequest;
import org.bassani.examplemodellib.domain.request.ProductFilterRequest;
import org.bassani.examplemodellib.domain.request.SimCalculationBasisRequest;
import org.bassani.examplemodellib.domain.request.SimClassificationRequest;
import org.bassani.examplemodellib.domain.request.SimNewPaymentTermRequest;
import org.bassani.examplemodellib.domain.request.SimulationOrderCDSupplierRequest;
import org.bassani.examplemodellib.domain.request.SimulationOrderRequest;
import org.bassani.examplemodellib.domain.request.SimulationStatusRequest;
import org.bassani.examplemodellib.domain.request.SubCategoryRequest;
import org.bassani.examplemodellib.domain.request.SupplierRequest;
import org.bassani.examplemodellib.domain.response.SimulationStatusResponse;
import org.bassani.examplemodellib.domain.response.SimulationSummaryDCResponse;
import org.bassani.examplemodellib.domain.response.SimulationSummaryResponse;
import org.bassani.examplemodellib.enums.DiscountTypeEnum;
import org.bassani.examplemodellib.enums.SalesByPeriodEnum;
import org.bassani.examplemodellib.enums.SimulationTypeEnum;
import org.bassani.examplemodellib.enums.TaxOperationTypeEnum;
import org.bassani.examplemodellib.enums.TemporaryInactiveEnum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SimulationMocks {

    private static final SimulationDTO dto;

    static {
        dto = SimulationDTO.builder()
                .distributionCenters(List.of(new DistributionCenterRequest(903L, "CD RIO JANEIRO")))
                .suppliers(List.of(new SupplierRequest(292L, "OSLER", 292L,"OSLERPAI")))
                .categories(List.of(new CategoryRequest(201L, "GENERICOS")))
                .subcategories(List.of(new SubCategoryRequest(575L, "GENERICOS", 201L)))
                .classification(new SimClassificationRequest(1L, "BLACK FRIDAY"))
                .generalDiscount(new GeneralDiscountDTO(DiscountTypeEnum.ADD, new BigDecimal("9.99")))
                .orderType(new OrderTypeRequest(1L, "Normal"))
                .calculationBasis(new SimCalculationBasisRequest(1L, SalesByPeriodEnum.SEVEN_DAYS))
                .productFilter(new ProductFilterRequest(true, null, null, TemporaryInactiveEnum.SOMENTE_INATIVOS_TEMPORARIOS))
                .note("Observações")
                .simulationType(SimulationTypeEnum.ANTECIPACAO)
                .newPaymentTerm(SimNewPaymentTermRequestMock.simNewPaymentTermRequestGeneral())
                .operatorId(3331987L)
                .anticipationDate(LocalDate.MAX)
                .build();
        }

    private SimulationMocks() {
    }

    public static SimulationDTO validSimulationDtoWithGeneralDiscount() {
        return dto.toBuilder().build();
    }

    public static SimulationDTO invalidSimulationDtoWithNoSupplierNorCategories() {
        return dto.toBuilder().categories(Collections.emptyList()).suppliers(Collections.emptyList()).build();
    }

    public static SimulationDTO invalidSimulationDtoWithBothGeneralAndProductDiscounts() {
        return dto.toBuilder()
                .productDiscounts(List.of(new ProductDiscountDTO(26346L, "PERSPIREX ROLL ON 20ML", DiscountTypeEnum.ADD,
                        BigDecimal.TEN)))
                .build();
    }

    public static SimulationDTO invalidSimulationDtoWithoutRequiredFields() {
        return dto.toBuilder()
                .distributionCenters(Collections.emptyList())
                .orderType(null)
                .classification(null)
                .simulationType(null)
                .note("")
                .calculationBasis(null)
                .operatorId(null)
                .build();
    }

    public static SimulationDTO invalidSimulationDtoWithPastAnticipationDate() {
        return dto.toBuilder().anticipationDate(LocalDate.of(2021, 1, 1)).build();
    }

    public static SimulationDTO invalidSimulationDtoWithDCWithoutIdAndName() {
        return dto.toBuilder().distributionCenters(List.of(new DistributionCenterRequest(null, null))).build();
    }

    public static SimulationDTO invalidSimulationDtoWithSupplierWithoutIdAndName() {
        return dto.toBuilder().suppliers(List.of(new SupplierRequest(null, null, null, null))).build();
    }

    public static SimulationDTO invalidSimulationDtoWithCategoryWithoutIdAndName() {
        return dto.toBuilder().categories(List.of(new CategoryRequest(null, ""))).build();
    }

    public static SimulationDTO invalidSimulationDtoWithSubcategoryWithoutIdNameAndCategoryId() {
        return dto.toBuilder().subcategories(List.of(new SubCategoryRequest(null, null, null))).build();
    }

    public static SimulationDTO invalidSimulationDtoWithOrderTypeWithoutIdAndName() {
        return dto.toBuilder().orderType(new OrderTypeRequest(null, null)).build();
    }

    public static SimulationDTO invalidSimulationDtoWithClassificationWithoutIdAndName() {
        return dto.toBuilder().classification(new SimClassificationRequest(null, null)).build();
    }

    public static SimulationDTO invalidSimulationDtoWithGeneralDiscountWithoutValueAndType() {
        return dto.toBuilder().generalDiscount(new GeneralDiscountDTO(null, null)).build();
    }

    public static SimulationDTO invalidSimulationDtoWithProductDiscountWithoutValueTypeIdAndName() {
        return dto.toBuilder().generalDiscount(null).productDiscounts(List.of(new ProductDiscountDTO())).build();
    }

    public static SimulationDTO invalidSimulationDtoWithProductFilterWithoutOnlyActive() {
        return dto.toBuilder().productFilter(new ProductFilterRequest(null, null, null, TemporaryInactiveEnum.SOMENTE_INATIVOS_TEMPORARIOS)).build();
    }

    public static SimulationDTO invalidSimulationDtoWithPaymentTermWithoutNameAndId() {
        return dto.toBuilder().newPaymentTerm(new SimNewPaymentTermRequest(null, null, null)).build();
    }

    public static SimulationDTO invalidSimulationDtoWithCalculationBasisWithoutId() {
        return dto.toBuilder()
                .calculationBasis(new SimCalculationBasisRequest(null, SalesByPeriodEnum.SEVEN_DAYS))
                .build();
    }

    public static SimulationStatusRequest validSimulationParametersRequest(Long simulationId) {
        return SimulationStatusRequest.builder()
                .id(simulationId)
                .build();
    }

    public static SimulationDTO mockedSimulationParametersResponse(Long simulationId) {
        return dto.toBuilder().id(simulationId).build();
    }

    public static SimulationStatusRequest validSimulationStatusRequest(Long simulationId) {
        return SimulationStatusRequest.builder()
                .id(simulationId)
                .build();
    }

    public static SimulationStatusResponse mockedSimulationStatusResponse() {
        return SimulationStatusResponse.builder()
                .id(2)
                .name("Rascunho")
                .active(true)
                .registerOperator(9998L)
                .build();
    }

    public static SimulationStatusResponse mockedSimulationStatusNullResponse() {
        return SimulationStatusResponse.builder()
                .build();
    }

    public static List<SimulationSummaryResponse> mockedSummaryList(Long simulationId) {
        return Arrays.asList(mockedSummary(simulationId));
    }

    public static SimulationSummaryResponse mockedSummary(Long simulationId) {
        return SimulationSummaryResponse.builder().simulationId(simulationId).distributionCenterId(List.of(903L)).build();
    }

    public static List<SimulationSummaryDCResponse> mockedSummaryDCList(Long simulationId) {
        return List.of(mockedSummaryDC(simulationId));
    }

    public static SimulationSummaryDCResponse mockedSummaryDC(Long simulationId) {
        return SimulationSummaryDCResponse.builder().simulationId(simulationId).distributionCenterId(903L).build();
    }

    public static List<SimulationStatusResponse> mockedSimulationStatusResponses() {
        SimulationStatusResponse draftStatus = SimulationStatusResponse.builder()
                .registerDate(LocalDate.of(2021, 10, 21))
                .registerOperator(9998L)
                .id(2)
                .name("Rascunho")
                .active(true)
                .build();

        SimulationStatusResponse processingStatus = SimulationStatusResponse.builder()
                .registerDate(LocalDate.of(2021, 10, 21))
                .registerOperator(9998L)
                .id(3)
                .name("Processando")
                .active(true)
                .build();

        SimulationStatusResponse errorStatus = SimulationStatusResponse.builder()
                .registerDate(LocalDate.of(2021, 10, 21))
                .registerOperator(9998L)
                .id(4)
                .name("Erro")
                .active(true)
                .build();

        return List.of(draftStatus, processingStatus, errorStatus);
    }

    public static SimulationOrderRequest mockedSimulationOrderRequest(Long simulationId) {
        return SimulationOrderRequest.builder()
                .simulationOrderCDSupplierRequest(List.of(SimulationOrderCDSupplierRequest.builder()
                        .distributionCenter(new DistributionCenterRequest(903L, "CD RIO JANEIRO"))
                        .billingSupplier(new BillingSupplierRequest(1L,"a", BigDecimal.valueOf(12L)))
                        .flagDispatch(true)
                        .build()))
                .simulationId(simulationId)
                .orderDispatchDate(LocalDate.now())
                .reasonPartialOrdering("my Amazing Reason")
                .taxOperation(TaxOperationTypeEnum.RESALE_PURCHASE)
                .build();
    }

}