package org.bassani.examplebff.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.ClientCredentialsGrant;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

	@Value("${springfox.documentation.swagger.v2.home}")
	private String home;

	@Value("${springfox.documentation.swagger.v2.contactEmail}")
	private String email;

	@Value("${kong.path}")
	private String kongPath;

	@Value("${kong.host}")
	private String kongHost;

	@Autowired
	private Docket dock;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		dock.apiInfo(apiInfo());
		registry.addResourceHandler(home + "/**").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.host(kongHost)
				.consumes(Set.of(MediaType.APPLICATION_JSON_VALUE))
				.produces(Set.of(MediaType.APPLICATION_JSON_VALUE))
				.protocols(Set.of("https", "http"))
				.select()
				.apis(RequestHandlerSelectors.basePackage("org.bassani.examplebff.controller"))
				.paths(PathSelectors.any()).build()
				.pathMapping("/")
				.securitySchemes(Collections.singletonList(oauth()))
				.useDefaultResponseMessages(false)
				.tags(new Tag("Purchasesimulatorbff", "Description of Purchasesimulatorbff"))
				.apiInfo(apiInfo());

	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Purchase Simulator BFF API Rest")
				.description("Documentação da API Purchase Simulator BFF")
				.version("1.0")
				.contact(new Contact("Developer", null, email))
				.build();
	}

	@Bean
	List<GrantType> grantTypes() {
		final String tokenUrl = String.format("https://%s%s/oauth2/token",
				kongHost, kongPath);
		List<GrantType> grantTypes = new ArrayList<>();
		grantTypes.add(new ClientCredentialsGrant(tokenUrl));
		return grantTypes;
	}

	@Bean
	SecurityScheme oauth() {
		return new OAuthBuilder()
				.name("OAuth2")
				.scopes(scopes())
				.grantTypes(grantTypes())
				.build();
	}

	private List<AuthorizationScope> scopes() {
		List<AuthorizationScope> list = new ArrayList<>();
		list.add(new AuthorizationScope("read", "Grants read access"));
		list.add(new AuthorizationScope("write", "Grants write access"));
		return list;
	}

}
