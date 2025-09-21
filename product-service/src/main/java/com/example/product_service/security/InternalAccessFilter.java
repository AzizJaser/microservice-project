package com.example.product_service.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Component
public class InternalAccessFilter implements Filter {


    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String header = httpRequest.getHeader("X-Internal-Call");

        if (!"true".equalsIgnoreCase(header)) {
            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            httpResponse.getWriter().write("Access denied: Only API Gateway can access this service");
            return;
        }

        filterChain.doFilter(request,response);
    }
}