package com.tutorial.crud.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {

    //Para ver el error donde nos puede dar la aplicación (esto se usa en desarrollo)
    private final static Logger logger= LoggerFactory.getLogger(JwtEntryPoint.class);

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException authException) throws IOException, ServletException {
        logger.error("fallo en el metodo commence");
        res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "no autorizado");
    }
}
