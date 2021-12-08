package com.nielsen.interview.carservicingappointmentsystem.config;

import com.nielsen.interview.carservicingappointmentsystem.constants.SwaggerConstants;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .packagesToScan(SwaggerConstants.SWAGGER_BASE_PACKAGE)
                .pathsToMatch("/appointments/**")
                .group("user")
                .build();
    }

    @Bean
    public OpenAPI appointSystemOpenApi() {

        Contact contact = new Contact();
        contact.setEmail(SwaggerConstants.SWAGGER_API_CONTACT_EMAIL);
        contact.setName(SwaggerConstants.SWAGGER_API_CONTACT_NAME);
        contact.setUrl(SwaggerConstants.SWAGGER_API_CONTACT_URL);

        return new OpenAPI()
                .info(new Info().title(SwaggerConstants.SWAGGER_API_TITLE)
                        .description(SwaggerConstants.SWAGGER_API_DESCRIPTION)
                        .version(SwaggerConstants.SWAGGER_API_VERSION)
                        .contact(contact));
    }
}
