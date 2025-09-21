package com.example.api_gateway.config;

import com.example.api_gateway.filter.AuthFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutesConfig {

    private AuthFilter authFilter;

    public GatewayRoutesConfig(AuthFilter authFilter) {
        this.authFilter = authFilter;
    }

    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder builder){
        return builder.routes()
                .route("product-service", r -> r
                        .path("/api/v1/products/**")
                        .filters(f -> f.filter(authFilter))
                                .uri("lb://product-service")
                )
                .build();
    }
}
