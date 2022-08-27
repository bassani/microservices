package org.bassani.examplemodellib.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.NoSuchElementException;

@AllArgsConstructor
public enum SalesByPeriodEnum {

    SEVEN_DAYS(7, "7 Dias"), FIFTEEN_DAYS(15, "15 Dias"), THIRTY_DAYS(30, "30 Dias"), SIXTY_DAYS(60, "60 Dias");

    private final int days;
    @Getter
    private final String name;

    @JsonCreator
    public static SalesByPeriodEnum fromCode(int days) {
        for (SalesByPeriodEnum period : SalesByPeriodEnum.values())
            if (period.days == days) return period;
        throw new NoSuchElementException("Enum para " + days + " dias não encontrado.");
    }

    @JsonValue
    public Integer getDays() {
        return days;
    }
}
