package org.bassani.examplebff.configuration.feign;

import org.bassani.examplebff.configuration.feign.properties.PurchaseSimulatorCompositionConfig;
import feign.Logger;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;

import java.util.List;

public class PurchaseSimulatorCompositionOAuth2FeignConfig {

    @Autowired
    private PurchaseSimulatorCompositionConfig purchaseSimulatorCompositionConfig;

    @Bean
    Logger.Level feignLoggerLevel() {
//        NONE, Sem registro ( PADRAO ).
//        BASIC, Registre apenas o metodo de solicitação e URL e o codigo de status de resposta e tempo de execucao.
//        HEADERS, Registre as informacoes basicas junto com os cabecalhos de solicitacao e resposta.
//        FULL, Registre os cabeçalhos, corpo e metadados para solicitacoes e respostas.
        return Logger.Level.FULL;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), resourceDetails());
    }

    private OAuth2ProtectedResourceDetails resourceDetails() {
        ClientCredentialsResourceDetails resource = new ClientCredentialsResourceDetails();
        resource.setAccessTokenUri(purchaseSimulatorCompositionConfig.getTokenUri());
        resource.setClientId(purchaseSimulatorCompositionConfig.getTokenClientId());
        resource.setClientSecret(purchaseSimulatorCompositionConfig.getTokenClientSecret());
        resource.setGrantType(purchaseSimulatorCompositionConfig.getTokenGrantType());
        resource.setScope(List.of(purchaseSimulatorCompositionConfig.getTokenScope()));

        return resource;
    }
}
