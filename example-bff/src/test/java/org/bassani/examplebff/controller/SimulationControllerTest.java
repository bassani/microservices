package org.bassani.examplebff.controller;

import org.bassani.examplebff.client.CoreClient;
import org.bassani.examplebff.mock.SimulationCashCycleMock;
import org.bassani.examplebff.mock.SimulationMocks;
import org.bassani.examplebff.mock.SimulationOrderSummaryResponseMocks;
import org.bassani.examplebff.mock.SimulationTemplateResponseMocks;
import org.bassani.examplebff.service.KeyCloakService;
import org.bassani.examplebff.service.SimulationCashCycleService;
import org.bassani.examplebff.service.SimulationOrderSummaryService;
import org.bassani.examplebff.service.SimulationService;
import org.bassani.examplebff.service.SimulationTemplateService;
import org.bassani.examplemodellib.domain.dto.SimulationDTO;
import org.bassani.examplemodellib.domain.request.SimulationOrderRequest;
import org.bassani.examplemodellib.domain.response.SimulationOrderSummaryResponse;
import org.bassani.examplemodellib.domain.response.SimulationProductResponseWrapper;
import org.bassani.examplemodellib.domain.response.SimulationSummaryCashCycleResponse;
import org.bassani.examplemodellib.domain.response.SimulationSummaryDCResponse;
import org.bassani.examplemodellib.domain.response.SimulationSummaryResponse;
import org.bassani.examplemodellib.domain.response.SimulationTemplateResponse;
import org.bassani.examplemodellib.util.MessageBundle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.bassani.examplebff.mock.SimulationMocks.invalidSimulationDtoWithBothGeneralAndProductDiscounts;
import static org.bassani.examplebff.mock.SimulationMocks.invalidSimulationDtoWithNoSupplierNorCategories;
import static org.bassani.examplebff.mock.SimulationMocks.invalidSimulationDtoWithSupplierWithoutIdAndName;
import static org.bassani.examplebff.mock.SimulationMocks.invalidSimulationDtoWithoutRequiredFields;
import static org.bassani.examplebff.mock.SimulationMocks.validSimulationDtoWithGeneralDiscount;
import static org.bassani.examplemodellib.util.Mocks.asJson;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(SimulationController.class)
@AutoConfigureMockMvc(addFilters = false)
class SimulationControllerTest {

    private static final String URL = "/purchases/simulations";

    @Autowired private MockMvc mvc;
    @MockBean private SimulationService service;
    @MockBean private SimulationTemplateService simulationTemplateService;
    @MockBean private SimulationOrderSummaryService simulationOrderSummaryService;
    @MockBean private SimulationCashCycleService simulationCashCycleService;
    @MockBean private KeyCloakService keyCloakService;
    @MockBean private CoreClient coreClient;

    @WithMockUser(roles="SIMULATION_SIMULATE")
    @Test
    @DisplayName("POST /simulations -> OK ")
    void givenRequestIsValid_thenReturnValidJson() throws Exception {
        SimulationDTO request = validSimulationDtoWithGeneralDiscount();
        String requestBody = asJson(request);
        SimulationDTO response = validSimulationDtoWithGeneralDiscount();
        response.setId(1L);

        when(service.saveParameters(request)).thenReturn(response);

        mvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id").value(response.getId()))
                .andExpect(
                        jsonPath("$.distributionCenters[0].id").value(response.getDistributionCenters().get(0).getId()))
                .andExpect(jsonPath("$.suppliers[0].id").value(response.getSuppliers().get(0).getId()))
                .andExpect(jsonPath("$.categories[0].id").value(response.getCategories().get(0).getId()))
                .andExpect(jsonPath("$.subcategories[0].id").value(response.getSubcategories().get(0).getId()))
                .andExpect(jsonPath("$.subcategories[0].id").value(response.getSubcategories().get(0).getId()))
                .andExpect(jsonPath("$.classification.id").value(response.getClassification().getId()))
                .andExpect(jsonPath("$.generalDiscount.value").value(response.getGeneralDiscount().getValue()))
                .andExpect(jsonPath("$.orderType.id").value(response.getOrderType().getId()))
                .andExpect(jsonPath("$.productFilter.onlyActive").value(response.getProductFilter().getOnlyActive()))
                .andExpect(jsonPath("$.note").value(response.getNote()))
                .andExpect(jsonPath("$.simulationType").value(response.getSimulationType().getId()))
                .andExpect(jsonPath("$.newPaymentTerm.newPaymentTermGeneral.newPaymentTermCode").value(response.getNewPaymentTerm().getNewPaymentTermGeneral().getNewPaymentTermCode()))
                .andExpect(jsonPath("$.operatorId").value(response.getOperatorId()))
                .andExpect(jsonPath("$.anticipationDate").value(response.getAnticipationDate().toString()));

        verify(service, Mockito.times(1)).saveParameters(request);
    }

    @WithMockUser(roles="SIMULATION_SIMULATE")
    @Test
    @DisplayName("POST /simulations -> BAD_REQUEST: Requires Supplier Or Categories")
    void givenRequestWithNoSuppliersNorCategories_thenReturnBadRequest() throws Exception {
        SimulationDTO request = invalidSimulationDtoWithNoSupplierNorCategories();
        String requestBody = asJson(request);

        mvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message").hasJsonPath())
                .andExpect(jsonPath("$.descriptions[0]").value(containsString("simulationDTO")));
        verifyZeroInteractions(service);
    }

    @WithMockUser(roles="SIMULATION_SIMULATE")
    @Test
    @DisplayName("POST /simulations -> BAD_REQUEST: Can't be both General and Products Discounts")
    void givenRequestWithBothGeneralAndProductDiscounts_thenReturnBadRequest() throws Exception {
        SimulationDTO request = invalidSimulationDtoWithBothGeneralAndProductDiscounts();
        String requestBody = asJson(request);

        mvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message").value(MessageBundle.getMessage("validation.failure.message")))
                .andExpect(jsonPath("$.descriptions[0]").value(containsString("generalDiscount")));
        verifyZeroInteractions(service);
    }

    @WithMockUser(roles="SIMULATION_SIMULATE")
    @Test
    @DisplayName("POST /simulations -> BAD_REQUEST: Required fields not set")
    void givenRequestWithNoRequiredFields_thenReturnBadRequest() throws Exception {
        SimulationDTO request = invalidSimulationDtoWithoutRequiredFields();
        String requestBody = asJson(request);
        int numberOfRequiredFields = 7;

        mvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message").value(MessageBundle.getMessage("validation.failure.message")))
                .andExpect(jsonPath("$.descriptions.length()").value(numberOfRequiredFields));
        verifyZeroInteractions(service);
    }

    @WithMockUser(roles="SIMULATION_SIMULATE")
    @Test
    @DisplayName("POST /simulations -> BAD_REQUEST: Past anticipationDate")
    void givenRequestWithPastAnticipationDate_thenReturnBadRequest() throws Exception {
        SimulationDTO request = SimulationMocks.invalidSimulationDtoWithPastAnticipationDate();
        String requestBody = asJson(request);

        mvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message").value(MessageBundle.getMessage("validation.failure.message")))
                .andExpect(jsonPath("$.descriptions[0]").value(containsString("anticipationDate")));
        verifyZeroInteractions(service);
    }

    @WithMockUser(roles="SIMULATION_SIMULATE")
    @Test
    @DisplayName("POST /simulations -> BAD_REQUEST: DC hasn't got id/name")
    void givenRequestWithInvalidDC_thenReturnBadRequest() throws Exception {
        SimulationDTO request = SimulationMocks.invalidSimulationDtoWithDCWithoutIdAndName();
        String requestBody = asJson(request);
        int numberOfRequiredFields = 2;

        mvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message").value(MessageBundle.getMessage("validation.failure.message")))
                .andExpect(jsonPath("$.descriptions[0]").value(containsString("distributionCenters")))
                .andExpect(jsonPath("$.descriptions.length()").value(numberOfRequiredFields));
        verifyZeroInteractions(service);
    }

    @WithMockUser(roles="SIMULATION_SIMULATE")
    @Test
    @DisplayName("POST /simulations -> BAD_REQUEST: Supplier hasn't got id/name")
    void givenRequestWithInvalidSupplier_thenReturnBadRequest() throws Exception {
        SimulationDTO request = invalidSimulationDtoWithSupplierWithoutIdAndName();
        String requestBody = asJson(request);
        int numberOfRequiredFields = 2;

        mvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message").value(MessageBundle.getMessage("validation.failure.message")))
                .andExpect(jsonPath("$.descriptions[0]").value(containsString("suppliers")))
                .andExpect(jsonPath("$.descriptions.length()").value(numberOfRequiredFields));
        verifyZeroInteractions(service);
    }

    @WithMockUser(roles="SIMULATION_SIMULATE")
    @Test
    @DisplayName("POST /simulations -> BAD_REQUEST: Category hasn't got id/name")
    void givenRequestWithInvalidCategory_thenReturnBadRequest() throws Exception {
        SimulationDTO request = SimulationMocks.invalidSimulationDtoWithCategoryWithoutIdAndName();
        String requestBody = asJson(request);
        int numberOfRequiredFields = 2;

        mvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message").value(MessageBundle.getMessage("validation.failure.message")))
                .andExpect(jsonPath("$.descriptions[0]").value(containsString("categories")))
                .andExpect(jsonPath("$.descriptions.length()").value(numberOfRequiredFields));
        verifyZeroInteractions(service);
    }

    @WithMockUser(roles="SIMULATION_SIMULATE")
    @Test
    @DisplayName("POST /simulations -> BAD_REQUEST: Subcategory hasn't got id/name")
    void givenRequestWithInvalidSubcategory_thenReturnBadRequest() throws Exception {
        SimulationDTO request = SimulationMocks.invalidSimulationDtoWithSubcategoryWithoutIdNameAndCategoryId();
        String requestBody = asJson(request);
        int numberOfRequiredFields = 3;

        mvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message").value(MessageBundle.getMessage("validation.failure.message")))
                .andExpect(jsonPath("$.descriptions[0]").value(containsString("subcategories")))
                .andExpect(jsonPath("$.descriptions.length()").value(numberOfRequiredFields));
        verifyZeroInteractions(service);
    }

    @WithMockUser(roles="SIMULATION_SIMULATE")
    @Test
    @DisplayName("POST /simulations -> BAD_REQUEST: Order type hasn't got id/name")
    void givenRequestWithInvalidOrderType_thenReturnBadRequest() throws Exception {
        SimulationDTO request = SimulationMocks.invalidSimulationDtoWithOrderTypeWithoutIdAndName();
        String requestBody = asJson(request);
        int numberOfRequiredFields = 2;

        mvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message").value(MessageBundle.getMessage("validation.failure.message")))
                .andExpect(jsonPath("$.descriptions[0]").value(containsString("orderType")))
                .andExpect(jsonPath("$.descriptions.length()").value(numberOfRequiredFields));
        verifyZeroInteractions(service);
    }

    @WithMockUser(roles="SIMULATION_SIMULATE")
    @Test
    @DisplayName("POST /simulations -> BAD_REQUEST: Classification hasn't got id/name")
    void givenRequestWithInvalidClassification_thenReturnBadRequest() throws Exception {
        SimulationDTO request = SimulationMocks.invalidSimulationDtoWithClassificationWithoutIdAndName();
        String requestBody = asJson(request);
        int numberOfRequiredFields = 2;

        mvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message").value(MessageBundle.getMessage("validation.failure.message")))
                .andExpect(jsonPath("$.descriptions[0]").value(containsString("classification")))
                .andExpect(jsonPath("$.descriptions.length()").value(numberOfRequiredFields));
        verifyZeroInteractions(service);
    }

    @WithMockUser(roles="SIMULATION_SIMULATE")
    @Test
    @DisplayName("POST /simulations -> BAD_REQUEST: General discount hasn't got value/type")
    void givenRequestWithInvalidGeneralDiscount_thenReturnBadRequest() throws Exception {
        SimulationDTO request = SimulationMocks.invalidSimulationDtoWithGeneralDiscountWithoutValueAndType();
        String requestBody = asJson(request);
        int numberOfRequiredFields = 2;

        mvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message").value(MessageBundle.getMessage("validation.failure.message")))
                .andExpect(jsonPath("$.descriptions[0]").value(containsString("generalDiscount")))
                .andExpect(jsonPath("$.descriptions.length()").value(numberOfRequiredFields))
                .andDo(print());
        verifyZeroInteractions(service);
    }

    @WithMockUser(roles="SIMULATION_SIMULATE")
    @Test
    @DisplayName("POST /simulations -> BAD_REQUEST: Product discount hasn't got value/type/id/name")
    void givenRequestWithInvalidProductDiscount_thenReturnBadRequest() throws Exception {
        SimulationDTO request = SimulationMocks.invalidSimulationDtoWithProductDiscountWithoutValueTypeIdAndName();
        String requestBody = asJson(request);
        int numberOfRequiredFields = 4;

        mvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message").value(MessageBundle.getMessage("validation.failure.message")))
                .andExpect(jsonPath("$.descriptions[0]").value(containsString("productDiscounts")))
                .andExpect(jsonPath("$.descriptions.length()").value(numberOfRequiredFields));
        verifyZeroInteractions(service);
    }

    @WithMockUser(roles="SIMULATION_SIMULATE")
    @Test
    @DisplayName("POST /simulations -> BAD_REQUEST: Product filter hasn't got onlyActive")
    void givenRequestWithInvalidProductFilter_thenReturnBadRequest() throws Exception {
        SimulationDTO request = SimulationMocks.invalidSimulationDtoWithProductFilterWithoutOnlyActive();
        String requestBody = asJson(request);

        mvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message").value(MessageBundle.getMessage("validation.failure.message")));
        verifyZeroInteractions(service);
    }

    @WithMockUser(roles="SIMULATION_SIMULATE")
    @Test
    @DisplayName("POST /simulations -> BAD_REQUEST: Payment term hasn't got id/name")
    void givenRequestWithInvalidPaymentTerm_thenReturnBadRequest() throws Exception {

        SimulationDTO request = SimulationMocks.invalidSimulationDtoWithPaymentTermWithoutNameAndId();
        String requestBody = asJson(request);
        
        int numberOfRequiredFields = 1;

        mvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message").value(MessageBundle.getMessage("validation.failure.message")))
                .andExpect(jsonPath("$.descriptions[0]").value(containsString("newPaymentTerm")))
                .andExpect(jsonPath("$.descriptions.length()").value(numberOfRequiredFields));
        verifyZeroInteractions(service);
    }

    @WithMockUser(roles="SIMULATION_SIMULATE")
    @Test
    @DisplayName("POST /simulations -> BAD_REQUEST: Calculation basis hasn't got id")
    void givenRequestWithInvalidCalculationBasis_thenReturnBadRequest() throws Exception {
        SimulationDTO request = SimulationMocks.invalidSimulationDtoWithCalculationBasisWithoutId();
        String requestBody = asJson(request);
        int numberOfRequiredFields = 1;

        mvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message").value(MessageBundle.getMessage("validation.failure.message")))
                .andExpect(jsonPath("$.descriptions[0]").value(containsString("calculationBasis")))
                .andExpect(jsonPath("$.descriptions.length()").value(numberOfRequiredFields));
        verifyZeroInteractions(service);
    }

    @WithMockUser(roles="SIMULATION_GETPARAMETERS")
    @Test
    @DisplayName("GET /simulations/{simulationId} -> OK ")
    void givenSimulationParametersRequestIsValid_thenReturnValidJson() throws Exception {
        Long simulationId = 67L;
        SimulationDTO expectedParameters;
        expectedParameters = SimulationMocks.mockedSimulationParametersResponse(simulationId);

        when(service.getSimulationParameters(simulationId)).thenReturn(expectedParameters);

        mvc.perform(get(URL + "/{simulationId}", simulationId).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id").value(expectedParameters.getId()));

        verify(service, Mockito.times(1)).getSimulationParameters(simulationId);
    }

    @WithMockUser(roles="SIMULATION_GETSUMMARY")
    @Test
    @DisplayName("POST /simulations/{simulationId}/summary -> OK ")
    void givenSimulationSummaryRequestIsValid_thenReturnValidJson() throws Exception {
        Long simulationId = 890L;
        List<SimulationSummaryResponse> responses = SimulationMocks.mockedSummaryList(simulationId);
        SimulationSummaryResponse firstResponse = responses.get(0);

        when(service.getSimulationSummary(simulationId)).thenReturn(responses);

        mvc.perform(get(URL + "/{simulationId}/summary", simulationId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].simulationId").value(firstResponse.getSimulationId()))
                .andExpect(jsonPath("$[0].distributionCenterId[0]").value(firstResponse.getDistributionCenterId().get(0)));


        verify(service, Mockito.times(1)).getSimulationSummary(simulationId);
    }

    @WithMockUser(roles="SIMULATION_GETSUMMARYDC")
    @Test
    @DisplayName("POST /simulations/{simulationId}/summary-dc -> OK ")
    void givenSimulationSummaryDCRequestIsValid_thenReturnValidJson() throws Exception {
        Long simulationId = 890L;
        List<SimulationSummaryDCResponse> responses = SimulationMocks.mockedSummaryDCList(simulationId);
        SimulationSummaryDCResponse firstResponse = responses.get(0);

        when(service.getSimulationSummaryDC(simulationId)).thenReturn(responses);

        mvc.perform(get(URL + "/{simulationId}/summary-dc", simulationId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].simulationId").value(firstResponse.getSimulationId()))
                .andExpect(jsonPath("$[0].distributionCenterId").value(firstResponse.getDistributionCenterId()));


        verify(service, Mockito.times(1)).getSimulationSummaryDC(simulationId);
    }

    @WithMockUser(roles="SIMULATION_TEMPLATE")
    @Test
    @DisplayName("GET /simulations/{simulationId}/template -> OK ")
    void givenSimulationIdIsValid_thenReturnValidJsonWithSimulationTemplate() throws Exception {
        Long simulationId = 67L;
        SimulationTemplateResponse templateResponse = SimulationTemplateResponseMocks.validSimulationTemplateResponse();

        when(simulationTemplateService.getSimulationTemplate(simulationId)).thenReturn(templateResponse);

        mvc.perform(get(URL + "/{simulationId}/template", simulationId).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.purchaseValue").value(templateResponse.getPurchaseValue()))
                .andExpect(jsonPath("$.orderClassification").value(templateResponse.getOrderClassification()))
                .andExpect(jsonPath("$.totalBudgetValue").value(templateResponse.getTotalBudgetValue()))
                .andExpect(jsonPath("$.gain").value(templateResponse.getGain()))
                .andExpect(jsonPath("$.cashFlowImpact").value(templateResponse.getCashFlowImpact()));

        verify(simulationTemplateService, Mockito.times(1)).getSimulationTemplate(simulationId);
    }

    @WithMockUser(roles="SIMULATION_TEMPLATE")
    @Test
    @DisplayName("GET /simulations/{simulationId}/template -> OK ")
    void givenSimulationIdIsValidAndIncorrectUrl_thenReturnSimulationTemplateNotFound() throws Exception {
        Long simulationId = 6700L;

        when(simulationTemplateService.getSimulationTemplate(simulationId)).thenReturn(null);

        mvc.perform(get(URL + "/{simulationId}/templates", simulationId).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());

        verify(simulationTemplateService, Mockito.times(0)).getSimulationTemplate(simulationId);
    }

    @WithMockUser(roles="SIMULATION_ORDERSUMMARY")
    @Test
    @DisplayName("GET /simulations/{simulationId}/order-summary -> OK ")
    void givenSimulationIdIsValid_thenReturnValidJsonWithSimulationOrderSummary() throws Exception {
        Long simulationId = 67L;
        SimulationOrderSummaryResponse orderSummaryResponse = SimulationOrderSummaryResponseMocks.validSimulationOrderSummaryResponse();

        when(simulationOrderSummaryService.getSimulationOrderSummary(simulationId)).thenReturn(orderSummaryResponse);

        mvc.perform(get(URL + "/{simulationId}/order-summary", simulationId).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.approvalCompetencyName").value(orderSummaryResponse.getApprovalCompetencyName()))
                .andExpect(jsonPath("$.orderClassification").value(orderSummaryResponse.getOrderClassification()))
                .andExpect(jsonPath("$.purchaseValue").value(orderSummaryResponse.getPurchaseValue()))
                .andExpect(jsonPath("$.parentSupplierName").value(orderSummaryResponse.getParentSupplierName()))
                .andExpect(jsonPath("$.calculationBasisName").value(orderSummaryResponse.getCalculationBasisName()))
                .andExpect(jsonPath("$.regularTermInDaysQuantity").value(orderSummaryResponse.getRegularTermInDaysQuantity()))
                .andExpect(jsonPath("$.newTermInDaysQuantity").value(orderSummaryResponse.getNewTermInDaysQuantity()))
                .andExpect(jsonPath("$.deltaTermInDaysQuantity").value(orderSummaryResponse.getDeltaTermInDaysQuantity()))
                .andExpect(jsonPath("$.regularPaymentDate").value(orderSummaryResponse.getRegularPaymentDate().toString()))
                .andExpect(jsonPath("$.negotiatedPaymentDate").value(orderSummaryResponse.getNegotiatedPaymentDate().toString()))
                .andExpect(jsonPath("$.cashFlowImpact").value(orderSummaryResponse.getCashFlowImpact()))
                .andExpect(jsonPath("$.budgetType").value(orderSummaryResponse.getBudgetType()))
                .andExpect(jsonPath("$.informedBudgetValue").value(orderSummaryResponse.getInformedBudgetValue()))
                .andExpect(jsonPath("$.totalBudgetValue").value(orderSummaryResponse.getTotalBudgetValue()))
                .andExpect(jsonPath("$.gain").value(orderSummaryResponse.getGain()))
                .andExpect(jsonPath("$.budgetReason").value(orderSummaryResponse.getBudgetReason()))
                .andExpect(jsonPath("$.stockDaysPurchase").value(orderSummaryResponse.getStockDaysPurchase()))
                .andExpect(jsonPath("$.dailyFinalStock").value(orderSummaryResponse.getDailyFinalStock()))
                .andExpect(jsonPath("$.dailyCDStock").value(orderSummaryResponse.getDailyCDStock()))
                .andExpect(jsonPath("$.dailyGridStock").value(orderSummaryResponse.getDailyGridStock()));


        verify(simulationOrderSummaryService, Mockito.times(1)).getSimulationOrderSummary(simulationId);
    }

    @WithMockUser(roles="SIMULATION_ORDERSUMMARY")
    @Test
    @DisplayName("GET /simulations/{simulationId}/order-summary -> OK ")
    void givenSimulationIdIsValidAndIncorrectUrl_thenReturnSimulationOrderSummaryNotFound() throws Exception {
        Long simulationId = 6700L;

        when(simulationOrderSummaryService.getSimulationOrderSummary(simulationId)).thenReturn(null);

        mvc.perform(get(URL + "/{simulationId}/order-summarys", simulationId).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());

        verify(simulationOrderSummaryService, Mockito.times(0)).getSimulationOrderSummary(simulationId);
    }

    @WithMockUser(roles="SIMULATION_TEMPLATE")
    @Test
    @DisplayName("GET /simulations/{simulationId}/cash-cycle -> OK ")
    void givenSimulationIdIsValid_thenReturnValidJsonWithSimulationCashCycle() throws Exception {
        Long simulationId = 67L;
        SimulationSummaryCashCycleResponse simulationCashCycleResponse = SimulationCashCycleMock.mockedSummaryResponse(simulationId);

        when(simulationCashCycleService.getSimulationCashCycle(simulationId)).thenReturn(simulationCashCycleResponse);

        mvc.perform(get(URL + "/{simulationId}/cash-cycle", simulationId).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.simulationId").value(simulationCashCycleResponse.getSimulationId()))
                .andExpect(jsonPath("$.impactCycleDays").value(simulationCashCycleResponse.getImpactCycleDays()))
                .andExpect(jsonPath("$.impactCycleReals").value(simulationCashCycleResponse.getImpactCycleReals()));


        verify(simulationCashCycleService, Mockito.times(1)).getSimulationCashCycle(simulationId);
    }

    @WithMockUser(roles="SIMULATION_TEMPLATE")
    @Test
    @DisplayName("GET /simulations/orders/{id} -> OK")
    public void getSimulationOrder() throws Exception {
        when(coreClient.getSimulationOrder(anyLong())).thenReturn(SimulationProductResponseWrapper.builder().build());
        mvc.perform(get(URL + "/orders/1")).andExpect(status().isOk());
    }

    @WithMockUser(roles="SIMULATION_CREATE_ORDER")
    @Test
    @DisplayName("POST /simulations/orders/{id} -> OK")
    public void createSimulationOrder() throws Exception {
        SimulationOrderRequest request = SimulationMocks.mockedSimulationOrderRequest(1L);

        when(coreClient.createSimulationOrder(anyLong(), any())).thenReturn(SimulationProductResponseWrapper.builder().build());
        mvc.perform(post(URL + "/orders/1").contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJson(request))).andExpect(status().isOk());
    }
}
