package org.bassani.examplebff.service;

import org.bassani.examplebff.client.SourceOperatorClient;
import org.bassani.examplemodellib.domain.request.UserRequest;
import org.bassani.examplemodellib.domain.response.PositionResponse;
import org.bassani.examplemodellib.domain.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final SourceOperatorClient sourceOperatorClient;

    public Page<UserResponse> getAllUsers(Pageable page, String search) {
        return sourceOperatorClient.getAll(search, page.getPageNumber(), page.getPageSize());
    }

    public List<PositionResponse> getAllPositions() {
        return sourceOperatorClient.getAllPositions();
    }

    public PositionResponse getUserPosition(String userId) {
        return sourceOperatorClient.getUserPosition(userId);
    }

    public void saveUser(UserRequest request) {
        sourceOperatorClient.saveUser(request);
    }

    public void updateUser(UserRequest request) {
        sourceOperatorClient.editUser(request);
    }

    public List<UserResponse> searchByNameOrRegistrationNumber(String search) {
        return  sourceOperatorClient.searchByNameOrRegistrationNumber(search);
    }

    public List<UserResponse> findAllByKeyCloakId(List<String> keyCloakIds) {
        return sourceOperatorClient.findAllByIds(keyCloakIds);
    }
}
