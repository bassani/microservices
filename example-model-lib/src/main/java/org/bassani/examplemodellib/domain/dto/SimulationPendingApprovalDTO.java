package org.bassani.examplemodellib.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class SimulationPendingApprovalDTO implements Serializable {

    private static final long serialVersionUID = -5905995723172182179L;

    private Long id;

    private String name;

}
