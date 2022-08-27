package org.bassani.examplemodellib.enums;


import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum StockTypeEnum {

    CONTABIL(0L, "Contabil"),
    FISCAL(1L, "Fiscal"),
    TRAVA(4L, "Trava de Estoque"),
    ;

    private Long code;
    private String description;

    private static class Holder {
        static Map<Long, StockTypeEnum> MAP = new HashMap<>();
    }

    StockTypeEnum(Long code, String description) {
        this.code = code;
        this.description = description;
        Holder.MAP.put(code, this);
    }

    public static StockTypeEnum find(Long code) {
        StockTypeEnum result = Holder.MAP.get(code);
        if (result == null) {
            throw new IllegalStateException(String.format("Unsupported StockTypeEnum %d.", code));
        }
        return result;
    }
}
