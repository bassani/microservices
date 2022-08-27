package org.bassani.examplebff.configuration;

import com.fasterxml.jackson.databind.Module;
import org.springframework.cloud.openfeign.support.PageJacksonModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {
    @Bean
    public Module jacksonModule(){
        return new PageJacksonModule();
    }
}
