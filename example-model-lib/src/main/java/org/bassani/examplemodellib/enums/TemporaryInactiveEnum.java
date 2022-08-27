package org.bassani.examplemodellib.enums;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.NoSuchElementException;

@AllArgsConstructor
public enum TemporaryInactiveEnum implements Serializable {

    NAO_SE_APLICA (0L, "Não se aplica"),
    CONSIDERAR_INATIVOS_TEMPORARIOS(1L, "Considerar inativos temporários"),
    SOMENTE_INATIVOS_TEMPORARIOS(2L, "Somente inativos temporários");

    private final Long code;
    @Getter
    private final String name;

    @JsonValue
    public Long getCode() {
        return code;
    }

    @JsonCreator
    public static TemporaryInactiveEnum fromCode(long code) {
        for (TemporaryInactiveEnum tIE : TemporaryInactiveEnum.values())
            if (tIE.code.equals(code))
                return tIE;
        throw new NoSuchElementException("Code " + code + " para temporaryInactiveCode, inválido.");
    }
}
