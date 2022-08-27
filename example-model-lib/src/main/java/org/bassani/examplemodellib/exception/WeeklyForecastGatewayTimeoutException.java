package org.bassani.examplemodellib.exception;

import br.com.example.purchasesimulatormodellib.enums.MessageEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class WeeklyForecastGatewayTimeoutException extends BusinessException {

    public static final String MESSAGE = MessageEnum.WEEKLY_FORECAST_GATEWAY_TIMEOUT.getMessage();
    public static final String DESCRIPTION = MessageEnum.WEEKLY_FORECAST_GATEWAY_TIMEOUT.getDescription();

    public WeeklyForecastGatewayTimeoutException() {
        super.setHttpStatusCode(HttpStatus.GATEWAY_TIMEOUT);
        super.setDescription(DESCRIPTION);
        super.setMessage(MESSAGE);
        super.setCode(String.valueOf(HttpStatus.GATEWAY_TIMEOUT.value()));
        
    }
}
