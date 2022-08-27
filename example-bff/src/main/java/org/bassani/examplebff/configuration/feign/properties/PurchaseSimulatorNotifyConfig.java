package org.bassani.examplebff.configuration.feign.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "ms-example-notify")
public class PurchaseSimulatorNotifyConfig {
    private String tokenUri;
    private String tokenGrantType;
    private String tokenClientId;
    private String tokenClientSecret;
    private String tokenScope;
}
