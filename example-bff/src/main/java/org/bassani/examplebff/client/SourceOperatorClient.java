package org.bassani.examplebff.client;

import org.bassani.examplebff.configuration.feign.PurchaseSimulatorSourceOperatorOAuthFeignConfig;
import org.bassani.examplemodellib.domain.request.OperatorRequest;
import org.bassani.examplemodellib.domain.request.UserRequest;
import org.bassani.examplemodellib.domain.response.OperatorResponse;
import org.bassani.examplemodellib.domain.response.PositionResponse;
import org.bassani.examplemodellib.domain.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "ms-source-operator", url = "${ms-example-source-operator.url}", configuration =
        PurchaseSimulatorSourceOperatorOAuthFeignConfig.class)
public interface SourceOperatorClient {

	@GetMapping("/operators/{email}")
    Optional<OperatorResponse> findOperatorByEmail(@PathVariable String email);

    @GetMapping("/operators")
    Page<OperatorResponse> getAll(@SpringQueryMap OperatorRequest operatorRequest, Pageable pageable);

    @GetMapping("/users")
    Page<UserResponse> getAll(@RequestParam(required = false) String search, @RequestParam Integer page, @RequestParam Integer size);

    @GetMapping("/users/positions")
    List<PositionResponse> getAllPositions();

    @GetMapping("/users/{id}/position")
    PositionResponse getUserPosition(@PathVariable("id") String id);

    @PostMapping("/users")
    void saveUser(@RequestBody UserRequest userRequest);

    @PutMapping("/users")
    void editUser(@RequestBody UserRequest userRequest);

    @PostMapping("/users/ids")
    List<UserResponse> findAllByIds(@RequestBody List<String> keyCloakIds);

    @GetMapping("/users/search")
    List<UserResponse> searchByNameOrRegistrationNumber(@RequestParam String nameOrRegNumber);



}
