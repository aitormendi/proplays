package com.tutorial.crud.security;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.persistence.PostRemove;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class CustomBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException {

       response.setHeader("Access-control-Allow-Origin", "*");
      response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
      response.setHeader("Access-Control-Allow-Headers", "x-requested-with, x-auth-token");
        response.setHeader("Access-Control-Max-Age", "3600");
     response.setHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Referrer-Policy","no-referrer-when-downgrade");
       // Referrer-Policy: no-referrer-when-downgrade

        response.addHeader("WWW-Authenticate", "Basic realm=\""+this.getRealmName()+"\"");
        response.setContentType("application/json");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
   
    @PostConstruct
    public void initRealname() {
        this.setRealmName("ADMIN");
    }
    
}
