package org.bassani.examplebff.it;

import org.bassani.examplebff.mock.KeyCloakMock;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = IntegrationTestBase.IntegrationTestConfig.class)
@DirtiesContext
@AutoConfigureWireMock(port = 0)
@ActiveProfiles("test")
abstract class IntegrationTestBase {

    @Autowired @Qualifier("rest") TestRestTemplate rest;

    @Autowired @Qualifier("msBffUri") String thisMsUri;

    @Autowired @Qualifier("msCompositionPath") String msCompositionPath;

    @Autowired @Qualifier("msCorePath") String msCorePath;

    @Autowired @Qualifier("msReportsPath") String msReportsPath;

    @Autowired @Qualifier("msOperatorPath") String msOperatorPath;

    @Autowired @Qualifier("msSupplierPath") String msSupplierPath;

    @Autowired @Qualifier("msNotifyPath") String msNotifyPath;

    @Autowired @Qualifier("msBiPath") String msBiPath;

    @Autowired @Qualifier("msKeyCloakPath") String msKeyCloakPath;

    @BeforeEach
    public void interceptorRequestSetup(){
        stubFor(get("/auth/admin/realms/jornada-de-compras/users?briefRepresentation=false&search=purchasedevrd@rd.com.br")
                .willReturn(okJson(KeyCloakMock.mockedKeyCloakUserAsJson())));
        stubFor(put("/auth/admin/realms/jornada-de-compras/users/9669b7c7-4fea-480c-84db-11537e0110b2").willReturn(ok()));
        stubFor(get("/v1/api/purchases/core/profiles/profileName//Administrador").willReturn(okJson("{ \"id\": 0, " +
                "\"name\": \"Administrador\" }")));
    }

    @Slf4j
    @Lazy
    @TestConfiguration
    public static class IntegrationTestConfig {

        @Value("${token.uri}") String tokenUri;

        @Value("${token.client-id}") String clientId;

        @Value("${token.grant-type}") String grantType;

        @Value("${token.scope}") String scope;

        @Value("${token.username}") String username;

        @Value("${token.password}") String password;

        @Bean
        public String msBffUri(@LocalServerPort int port,
                               @Value("${kong.path}") String path,
                               @Value("${endpoint}") String endpoint) {
            var uri = UriComponentsBuilder.fromUriString("http://localhost")
                    .path(path)
                    .port(port)
                    .path(endpoint)
                    .toUriString();
            log.info("MS BFF URL for testing={}", uri);
            return uri;
        }

        @Bean
        public String msCompositionPath(@Value("${ms-purchase-composition.path}") String msCompositionPath,
                                        @Value("${endpoint}") String endpoint) {
            String path = UriComponentsBuilder.newInstance().path(msCompositionPath).path(endpoint).toUriString();
            log.info("MS Composition path for testing={}", path);
            return path;
        }

        @Bean
        public String msCorePath(@Value("${ms-example-core.path}") String msCorePath,
                                        @Value("${endpoint}") String endpoint) {
            String path = UriComponentsBuilder.newInstance().path(msCorePath).path(endpoint).toUriString();
            log.info("MS Core path for testing={}", path);
            return path;
        }

        @Bean
        public String msReportsPath(@Value("${ms-example-reports.path}") String msReportsPath,
                                 @Value("${endpoint}") String endpoint) {
            String path = UriComponentsBuilder.newInstance().path(msReportsPath).path(endpoint).toUriString();
            log.info("MS Reports path for testing={}", path);
            return path;
        }

        @Bean
        public String msOperatorPath(@Value("${ms-example-source-operator.path}") String msOperatorPath,
                                 @Value("${endpoint}") String endpoint) {
            String path = UriComponentsBuilder.newInstance().path(msOperatorPath).path(endpoint).toUriString();
            log.info("MS Source Operator path for testing={}", path);
            return path;
        }

        @Bean
        public String msSupplierPath(@Value("${ms-example-source-supplier.path}") String msSupplierPath,
                                     @Value("${endpoint}") String endpoint) {
            String path = UriComponentsBuilder.newInstance().path(msSupplierPath).path(endpoint).toUriString();
            log.info("MS Source Supplier path for testing={}", path);
            return path;
        }

        @Bean
        public String msNotifyPath(@Value("${ms-example-notify.path}") String msNotifyPath,
                                     @Value("${endpoint}") String endpoint) {
            String path = UriComponentsBuilder.newInstance().path(msNotifyPath).path(endpoint).toUriString();
            log.info("MS Notify path for testing={}", path);
            return path;
        }

        @Bean
        public String msBiPath(@Value("${ms-example-bi.path}") String msBiPath,
                                   @Value("${endpoint}") String endpoint) {
            String path = UriComponentsBuilder.newInstance().path(msBiPath).path(endpoint).toUriString();
            log.info("MS Bi path for testing={}", path);
            return path;
        }

        @Bean
        public String msKeyCloakPath(@Value("${ms-purchase-keycloak.path}") String msKeyCloakUrl,
                                    @Value("${endpoint}") String endpoint) {
            String path = UriComponentsBuilder.newInstance().path(msKeyCloakUrl).path(endpoint).toUriString();
            log.info("MS KeyCloak path for testing={}", path);
            return path;
        }

        private String obtainToken() {
            LinkedMultiValueMap<String, String> credentials = new LinkedMultiValueMap<>();
            credentials.add("client_id", clientId);
            credentials.add("grant_type", grantType);
            credentials.add("scope", scope);
            credentials.add("username", username);
            credentials.add("password", password);

            URI keyCloakUri = new UriTemplate(this.tokenUri).expand();
            RequestEntity<LinkedMultiValueMap<String, String>> request = RequestEntity.post(keyCloakUri)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(credentials);

            log.info("Requesting KeyCloak token.");
            ResponseEntity<Map<String, String>> response = new RestTemplate().exchange(request,
                    new ParameterizedTypeReference<Map<String, String>>() {
                    });

            if (response.getStatusCode().value() != 200) {
                log.error("Error generation token: {}", response);
                throw new IllegalStateException(response.toString());
            }

            log.info("Token successfully generated.");
            return Objects.requireNonNull(response.getBody()).get("access_token");
        }

        @Bean
        public TestRestTemplate rest() {
            String token = obtainToken();
            ClientHttpRequestInterceptor requestInterceptor = (request, body, execution) -> {
                request.getHeaders().set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
                return execution.execute(request, body);
            };
            TestRestTemplate testRestTemplate = new TestRestTemplate();
            testRestTemplate.getRestTemplate().setInterceptors(List.of(requestInterceptor));
            return testRestTemplate;
        }
    }
}
