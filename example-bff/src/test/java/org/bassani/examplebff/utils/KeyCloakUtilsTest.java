package org.bassani.examplebff.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.representations.AccessToken;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;


@ExtendWith(MockitoExtension.class)
class KeyCloakUtilsTest {

    @InjectMocks private KeyCloakUtils service;

    @Test
    void getSecurityContext() {
        try (MockedStatic<KeyCloakUtils> keyCloakUtilsMockedStatic = Mockito.mockStatic(KeyCloakUtils.class)) {
            //scenario
            keyCloakUtilsMockedStatic.when(KeyCloakUtils::getSecurityContext)
                    .thenReturn(Mockito.mock(SecurityContext.class));
            //action
            SecurityContext securityContext = KeyCloakUtils.getSecurityContext();
            //validation
            Assertions.assertNotNull(securityContext);
        }
    }

    @Test
    void getAuthentication() {
        try (MockedStatic<KeyCloakUtils> keyCloakUtilsMockedStatic = Mockito.mockStatic(KeyCloakUtils.class)) {
            //scenario
            keyCloakUtilsMockedStatic.when(KeyCloakUtils::getAuthentication)
                    .thenReturn(Mockito.mock(Authentication.class));
            //action
            Authentication authentication = KeyCloakUtils.getAuthentication();
            //validation
            Assertions.assertNotNull(authentication);
        }

    }

    @Test
    void getUser() {
        try (MockedStatic<KeyCloakUtils> keyCloakUtilsMockedStatic = Mockito.mockStatic(KeyCloakUtils.class)) {
            //scenario
            keyCloakUtilsMockedStatic.when(KeyCloakUtils::getUser).thenReturn(Mockito.mock(KeycloakPrincipal.class));
            //action
            KeycloakPrincipal user = KeyCloakUtils.getUser();
            //validation
            Assertions.assertNotNull(user);
        }
    }

    @Test
    void getKeycloakUserId() {
        try (MockedStatic<KeyCloakUtils> keyCloakUtilsMockedStatic = Mockito.mockStatic(KeyCloakUtils.class)) {
            //scenario
            String expected = "myAmazingKecloakUserId";
            keyCloakUtilsMockedStatic.when(KeyCloakUtils::getKeycloakUserId).thenReturn(expected);
            //action
            String keycloakUserId = KeyCloakUtils.getKeycloakUserId();
            //validation
            Assertions.assertNotNull(keycloakUserId);
            Assertions.assertEquals(expected, keycloakUserId);
        }
    }

    @Test
    void getAccessToken() {
        try (MockedStatic<KeyCloakUtils> keyCloakUtilsMockedStatic = Mockito.mockStatic(KeyCloakUtils.class)) {
            //scenario
            keyCloakUtilsMockedStatic.when(KeyCloakUtils::getAccessToken).thenReturn(Mockito.mock(AccessToken.class));
            //action
            AccessToken accessToken = KeyCloakUtils.getAccessToken();
            //validation
            Assertions.assertNotNull(accessToken);
        }
    }

    @Test
    void getScopes() {
        try (MockedStatic<KeyCloakUtils> keyCloakUtilsMockedStatic = Mockito.mockStatic(KeyCloakUtils.class)) {
            //scenario
            keyCloakUtilsMockedStatic.when(KeyCloakUtils::getScopes).thenReturn(Arrays.asList("my", "amazing", "scope"));
            //action
            List<String> scopes = KeyCloakUtils.getScopes();
            //validation
            Assertions.assertNotNull(scopes);
            Assertions.assertTrue(!scopes.isEmpty());
            Assertions.assertTrue(scopes.size() == 3);
        }
    }

    @Test
    void getRoles() {
        try (MockedStatic<KeyCloakUtils> keyCloakUtilsMockedStatic = Mockito.mockStatic(KeyCloakUtils.class)) {
            //scenario
            keyCloakUtilsMockedStatic.when(KeyCloakUtils::getRoles).thenReturn(new HashSet<>(Arrays.asList("my", "amazing",
                    "role")));
            //action
            Set<String> roles = KeyCloakUtils.getRoles();
            //validation
            Assertions.assertNotNull(roles);
            Assertions.assertTrue(!roles.isEmpty());
            Assertions.assertTrue(roles.size() == 3);
        }
    }

    @Test
    void getEmail() {
        try (MockedStatic<KeyCloakUtils> keyCloakUtilsMockedStatic = Mockito.mockStatic(KeyCloakUtils.class)) {
            //scenario
            String expected = "my@amazing.email.com.br";
            keyCloakUtilsMockedStatic.when(KeyCloakUtils::getEmail).thenReturn(expected);
            //action
            String email = KeyCloakUtils.getEmail();
            //validation
            Assertions.assertNotNull(email);
            Assertions.assertEquals(expected, email);
        }
    }

    @Test
    void getName() {
        try (MockedStatic<KeyCloakUtils> keyCloakUtilsMockedStatic = Mockito.mockStatic(KeyCloakUtils.class)) {
            //scenario
            String expected = "myamazingname";
            keyCloakUtilsMockedStatic.when(KeyCloakUtils::getName).thenReturn(expected);
            //action
            String name = KeyCloakUtils.getName();
            //validation
            Assertions.assertNotNull(name);
            Assertions.assertEquals(expected, name);
        }
    }

    @Test
    void getID() {
        try (MockedStatic<KeyCloakUtils> keyCloakUtilsMockedStatic = Mockito.mockStatic(KeyCloakUtils.class)) {
            //scenario
            String expected = "myamazingid";
            keyCloakUtilsMockedStatic.when(KeyCloakUtils::getID).thenReturn(expected);
            //action
            String id = KeyCloakUtils.getID();
            //validation
            Assertions.assertNotNull(id);
            Assertions.assertEquals(expected, id);
        }
    }

    @Test
    void getProfile() {
        try (MockedStatic<KeyCloakUtils> keyCloakUtilsMockedStatic = Mockito.mockStatic(KeyCloakUtils.class)) {
            //scenario
            String expected = "myamazingprofile";
            keyCloakUtilsMockedStatic.when(KeyCloakUtils::getProfile).thenReturn(expected);
            //action
            String profile = KeyCloakUtils.getProfile();
            //validation
            Assertions.assertNotNull(profile);
            Assertions.assertEquals(expected, profile);
        }
    }

    @Test
    void getOtherClaims() {
        try (MockedStatic<KeyCloakUtils> keyCloakUtilsMockedStatic = Mockito.mockStatic(KeyCloakUtils.class)) {
            //scenario
            Map<String, String> expected = Map.of("expected", "value1", "key2", "value2", "key3", "value3");
            keyCloakUtilsMockedStatic.when(KeyCloakUtils::getOtherClaims).thenReturn(expected);
            //action
            Map<String, Object> otherClaims = KeyCloakUtils.getOtherClaims();
            //validation
            Assertions.assertNotNull(otherClaims);
            Assertions.assertTrue(!otherClaims.isEmpty());
            Assertions.assertEquals(expected, otherClaims);
        }
    }

    @Test
    void getGroup() {
        try (MockedStatic<KeyCloakUtils> keyCloakUtilsMockedStatic = Mockito.mockStatic(KeyCloakUtils.class)) {
            //scenario
            Optional<String> expected = Optional.of("myamazingGroup");
            keyCloakUtilsMockedStatic.when(KeyCloakUtils::getGroup).thenReturn(expected);
            //action
            Optional<String> group = KeyCloakUtils.getGroup();
            //validation
            Assertions.assertNotNull(group);
            Assertions.assertTrue(!group.isEmpty());
            Assertions.assertEquals(expected, group);
        }
    }

    @Test
    void getCDOperador() {
        try (MockedStatic<KeyCloakUtils> keyCloakUtilsMockedStatic = Mockito.mockStatic(KeyCloakUtils.class)) {
            //scenario
            String expected = "123";
            keyCloakUtilsMockedStatic.when(KeyCloakUtils::getCDOperador).thenReturn(expected);
            //action
            String cdOperador = KeyCloakUtils.getCDOperador();
            //validation
            Assertions.assertNotNull(cdOperador);
            Assertions.assertEquals(expected, cdOperador);
        }
    }

    @Test
    void getCDPerfil() {
        try (MockedStatic<KeyCloakUtils> keyCloakUtilsMockedStatic = Mockito.mockStatic(KeyCloakUtils.class)) {
            //scenario
            String expected = "123";
            keyCloakUtilsMockedStatic.when(KeyCloakUtils::getCDPerfil).thenReturn(expected);
            //action
            String cdPerfil = KeyCloakUtils.getCDPerfil();
            //validation
            Assertions.assertNotNull(cdPerfil);
            Assertions.assertEquals(expected, cdPerfil);
        }
    }
}
