package org.bassani.examplemodellib.enums;

import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.NewPaymentTermTypeEntity;
import lombok.Getter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Getter
public enum NewPaymentTermTypeEnum implements Serializable {

    TIPO_NOVO_PRAZO_GERAL(1L, "Tipo Novo Prazo Geral"),
    TIPO_NOVO_PRAZO_POR_CD(2L, "Tipo Novo Prazo por Centro de Distribuição");

    private final Long id;
    private final String name;

    private static class Holder {
        static Map<Long, NewPaymentTermTypeEnum> MAP = new HashMap<>();
    }

    NewPaymentTermTypeEnum(Long id, String name) {
        this.id = id;
        this.name = name;
        Holder.MAP.put(id, this);
    }

    public static NewPaymentTermTypeEnum find(Long id) {
        NewPaymentTermTypeEnum result = Holder.MAP.get(id);
        if (result == null) {
            throw new IllegalStateException(String.format("Unsupported NewPaymentTermTypeEnum %d.", id));
        }
        return result;
    }

    public NewPaymentTermTypeEntity getEntity() {
        return NewPaymentTermTypeEntity.builder()
                .id(this.getId())
                .name(this.getName())
                .build();
    }
}
