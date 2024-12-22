package com.pxp.spring.ai.config;

import org.springframework.web.filter.CorsFilter;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class AppConfig {

    @Bean
    public TomcatConnectorCustomizer customizer() {
        return connector -> connector.setAsyncTimeout(0);
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.addAllowedOriginPattern("*"); // Allow all origins
        configuration.addAllowedHeader("*");        // Allow all headers
        configuration.addAllowedMethod("*");        // Allow all HTTP methods

        source.registerCorsConfiguration("/**", configuration);
        return new CorsFilter(source);
    }
}
