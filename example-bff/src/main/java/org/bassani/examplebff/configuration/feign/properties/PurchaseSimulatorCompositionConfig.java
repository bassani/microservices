package org.bassani.examplebff.configuration.feign.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "ms-purchase-composition")
public class PurchaseSimulatorCompositionConfig {

    private String tokenUri;
    private String tokenGrantType;
    private String tokenClientId;
    private String tokenClientSecret;
    private String tokenScope;
}
