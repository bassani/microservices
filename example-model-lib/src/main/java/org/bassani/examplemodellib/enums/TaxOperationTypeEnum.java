package org.bassani.examplemodellib.enums;

import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.AreaEntity;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@RequiredArgsConstructor
@Getter
public enum TaxOperationTypeEnum implements Serializable {

    RESALE_PURCHASE(3L, "COMPRAS REVENDA", "3 - COMPRAS REVENDA"),
    RESALE_PURCHASE_BONIFICATION(192L, "COMPRAS REVENDA - BONIFIC - REC CML", "192 - COMPRAS REVENDA - BONIFIC - REC CML"),
    ;

    private final Long id;
    private final String name;
    private final String concat;

    public static TaxOperationTypeEnum find(Long id) {
        for (TaxOperationTypeEnum value : TaxOperationTypeEnum.values()) {
            if (value.id.equals(id))
                return value;
        }
        throw new IllegalArgumentException(String.format("Unsupported taxOperationTypeEnum %d.", id));
    }

    public AreaEntity getEntity() {
        return AreaEntity.builder()
                .id(this.getId())
                .description(this.getName())
                .build();
    }

    @JsonValue
    public Long getId() {
        return id;
    }

    @JsonCreator
    public static TaxOperationTypeEnum fromId(long id) {
        for (TaxOperationTypeEnum sim : TaxOperationTypeEnum.values())
            if (sim.id.equals(id))
                return sim;
        return null;
    }

}
