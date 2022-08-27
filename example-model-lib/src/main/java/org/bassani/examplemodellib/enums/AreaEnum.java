package org.bassani.examplemodellib.enums;

import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.AreaEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@RequiredArgsConstructor
@Getter
public enum AreaEnum implements Serializable {

    ABASTECIMENTO(1L, "Abastecimento"), COMERCIAL(2L, "Comercial"),
    ;

    private final Long id;
    private final String name;

    public static AreaEnum find(Long id) {
        for (AreaEnum value : AreaEnum.values()) {
            if (value.id.equals(id))
                return value;
        }
        throw new IllegalArgumentException(String.format("Unsupported AreaEnum %d.", id));
    }

    public AreaEntity getEntity() {
        return AreaEntity.builder()
                .id(this.getId())
                .description(this.getName())
                .build();
    }

}
