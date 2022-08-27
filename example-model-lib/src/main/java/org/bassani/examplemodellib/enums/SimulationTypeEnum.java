package org.bassani.examplemodellib.enums;

import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.SimulationTypeEntity;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@AllArgsConstructor
@ToString
public enum SimulationTypeEnum implements Serializable {

    ANTECIPACAO(1L, "Antecipação"),
    VALOR(2L, "Valor"),
    QUANTIDADE_SKU(3L, "Quantidade SKU"),
    COBERTURA_ESTOQUE_CD(4L, "Cobertura de Estoque CD"),
    PROGRAMACAO(5L, "Programação"),
    DISTRIBUICAO(6L, "Distribuição");

    private final Long id;
    @Getter
    private final String name;

    public SimulationTypeEntity getEntity(){
        return SimulationTypeEntity.builder().id(this.getId()).name(this.getName()).build();
    }

    @JsonValue
    public Long getId() {
        return id;
    }

    @JsonCreator
    public static SimulationTypeEnum fromId(long id) {
        for (SimulationTypeEnum sim : SimulationTypeEnum.values())
            if (sim.id.equals(id))
                return sim;
        return null;
    }
}
