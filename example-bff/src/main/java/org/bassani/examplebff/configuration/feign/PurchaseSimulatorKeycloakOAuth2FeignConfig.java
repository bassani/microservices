package org.bassani.examplebff.configuration.feign;

import org.bassani.examplebff.configuration.feign.properties.PurchaseSimulatorKeycloakConfig;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;

import java.util.List;

public class PurchaseSimulatorKeycloakOAuth2FeignConfig {

    @Autowired private PurchaseSimulatorKeycloakConfig purchaseSimulatorKeycloakConfig;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), resourceDetails());
    }

    private OAuth2ProtectedResourceDetails resourceDetails() {
        var resource = new ResourceOwnerPasswordResourceDetails();
        resource.setAccessTokenUri(purchaseSimulatorKeycloakConfig.getTokenUri());
        resource.setClientId(purchaseSimulatorKeycloakConfig.getTokenClientId());
        resource.setGrantType(purchaseSimulatorKeycloakConfig.getTokenGrantType());
        resource.setScope(List.of(purchaseSimulatorKeycloakConfig.getTokenScope()));
        resource.setUsername(purchaseSimulatorKeycloakConfig.getTokenUsername());
        resource.setPassword(purchaseSimulatorKeycloakConfig.getTokenPassword());
        return resource;
    }
}
