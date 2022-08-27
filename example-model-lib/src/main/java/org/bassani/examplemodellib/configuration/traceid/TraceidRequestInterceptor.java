package org.bassani.examplemodellib.configuration.traceid;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

@Component
public class TraceidRequestInterceptor implements RequestInterceptor {

    private String traceId;

    @Override
    public void apply(RequestTemplate template) {
        template.header("traceId", traceId);
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }
}
