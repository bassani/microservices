package org.bassani.examplemodellib.configuration.traceid;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newrelic.api.agent.NewRelic;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;
import java.util.function.Predicate;

@Component
@Slf4j
public class UniqueTrackingNumberFilter implements Filter {

    private TraceidRequestInterceptor traceIdInterceptor;

    public static final String X_CONSUMER_USERNAME = "x-consumer-username";
    public static final String TRACE_ID_KEY = "traceId";
    public static final String EMPTY_TRACE_ID = "";

    public UniqueTrackingNumberFilter(TraceidRequestInterceptor traceIdInterceptor) {
        this.traceIdInterceptor = traceIdInterceptor;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var distributedTracePayload = NewRelic.getAgent()
                .getTransaction()
                .createDistributedTracePayload()
                .text();

        Optional<String> json = Optional
                .ofNullable(distributedTracePayload)
                .filter(Predicate.not(String::isEmpty));

        String traceId = EMPTY_TRACE_ID;
        if (json.isPresent()) {
            JsonNode parent = new ObjectMapper().readTree(distributedTracePayload);
            traceId = parent.get("d")
                    .get("tx")
                    .asText();
        }

        MDC.put(TRACE_ID_KEY, traceId);
        traceIdInterceptor.setTraceId(traceId);
        try {

            var clientId = ((HttpServletRequest) request).getHeader(X_CONSUMER_USERNAME);

            if (clientId != null) {
                log.info("clientId={}", clientId);
            }

            filterChain.doFilter(request, response);
        } finally {
            MDC.remove(TRACE_ID_KEY);
        }
    }

}
