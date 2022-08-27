package org.bassani.examplemodellib.exception;

import br.com.example.purchasesimulatormodellib.enums.MessageEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class WeeklyForecastServiceUnavailableException extends BusinessException {

    public static final String MESSAGE = MessageEnum.WEEKLY_FORECAST_SERVICE_UNAVALIABLE.getMessage();
    public static final String DESCRIPTION = MessageEnum.WEEKLY_FORECAST_SERVICE_UNAVALIABLE.getDescription();

    public WeeklyForecastServiceUnavailableException() {
        super.setHttpStatusCode(HttpStatus.SERVICE_UNAVAILABLE);
        super.setDescription(DESCRIPTION);
        super.setMessage(MESSAGE);
        super.setCode(String.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value()));
        
    }
}
