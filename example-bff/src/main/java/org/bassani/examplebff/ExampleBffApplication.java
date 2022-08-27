package org.bassani.examplebff;

import org.bassani.examplemodellib.configuration.ControlExceptionHandler;
import org.bassani.examplemodellib.configuration.LogHandler;
import org.bassani.examplemodellib.configuration.traceid.TraceidRequestInterceptor;
import org.bassani.examplemodellib.configuration.traceid.UniqueTrackingNumberFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@Import({ControlExceptionHandler.class, LogHandler.class,
        TraceidRequestInterceptor.class, UniqueTrackingNumberFilter.class})
@SpringBootApplication(scanBasePackages = {"org.bassani.examplebff"},
        exclude = {DataSourceAutoConfiguration.class, RedisAutoConfiguration.class})
@EnableFeignClients
public class ExampleBffApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExampleBffApplication.class, args);
    }
}