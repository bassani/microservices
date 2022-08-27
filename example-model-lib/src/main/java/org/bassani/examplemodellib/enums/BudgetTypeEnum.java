package org.bassani.examplemodellib.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BudgetTypeEnum {

    MONEY(1), PERCENTAGE(2);

    private final int id;

    @JsonCreator
    public static BudgetTypeEnum fromId(int id) {
        for (BudgetTypeEnum action : BudgetTypeEnum.values())
            if (action.id == id)
                return action;

        throw new IllegalArgumentException("Tipo de verba " + id + " não encontrado.");
    }

    @JsonValue
    public Integer getId() {
        return id;
    }
}
