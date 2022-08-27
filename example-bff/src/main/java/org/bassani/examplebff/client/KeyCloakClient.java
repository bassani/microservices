package org.bassani.examplebff.client;

import org.bassani.examplebff.configuration.feign.PurchaseSimulatorKeycloakOAuth2FeignConfig;
import org.bassani.examplemodellib.domain.request.KeyCloakUserDetailRequest;
import org.bassani.examplemodellib.domain.response.KeyCloakGroupDetailResponse;
import org.bassani.examplemodellib.domain.response.KeyCloakUserDetailResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "keycloak", url = "${ms-purchase-keycloak.host}",
        path = "/auth/admin/realms/${keycloak.realm}",
        configuration = PurchaseSimulatorKeycloakOAuth2FeignConfig.class
)
public interface KeyCloakClient {

    @PutMapping(value = "/users/{id}")
    void updateUserAttribute(@PathVariable("id") String id, @RequestBody
            KeyCloakUserDetailRequest keyCloakUserDetailRequest);

    @GetMapping(value = "/users")
    List<KeyCloakUserDetailResponse> getAllUsers(
            @RequestParam(defaultValue = "false") boolean briefRepresentation,
            @RequestParam(required = false) String search,
            @RequestParam Integer first, @RequestParam Integer max);

    @GetMapping(value = "/users")
    List<KeyCloakUserDetailResponse> getAllUsersWithoutPagination(
            @RequestParam(defaultValue = "false") boolean briefRepresentation,
            @RequestParam(required = false) String search);

    @GetMapping(value = "/users/count")
    Long countUsers(@RequestParam(required = false) String search);

    @GetMapping(value = "/groups")
    List<KeyCloakGroupDetailResponse> getAllGroups();

    @GetMapping(value = "/users/{id}/groups")
    List<KeyCloakGroupDetailResponse> getUserGroups(@PathVariable("id") String userId);

}