package org.bassani.examplemodellib.enums;

import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.SimulationApprovalStatusFlowEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
@Getter
public enum SimulationApprovalStatusFlowEnum implements Serializable {

    ENVIADO_PARA_APROVACAO_POR(1L, "Enviado para aprovação por"),
    APROVADO_POR(2L, "Aprovado por"),
    REPROVADOR_POR(3L, "Reprovador por")
    ;

    private final Long id;
    private final String name;

    public static SimulationApprovalStatusFlowEnum find(Long id) {
        for (SimulationApprovalStatusFlowEnum value : SimulationApprovalStatusFlowEnum.values()) {
            if (value.id.equals(id))
                return value;
        }
        throw new IllegalArgumentException(String.format("Unsupported SimulationApprovalStatusFlowEnum %d.", id));
    }

    public SimulationApprovalStatusFlowEntity getEntity() {
        return SimulationApprovalStatusFlowEntity.builder()
                .id(this.getId())
                .description(this.getName())
                .build();
    }

    public boolean isReproved(){return (isNull(this.getId())? false: REPROVADOR_POR.getId().equals(this.getId())); }

}
