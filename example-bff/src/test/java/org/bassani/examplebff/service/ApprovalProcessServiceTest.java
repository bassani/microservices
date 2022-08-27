package org.bassani.examplebff.service;

import org.bassani.examplebff.client.BPMServerClient;
import org.bassani.examplebff.utils.KeyCloakUtils;
import org.bassani.examplemodellib.domain.dto.GeneralDiscountDTO;
import org.bassani.examplemodellib.domain.dto.SimulationDTO;
import org.bassani.examplemodellib.domain.request.CategoryRequest;
import org.bassani.examplemodellib.domain.request.DistributionCenterRequest;
import org.bassani.examplemodellib.domain.request.OrderTypeRequest;
import org.bassani.examplemodellib.domain.request.ProductFilterRequest;
import org.bassani.examplemodellib.domain.request.SimCalculationBasisRequest;
import org.bassani.examplemodellib.domain.request.SimClassificationRequest;
import org.bassani.examplemodellib.domain.request.SimulationApprovalCompleteRequest;
import org.bassani.examplemodellib.domain.request.SubCategoryRequest;
import org.bassani.examplemodellib.domain.request.SupplierRequest;
import org.bassani.examplemodellib.domain.response.SimulationSummaryResponse;
import org.bassani.examplemodellib.enums.DiscountTypeEnum;
import org.bassani.examplemodellib.enums.SalesByPeriodEnum;
import org.bassani.examplemodellib.enums.SimulationStatusEnum;
import org.bassani.examplemodellib.enums.SimulationTypeEnum;
import org.bassani.examplemodellib.enums.TemporaryInactiveEnum;
import org.camunda.bpm.engine.rest.dto.runtime.ProcessInstanceWithVariablesDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.representations.AccessToken;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class ApprovalProcessServiceTest {

    @InjectMocks ApprovalProcessService service;

    @Mock BPMServerClient bpmServerClient;
    @Mock SimulationSummaryResponse simulationSummaryResponseMock;

    @Test
    void givenValidTaskExists_whenCompleteApprovalTaskIsPerformed_thenTaskIsCompleted() {

        //scenario
        try (MockedStatic<KeyCloakUtils> keyCloakUtilsMockedStatic = Mockito.mockStatic(KeyCloakUtils.class)) {
            //scenario
            Map<String, String> expected = Map.of("expected", "value1", "key2", "value2", "key3", "value3");
            keyCloakUtilsMockedStatic.when(KeyCloakUtils::getOtherClaims).thenReturn(expected);
            keyCloakUtilsMockedStatic.when(KeyCloakUtils::getUser).thenReturn(Mockito.mock(KeycloakPrincipal.class));
            keyCloakUtilsMockedStatic.when(KeyCloakUtils::getAccessToken).thenReturn(Mockito.mock(AccessToken.class));
            keyCloakUtilsMockedStatic.when(KeyCloakUtils::getCDOperador).thenReturn("0123456");

            Long simulationId = 1L;
            Boolean approved = true;
            String reason = "myAmazingReason";
            SimulationApprovalCompleteRequest request =
                    SimulationApprovalCompleteRequest.builder().approved(approved).reason(reason).build();

            Mockito.when(bpmServerClient.completeApprovalTask(anyLong(), any(Map.class))).thenReturn(ResponseEntity.ok(true));

            //action
            boolean result = service.completeApprovalTask(simulationId, request);

            //validation
            Assertions.assertTrue(result);

            Mockito.verify(bpmServerClient).completeApprovalTask(anyLong(), any(Map.class));
        }
    }

    @Test
    void givenValidSimulationIdExists_whenStartApprovalProcessInstanceIsPerformed_thenTaskIsCreated() {

        //scenario
        try (MockedStatic<KeyCloakUtils> keyCloakUtilsMockedStatic = Mockito.mockStatic(KeyCloakUtils.class)) {
            //scenario
            Map<String, String> expected = Map.of("expected", "value1", "key2", "value2", "key3", "value3");
            keyCloakUtilsMockedStatic.when(KeyCloakUtils::getOtherClaims).thenReturn(expected);
            keyCloakUtilsMockedStatic.when(KeyCloakUtils::getUser).thenReturn(Mockito.mock(KeycloakPrincipal.class));
            keyCloakUtilsMockedStatic.when(KeyCloakUtils::getAccessToken).thenReturn(Mockito.mock(AccessToken.class));
            keyCloakUtilsMockedStatic.when(KeyCloakUtils::getCDOperador).thenReturn("0123456");

            Long simulationId = 1L;
            Boolean approved = false;
            String reason = "myAmazingReason";

            SimulationDTO simulationDTO = SimulationDTO.builder()
                    .distributionCenters(List.of(new DistributionCenterRequest(903L, "CD RIO JANEIRO")))
                    .suppliers(List.of(new SupplierRequest(292L, "OSLER", 292L, "OSLERPAI")))
                    .categories(List.of(new CategoryRequest(201L, "GENERICOS")))
                    .subcategories(List.of(new SubCategoryRequest(575L, "GENERICOS", 201L)))
                    .classification(new SimClassificationRequest(1L, "BLACK FRIDAY"))
                    .generalDiscount(new GeneralDiscountDTO(DiscountTypeEnum.ADD, new BigDecimal("9.99")))
                    .orderType(new OrderTypeRequest(1L, "Normal"))
                    .calculationBasis(new SimCalculationBasisRequest(1L, SalesByPeriodEnum.SEVEN_DAYS))
                    .productFilter(new ProductFilterRequest(true, null, null, TemporaryInactiveEnum.SOMENTE_INATIVOS_TEMPORARIOS))
                    .note("Observações")
                    .simulationType(SimulationTypeEnum.ANTECIPACAO)
                    .simulationStatus(SimulationStatusEnum.DRAFT)
                    .operatorId(3331987L)
                    .anticipationDate(LocalDate.MAX)
                    .build();

            Map<String, Object> variables = new HashMap<>();
            variables.put("operatorId", simulationDTO.getOperatorId());
            variables.put("areaId", 1L);
            variables.put("profileId", 1L);
            variables.put("withVariablesInReturn", true);

            //action
            ProcessInstanceWithVariablesDto result = service.startApprovalProcessInstance(simulationId);

            //validation
            Mockito.verify(bpmServerClient, times(1)).startApprovalProcessInstance(anyLong(), any(Map.class));
        }
    }

}
