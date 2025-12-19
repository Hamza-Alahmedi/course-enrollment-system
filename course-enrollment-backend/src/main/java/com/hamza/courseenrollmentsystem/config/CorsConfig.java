package com.hamza.courseenrollmentsystem.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${allowed.origins:https://course-enrollment-frontend-c9mr.onrender.com}")
    private String allowedOrigins;

    private List<String> getAllowedOriginsList() {
        return Arrays.stream(allowedOrigins.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        List<String> origins = getAllowedOriginsList();
        boolean wildcard = origins.size() == 1 && "*".equals(origins.get(0));

        if (wildcard) {
            // If wildcard is used, we cannot allow credentials. Use patterns instead if needed.
            registry.addMapping("/api/**")
                    .allowedOriginPatterns("*")
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                    .allowedHeaders("*")
                    .allowCredentials(false)
                    .maxAge(3600);
        } else {
            registry.addMapping("/api/**")
                    .allowedOrigins(origins.toArray(new String[0]))
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                    .allowedHeaders("*")
                    .allowCredentials(true)
                    .maxAge(3600);
        }
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        List<String> origins = getAllowedOriginsList();
        boolean wildcard = origins.size() == 1 && "*".equals(origins.get(0));

        if (wildcard) {
            config.setAllowCredentials(false);
            config.setAllowedOriginPatterns(Collections.singletonList("*"));
        } else {
            config.setAllowCredentials(true);
            config.setAllowedOrigins(origins);
        }

        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        config.setMaxAge(3600L);
        source.registerCorsConfiguration("/api/**", config);
        return new CorsFilter(source);
    }
}
