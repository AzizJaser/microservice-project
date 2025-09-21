package com.example.api_gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthFilter implements GatewayFilter {

    WebClient webClient = WebClient.builder().build();


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        if(path.startsWith("/api/v1/auth/")){
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer")){
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

//        String token = authHeader.replace("Bearer ","");

        return webClient
                .get()
                .uri("http://localhost:8081/api/v1/auth/validate") // You’ll replace with service name in docker/k8s
                .header("Authorization", authHeader)
                .retrieve()
                .toBodilessEntity()
                .flatMap(response -> {
                    // ✅ Token is valid → forward the request
                    return chain.filter(exchange);
                })
                .onErrorResume(error -> {
                    // ❌ Token is invalid → return 401
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                });

    }

}
