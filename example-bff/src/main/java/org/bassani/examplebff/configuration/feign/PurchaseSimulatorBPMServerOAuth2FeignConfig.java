package org.bassani.examplebff.configuration.feign;

import org.bassani.examplebff.configuration.feign.properties.PurchaseSimulatorBPMServerConfig;
import feign.RequestInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;

import java.util.List;

@AllArgsConstructor
public class PurchaseSimulatorBPMServerOAuth2FeignConfig {

    private PurchaseSimulatorBPMServerConfig purchaseSimulatorBPMServerConfig;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), resourceDetails());
    }

    private OAuth2ProtectedResourceDetails resourceDetails() {
        var resource = new ClientCredentialsResourceDetails();
        resource.setAccessTokenUri(purchaseSimulatorBPMServerConfig.getTokenUri());
        resource.setClientId(purchaseSimulatorBPMServerConfig.getTokenClientId());
        resource.setClientSecret(purchaseSimulatorBPMServerConfig.getTokenClientSecret());
        resource.setGrantType(purchaseSimulatorBPMServerConfig.getTokenGrantType());
        resource.setScope(List.of(purchaseSimulatorBPMServerConfig.getTokenScope()));

        return resource;
    }
}
