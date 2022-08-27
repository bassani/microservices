package org.bassani.examplebff.configuration.feign;

import org.bassani.examplebff.configuration.feign.properties.PurchaseSimulatorSourceDictionaryConfig;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;

import java.util.List;

public class PurchaseSimulatorSourceDictionaryOAuthFeignConfig {

    @Autowired
    private PurchaseSimulatorSourceDictionaryConfig purchaseSimulatorSourceDictionaryConfig;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), resourceDetails());
    }

    private OAuth2ProtectedResourceDetails resourceDetails() {
        var resource = new ClientCredentialsResourceDetails();
        resource.setAccessTokenUri(purchaseSimulatorSourceDictionaryConfig.getTokenUri());
        resource.setClientId(purchaseSimulatorSourceDictionaryConfig.getTokenClientId());
        resource.setClientSecret(purchaseSimulatorSourceDictionaryConfig.getTokenClientSecret());
        resource.setGrantType(purchaseSimulatorSourceDictionaryConfig.getTokenGrantType());
        resource.setScope(List.of(purchaseSimulatorSourceDictionaryConfig.getTokenScope()));

        return resource;
    }

}
