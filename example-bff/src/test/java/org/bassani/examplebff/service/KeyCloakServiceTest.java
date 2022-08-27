package org.bassani.examplebff.service;

import org.bassani.examplebff.client.CoreClient;
import org.bassani.examplebff.client.KeyCloakClient;
import org.bassani.examplebff.client.SourceOperatorClient;
import org.bassani.examplebff.utils.KeyCloakUtils;
import org.bassani.examplemodellib.domain.request.KeyCloakUserDetailRequest;
import org.bassani.examplemodellib.domain.response.KeyCloakUserDetailResponse;
import org.bassani.examplemodellib.domain.response.OperatorResponse;
import org.bassani.examplemodellib.domain.response.ProfileResponse;
import org.bassani.examplemodellib.exception.GroupNotFoundException;
import org.bassani.examplemodellib.exception.OperatorNotFoundException;
import org.bassani.examplemodellib.exception.ProfileNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class KeyCloakServiceTest {

    @InjectMocks KeyCloakService service;

    @Mock private SourceOperatorClient sourceOperatorClient;
    @Mock private KeyCloakClient keyCloakClient;
    @Mock private CoreClient coreClient;

    @Test
    void testUpdateTokenAttribute_whenOperatorDoesNotExist_thenThrowOperatorNotFoundException() {

        try (MockedStatic<KeyCloakUtils> keyCloakUtilsMockedStatic = Mockito.mockStatic(KeyCloakUtils.class)) {

            //scenario
            keyCloakUtilsMockedStatic.when(KeyCloakUtils::getEmail).thenReturn("e@mail.com");
            Mockito.when(keyCloakClient.getAllUsersWithoutPagination(Mockito.anyBoolean(), Mockito.anyString()))
                    .thenThrow(OperatorNotFoundException.class);

            //action
            Assertions.assertThrows(OperatorNotFoundException.class, () -> service.updateTokenAttribute());

            //validation
            keyCloakUtilsMockedStatic.verify(KeyCloakUtils::getEmail);
            Mockito.verify(keyCloakClient, times(1)).getAllUsersWithoutPagination(Mockito.anyBoolean(), Mockito.anyString());
            Mockito.verify(coreClient, times(0)).getProfile(Mockito.anyString());
            Mockito.verify(keyCloakClient, times(0))
                    .updateUserAttribute(Mockito.anyString(), Mockito.any(KeyCloakUserDetailRequest.class));
            Mockito.verifyNoMoreInteractions(keyCloakClient, sourceOperatorClient, coreClient);
        }
    }

    @Test
    void testUpdateTokenAttribute_whenGroupDoesNotExist_thenThrowGroupNotFoundException() {

        try (MockedStatic<KeyCloakUtils> keyCloakUtilsMockedStatic = Mockito.mockStatic(KeyCloakUtils.class)) {

            //scenario
            keyCloakUtilsMockedStatic.when(KeyCloakUtils::getEmail).thenReturn("e@mail.com");
            keyCloakUtilsMockedStatic.when(KeyCloakUtils::getGroup).thenThrow(GroupNotFoundException.class);
            Mockito.when(keyCloakClient.getAllUsersWithoutPagination(Mockito.anyBoolean(), Mockito.anyString()))
                    .thenReturn(List.of(KeyCloakUserDetailResponse.builder().build()));

            //action
            Assertions.assertThrows(GroupNotFoundException.class, () -> service.updateTokenAttribute());

            //validation
            keyCloakUtilsMockedStatic.verify(KeyCloakUtils::getEmail, Mockito.times(1));
            keyCloakUtilsMockedStatic.verify(KeyCloakUtils::getGroup, Mockito.times(1));
            Mockito.verify(keyCloakClient, times(1)).getAllUsersWithoutPagination(Mockito.anyBoolean(), Mockito.anyString());
            Mockito.verify(coreClient, times(0)).getProfile(Mockito.anyString());
            Mockito.verify(keyCloakClient, times(0))
                    .updateUserAttribute(Mockito.anyString(), Mockito.any(KeyCloakUserDetailRequest.class));
            Mockito.verifyNoMoreInteractions(keyCloakClient, sourceOperatorClient, coreClient);
        }
    }

    @Test
    void testUpdateTokenAttribute_whenProfileDoesNotExist_thenThrowProfileNotFoundException() {

        try (MockedStatic<KeyCloakUtils> keyCloakUtilsMockedStatic = Mockito.mockStatic(KeyCloakUtils.class)) {

            //scenario
            keyCloakUtilsMockedStatic.when(KeyCloakUtils::getEmail).thenReturn("e@mail.com");
            keyCloakUtilsMockedStatic.when(KeyCloakUtils::getGroup).thenReturn(Optional.of("myAmazingGroup"));
            Mockito.when(keyCloakClient.getAllUsersWithoutPagination(Mockito.anyBoolean(), Mockito.anyString()))
                    .thenReturn(List.of(KeyCloakUserDetailResponse.builder().build()));
            Mockito.when(coreClient.getProfile(Mockito.anyString())).thenThrow(ProfileNotFoundException.class);

            //action
            Assertions.assertThrows(ProfileNotFoundException.class, () -> service.updateTokenAttribute());

            //validation
            keyCloakUtilsMockedStatic.verify(KeyCloakUtils::getEmail, Mockito.times(1));
            keyCloakUtilsMockedStatic.verify(KeyCloakUtils::getGroup, Mockito.times(1));
            Mockito.verify(keyCloakClient, times(1)).getAllUsersWithoutPagination(Mockito.anyBoolean(), Mockito.anyString());
            Mockito.verify(coreClient, times(1)).getProfile(Mockito.anyString());
            Mockito.verify(keyCloakClient, times(0))
                    .updateUserAttribute(Mockito.anyString(), Mockito.any(KeyCloakUserDetailRequest.class));
            Mockito.verifyNoMoreInteractions(keyCloakClient, sourceOperatorClient, coreClient);

        }

    }

    @Test
    void testUpdateTokenAttribute_whenAllDataExists_thenUpdateSucess() {

        try (MockedStatic<KeyCloakUtils> keyCloakUtilsMockedStatic = Mockito.mockStatic(KeyCloakUtils.class)) {

            //scenario
            keyCloakUtilsMockedStatic.when(KeyCloakUtils::getEmail).thenReturn("e@mail.com");
            keyCloakUtilsMockedStatic.when(KeyCloakUtils::getGroup).thenReturn(Optional.of("myAmazingGroup"));
            keyCloakUtilsMockedStatic.when(KeyCloakUtils::getKeycloakUserId).thenReturn("myAmazingKeycloakUserId");
            keyCloakUtilsMockedStatic.when(() -> KeyCloakUtils.addCdOperatorCurrentToken(123L)).thenCallRealMethod();
            keyCloakUtilsMockedStatic.when(() -> KeyCloakUtils.addCdPerfilCurrentToken(321L)).thenCallRealMethod();


            Optional<OperatorResponse> optionalOperatorResponse = Optional.of(
                    OperatorResponse.builder().code(123L).name("myAmazingName").email("my@amazing.email.com").build());

            Mockito.when(keyCloakClient.getAllUsersWithoutPagination(Mockito.anyBoolean(), Mockito.anyString()))
                    .thenReturn(List.of(KeyCloakUserDetailResponse.builder().build()));
            Optional<ProfileResponse> optionalProfileResponse = Optional.of(ProfileResponse.builder().id(123L).build());

            Mockito.when(coreClient.getProfile(Mockito.anyString())).thenReturn(optionalProfileResponse);

            //action
            service.updateTokenAttribute();

            //validation
            keyCloakUtilsMockedStatic.verify(KeyCloakUtils::getEmail, Mockito.times(1));
            keyCloakUtilsMockedStatic.verify(KeyCloakUtils::getGroup, Mockito.times(1));
            keyCloakUtilsMockedStatic.verify(KeyCloakUtils::getKeycloakUserId);
            Mockito.verify(keyCloakClient, times(1)).getAllUsersWithoutPagination(Mockito.anyBoolean(), Mockito.anyString());
            Mockito.verify(coreClient, times(1)).getProfile(Mockito.anyString());
            Mockito.verify(keyCloakClient, times(1))
                    .updateUserAttribute(Mockito.anyString(), Mockito.any(KeyCloakUserDetailRequest.class));
            Mockito.verifyNoMoreInteractions(keyCloakClient, sourceOperatorClient, coreClient);
        }
    }
}
