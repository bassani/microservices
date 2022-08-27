package org.bassani.examplemodellib.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

import java.util.NoSuchElementException;

import static br.com.example.purchasesimulatormodellib.util.MessageBundle.getMessage;

@AllArgsConstructor
public enum DiscountTypeEnum {

    ADD(1), REPLACE(2);

    private final int id;


    @JsonCreator
    public static DiscountTypeEnum fromId(int id) {
        for (DiscountTypeEnum action : DiscountTypeEnum.values())
            if (action.id == id)
                return action;

        throw new NoSuchElementException(getMessage("nonexistent_discount_id.message", id));
    }

    @JsonValue
    public Integer getId() {
        return id;
    }
}
