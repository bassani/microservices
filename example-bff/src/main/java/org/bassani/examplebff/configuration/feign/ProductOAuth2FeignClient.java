package org.bassani.examplebff.configuration.feign;

import org.bassani.examplebff.configuration.feign.properties.ProductConfig;
import feign.RequestInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;

import java.util.List;

@AllArgsConstructor
public class ProductOAuth2FeignClient {

    private ProductConfig productConfig;

    @Bean
    public RequestInterceptor productOAuth2RequestInterceptor() {
        return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), resource());
    }

    private OAuth2ProtectedResourceDetails resource() {
        var resource = new ClientCredentialsResourceDetails();
        resource.setAccessTokenUri(productConfig.getTokenUri());
        resource.setClientId(productConfig.getTokenClientId());
        resource.setClientSecret(productConfig.getTokenClientSecret());
        resource.setGrantType(productConfig.getTokenGrantType());
        resource.setScope(List.of(productConfig.getTokenScope()));

        return resource;
    }
}