package org.bassani.examplebff.mock;

import org.bassani.examplemodellib.domain.dto.MessageDTO;
import org.bassani.examplemodellib.domain.projection.MessageUserNotificationProjection;
import org.bassani.examplemodellib.domain.response.MessageUserNotificationResponse;
import org.bassani.examplemodellib.enums.SimulationStatusEnum;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class MessageMock implements TemplateLoader {

    private final static Integer PAGE = 0;
    private final static Integer SIZE = 25;
    private final static Sort SORT = Sort.by("notificationDate").descending();

    @Override
    public void load() {
        buildNotificationResponseComplete();
    }

    public static Page<MessageUserNotificationProjection> mockedProjectionPage() {

        List<MessageUserNotificationProjection> response = List.of(mockedProjectionId(1L));

        return new PageImpl<>(response);
    }

    public static Page<MessageUserNotificationResponse> mockedResponsePage() {

        List<MessageUserNotificationResponse> response = List.of(mockedResponseId(1L));

        return new PageImpl<>(response);
    }

    public static MessageUserNotificationResponse mockedResponseId(Long id) {
        return MessageUserNotificationResponse.builder().id(id).read(Boolean.FALSE)
                .notificationDate(LocalDateTime.now()).keycloakUserId("553046d0-6365-46ca-999a-8c6a27c87cb8")
                .message(MessageDTO.builder().simulationId(888L)
                         .simulationStatus(SimulationStatusEnum.PENDENTE_APROVACAO_COORDENADOR_COMERCIAL).build())
                .build();
    }

    public static MessageUserNotificationProjection mockedProjectionId(Long id) {
        var mock = Mockito.mock(MessageUserNotificationProjection.class);
        Mockito.when(mock.getId()).thenReturn(id);
        Mockito.when(mock.getRead()).thenReturn(Boolean.FALSE);
        Mockito.when(mock.getNotificationDate()).thenReturn(LocalDateTime.now());
        Mockito.when(mock.getKeycloakUserId()).thenReturn("553046d0-6365-46ca-999a-8c6a27c87cb8");
        Mockito.when(mock.getMessage()).thenReturn(MessageDTO.builder().simulationId(888L)
                .simulationStatus(SimulationStatusEnum.PENDENTE_APROVACAO_COORDENADOR_COMERCIAL).build());
        return mock;
    }

    private void buildNotificationResponseComplete() {

        Fixture.of(MessageUserNotificationResponse.class).addTemplate("MessageUserNotificationResponseComplete", new Rule() {
            {
                add("id", random(Long.class, range(1L, 50L)));
                add("read", random(true, false));
                add("notificationDate",  LocalDate.now());
                add("keycloakUserId", regex("[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}"));
                add("message", "{\"simulationStatus\":\"PENDENTE_APROVACAO_COORDENADOR_ABASTECIMENTO\",\"simulationId\":995,\"parentSupplierName\":\"ACHE\",\"classification\":{\"id\":1,\"name\":\"BLACK FRIDAY\"},\"completedBy\":null,\"reason\":null}");
            }
        });
    }
}
