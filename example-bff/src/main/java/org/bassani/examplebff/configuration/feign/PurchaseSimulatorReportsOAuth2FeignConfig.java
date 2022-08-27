package org.bassani.examplebff.configuration.feign;

import org.bassani.examplebff.configuration.feign.properties.PurchaseSimulatorReportsConfig;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;

import java.util.List;

public class PurchaseSimulatorReportsOAuth2FeignConfig {

	@Autowired
	private PurchaseSimulatorReportsConfig purchaseSimulatorReportsConfig;

	@Bean
	public RequestInterceptor requestInterceptor() {
		return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), resourceDetails());
	}

	private OAuth2ProtectedResourceDetails resourceDetails() {
		var resource = new ClientCredentialsResourceDetails();
		resource.setAccessTokenUri(purchaseSimulatorReportsConfig.getTokenUri());
		resource.setClientId(purchaseSimulatorReportsConfig.getTokenClientId());
		resource.setClientSecret(purchaseSimulatorReportsConfig.getTokenClientSecret());
		resource.setGrantType(purchaseSimulatorReportsConfig.getTokenGrantType());
		resource.setScope(List.of(purchaseSimulatorReportsConfig.getTokenScope()));

		return resource;
	}
}
