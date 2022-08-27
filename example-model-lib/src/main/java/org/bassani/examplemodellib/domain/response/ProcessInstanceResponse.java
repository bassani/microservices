package org.bassani.examplemodellib.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProcessInstanceResponse {

    private String id;
    private String name;
    private String processDefinitionId;
    private String processInstanceId;
    private String taskDefinitionKey;
    private String executionId;


}
