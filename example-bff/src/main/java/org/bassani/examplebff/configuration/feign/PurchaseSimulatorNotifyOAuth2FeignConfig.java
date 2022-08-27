package org.bassani.examplebff.configuration.feign;

import org.bassani.examplebff.configuration.feign.properties.PurchaseSimulatorNotifyConfig;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;

import java.util.List;

public class PurchaseSimulatorNotifyOAuth2FeignConfig {

    @Autowired
    private PurchaseSimulatorNotifyConfig purchaseSimulatorNotifyConfig;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), resourceDetails());
    }

    private OAuth2ProtectedResourceDetails resourceDetails() {
        var resource = new ClientCredentialsResourceDetails();
        resource.setAccessTokenUri(purchaseSimulatorNotifyConfig.getTokenUri());
        resource.setClientId(purchaseSimulatorNotifyConfig.getTokenClientId());
        resource.setClientSecret(purchaseSimulatorNotifyConfig.getTokenClientSecret());
        resource.setGrantType(purchaseSimulatorNotifyConfig.getTokenGrantType());
        resource.setScope(List.of(purchaseSimulatorNotifyConfig.getTokenScope()));

        return resource;
    }
}
