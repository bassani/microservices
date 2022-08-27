package org.bassani.examplebff.service;

import org.bassani.examplebff.client.BPMServerClient;
import org.bassani.examplebff.utils.KeyCloakUtils;
import org.bassani.examplemodellib.domain.request.SimulationApprovalCompleteRequest;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.rest.dto.runtime.ProcessInstanceWithVariablesDto;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class ApprovalProcessService {
    private final BPMServerClient bpmServerClient;

    public ProcessInstanceWithVariablesDto startApprovalProcessInstance(Long simulationId) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("operatorId", KeyCloakUtils.getCDOperador());
        variables.put("keycloakUserId", KeyCloakUtils.getKeycloakUserId());
        variables.put("areaId", ofNullable(KeyCloakUtils.getCDArea()).orElse("1"));
        variables.put("profileId",  KeyCloakUtils.getCDPerfil());
        variables.put("withVariablesInReturn", true);

        return bpmServerClient.startApprovalProcessInstance(simulationId, variables);
    }

    public boolean completeApprovalTask(Long simulationId, SimulationApprovalCompleteRequest request) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("operatorId", KeyCloakUtils.getCDOperador());
        variables.put("keycloakUserId", KeyCloakUtils.getKeycloakUserId());
        variables.put("areaId", ofNullable(KeyCloakUtils.getCDArea()).orElse("1"));
        variables.put("profileId", KeyCloakUtils.getCDPerfil());
        variables.put("approved", request.getApproved());
        variables.put("reason", request.getReason());
        variables.put("withVariablesInReturn", true);

        return Boolean.TRUE.equals(bpmServerClient.completeApprovalTask(simulationId, variables).getBody());
    }

}
