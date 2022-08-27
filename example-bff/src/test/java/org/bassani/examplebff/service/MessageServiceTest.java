package org.bassani.examplebff.service;

import org.bassani.examplebff.client.NotifyClient;
import org.bassani.examplebff.mock.MessageMock;
import org.bassani.examplebff.utils.KeyCloakUtils;
import org.bassani.examplemodellib.domain.response.MessageUserNotificationResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.representations.AccessToken;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {
    @Mock
    private NotifyClient client;

    @InjectMocks
    private MessageService service;

    @Test
    public void givenCaegorysHasList_whenGetAllIsPerformed_thenAllCategoryAreReturned() {

        try (MockedStatic<KeyCloakUtils> keyCloakUtilsMockedStatic = Mockito.mockStatic(KeyCloakUtils.class)) {
            //scenario
            Map<String, String> expected = Map.of("expected", "value1", "key2", "value2", "key3", "value3");
            keyCloakUtilsMockedStatic.when(KeyCloakUtils::getOtherClaims).thenReturn(expected);
            keyCloakUtilsMockedStatic.when(KeyCloakUtils::getUser).thenReturn(Mockito.mock(KeycloakPrincipal.class));
            keyCloakUtilsMockedStatic.when(KeyCloakUtils::getAccessToken).thenReturn(Mockito.mock(AccessToken.class));
            keyCloakUtilsMockedStatic.when(KeyCloakUtils::getCDOperador).thenReturn("0123456");
            keyCloakUtilsMockedStatic.when(KeyCloakUtils::getKeycloakUserId).thenReturn("553046d0-6365-46ca-999a-8c6a27c87cb8");


            Pageable pageable = PageRequest.of(1, 20);
            Page<MessageUserNotificationResponse> expectedResp = MessageMock.mockedResponsePage();
            List<MessageUserNotificationResponse> page = expectedResp.getContent();

            lenient().when(client.findAllByRecipient(anyString(), any(Pageable.class))).thenReturn(expectedResp);
            //
            Page<MessageUserNotificationResponse> actual = service.findAllByRecipient(pageable);
            //
            assertIterableEquals(expectedResp.getContent(), actual.getContent());
        }
    }

}
