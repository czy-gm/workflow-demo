package ai.powerlaw.workflow.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApiControllerPathPrefixConfiguration implements WebMvcConfigurer {
    @Value("${spring.api.controller.path-prefix}")
    private String pathPrefix;

    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix(pathPrefix, c -> c.isAnnotationPresent(RestController.class));
    }
}
