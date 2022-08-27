package org.bassani.examplebff.configuration.feign;

import org.bassani.examplebff.configuration.feign.properties.PurchaseWeeklyForecastConfig;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;

import java.util.List;

public class PurchaseSimulatorWeeklyForecastOAuthFeignConfig {

    @Autowired
    private PurchaseWeeklyForecastConfig purchaseWeeklyForecastConfig;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), resourceDetails());
    }

    private OAuth2ProtectedResourceDetails resourceDetails() {
        var resource = new ClientCredentialsResourceDetails();
        resource.setAccessTokenUri(purchaseWeeklyForecastConfig.getTokenUri());
        resource.setClientId(purchaseWeeklyForecastConfig.getTokenClientId());
        resource.setClientSecret(purchaseWeeklyForecastConfig.getTokenClientSecret());
        resource.setGrantType(purchaseWeeklyForecastConfig.getTokenGrantType());
        resource.setScope(List.of(purchaseWeeklyForecastConfig.getTokenScope()));

        return resource;
    }

}
