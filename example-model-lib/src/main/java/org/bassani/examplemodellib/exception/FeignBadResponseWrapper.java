package org.bassani.examplemodellib.exception;

import com.netflix.hystrix.exception.HystrixBadRequestException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;

@Getter
@Setter
@Slf4j
public class FeignBadResponseWrapper extends HystrixBadRequestException {

    private static final long serialVersionUID = -734069695997381331L;

    private final int status;
    private final HttpHeaders headers;
    private final String body;

    public FeignBadResponseWrapper(int status, HttpHeaders headers, String body) {
        super("Bad request");
        this.status = status;
        this.headers = headers;
        this.body = body;
        log.error("status={} httpHeaders={} body={}", status, headers, body);
    }
}
