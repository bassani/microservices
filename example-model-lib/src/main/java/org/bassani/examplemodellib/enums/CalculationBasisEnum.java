package org.bassani.examplemodellib.enums;

import lombok.Getter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Getter
public enum CalculationBasisEnum implements Serializable {

    SALE_DAYS(1L, "Dias de Vendas"),
    WEEKLY_FORECAST(2L, "Forecast Semanal"),
    MONTHLY_FORECAST(3L, "Forecast Mensal");

    private Long id;
    private String name;

    private static class Holder {
        static Map<Long, CalculationBasisEnum> MAP = new HashMap<>();
    }

    CalculationBasisEnum(Long id, String name) {
        this.id = id;
        this.name = name;
        Holder.MAP.put(id, this);
    }

    public static CalculationBasisEnum find(Long id) {
        CalculationBasisEnum result = Holder.MAP.get(id);
        if (result == null) {
            throw new IllegalStateException(String.format("Unsupported CalculationBasisEnum %d.", id));
        }
        return result;
    }

    public static boolean isSaleDays(Long id){return SALE_DAYS.getId().equals(id); }
    public static boolean isWeeklyForecast(Long id){return WEEKLY_FORECAST.getId().equals(id); }
    public static boolean isMonthlyForecast(Long id){return MONTHLY_FORECAST.getId().equals(id); }

}
