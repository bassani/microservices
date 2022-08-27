package org.bassani.examplebff.mock;

import org.bassani.examplemodellib.domain.entity.dbjor.OrderTypeParamEntity;
import org.bassani.examplemodellib.domain.entity.dbjor.SimulationParametersEntity;
import org.bassani.examplemodellib.domain.entity.dbjor.SimulationStatusEntity;
import org.bassani.examplemodellib.domain.request.SimulationFollowUpRequest;
import org.bassani.examplemodellib.domain.response.SimulationFollowUpResponse;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.bassani.examplebff.mock.NewPaymentTermSimParamEntityMock.getNewPaymentTermSimParamEntity_General;

public class SimulationFollowUpMock extends BaseAbstractMock implements TemplateLoader {
    @Override
    public void load() {
        buildSimulationParametersEntityComplete();
        buildSimulationFollowUpRequestComplete();
        buildSimulationFollowUpResponseComplete();
    }

    private void buildSimulationParametersEntityComplete() {
        Fixture.of(SimulationParametersEntity.class).addTemplate("SimulationParametersComplete", new Rule() {{
            add("id", random(Long.class, range(1L, 100L)));
            add("name", name());
            add("description", name());
            add("cnpjNumber", cnpj().toString());
            add("stateRegistrationNr", regex("\\w{49}"));
            add("orderFrequencyDaysQt", random(Long.class, range(1L, 100L)));
            add("securityMarginDaysQt", random(Long.class, range(1L, 100L)));
            add("breakOrderQt", random(Long.class, range(1L, 100L)));
            add("discountPc", random(BigDecimal.class));
            add("isDeleted", random(true, false));
            add("isEdi", random(true, false));
            add("isRevenueRegistrationStatus", random(true, false));
            add("isBalanceDelivery", random(true, false));
            add("supplierEanId", regex("\\w{49}"));
            add("supplierTypeId", random(Long.class, range(1L, 100L)));
            add("paymentTermsId", random(Long.class, range(1L, 4L)));
            add("layoutId", random(Long.class, range(1L, 10L)));
        }});
    }

    private void buildSimulationFollowUpResponseComplete() {
        Fixture.of(SimulationFollowUpResponse.class).addTemplate("SimulationFollowUpResponseComplete", new Rule() {{
            add("id", random(Long.class, range(1L, 100L)));
            add("registerDate", random(LocalDateTime.class));
            add("operatorId", random(Long.class, range(1L, 100L)));
            add("totalAmountWithNegotiatedDiscount", random(Double.class, range(1.00, 100.00)));
            add("orderedQty", random(Integer.class, range(1, 100)));
        }});
    }

    private void buildSimulationFollowUpRequestComplete() {
        Fixture.of(SimulationFollowUpRequest.class).addTemplate("SimulationFollowUpRequestComplete", new Rule() {{
            add("id", random(Long.class, range(1L, 100L)));
            add("name", name());
            add("parentSupplierIds", List.of(520l, 258l, 639l));
            add("parentSupplierName", name());
        }});
    }

    public static Page<SimulationParametersEntity> simulationParametersEntityPage(List<SimulationParametersEntity> dataReturn) {
        return new PageImpl<>(dataReturn, BaseAbstractMock.PAGE_REQUEST_DEFAULT, dataReturn.size());
    }

    public static Page<SimulationFollowUpResponse> simulationFollowUpResponsePage(List<SimulationFollowUpResponse> dataReturn) {
        return new PageImpl<>(dataReturn, BaseAbstractMock.PAGE_REQUEST_DEFAULT, dataReturn.size());
    }

    public static List<SimulationFollowUpResponse> mockedSimulationFollowUp() {
        return List.of(SimulationFollowUpResponse.builder().id(1L).status(SimulationStatusEntity.builder().id(4L).build()).build(),
                       SimulationFollowUpResponse.builder().id(2L).status(SimulationStatusEntity.builder().id(4L).build()).build());
    }

    public static SimulationParametersEntity mockedSimulationParametersId(Long simulationId) {

        SimulationParametersEntity response = SimulationParametersEntity.builder()
                .id(967L)
                .orderTypeParam(OrderTypeParamEntity.builder().build())
                .newPaymentTerm(getNewPaymentTermSimParamEntity_General())
                .note("Observação")
                .saleDay(7L)
                .onlyInactive(false)
                .operatorId(1L)
                .registerDate(LocalDateTime.of(2021, 9, 23, 15, 42, 14))
                .build();
        return response;
    }

    public static SimulationFollowUpRequest mockedSimulationRequestId(Long simulationStatusId) {

        SimulationFollowUpRequest request = SimulationFollowUpRequest.builder()
                .initialDate(LocalDate.now())
                .finalDate(LocalDate.now())
                .supplierIds(List.of(1L,2L))
                .operatorId(1L)
                .simulationTypeId(1L)
                .classificationId(8L)
                .parentSupplierId(List.of(2L))
                .statusId(4L)
                .build();
        return request;
    }

    public static SimulationFollowUpRequest mockedSimulationRequestWithNullFields() {

        SimulationFollowUpRequest request = SimulationFollowUpRequest.builder()
                .initialDate(null)
                .finalDate(null)
                .supplierIds(null)
                .operatorId(null)
                .simulationTypeId(null)
                .classificationId(null)
                .parentSupplierId(null)
                .statusId(null)
                .build();
        return request;
    }
}
