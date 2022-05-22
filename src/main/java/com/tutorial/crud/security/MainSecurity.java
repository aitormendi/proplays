package com.tutorial.crud.security;


import com.tutorial.crud.jwt.JwtEntryPoint;
import com.tutorial.crud.jwt.JwtTokenFilter;
import com.tutorial.crud.security.application.service.UserDetailsServiceImpl;
import com.tutorial.crud.shared.PowerException;
import com.tutorial.crud.shared.PowerResponse;
import com.tutorial.crud.shared.dto.CustomErrorDTO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Configuration
@EnableWebSecurity
public class MainSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomBasicAuthenticationEntryPoint customBasicAuthenticationEntryPoint;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    JwtEntryPoint jwtEntryPoint;

    @Bean
    public JwtTokenFilter jwtTokenFilter() {
        return new JwtTokenFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //Pasaremos el userdetailsService obtenemos usuario y ciframos contraseña
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       /* http.csrf().disable();
        http.httpBasic().disable();
        http.formLogin().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.exceptionHandling().authenticationEntryPoint(this::unauthorized);*/
        CorsConfiguration cors = new CorsConfiguration().applyPermitDefaultValues();
        cors.addAllowedMethod(HttpMethod.PUT);
        cors.addAllowedMethod(HttpMethod.DELETE);
        cors.addAllowedMethod(HttpMethod.GET);
        cors.addAllowedMethod(HttpMethod.POST);
        http.cors().configurationSource(request -> cors);

        //Aquí definimos las peticiónes que va a poder realizar cada usuario
        // probado en swagger funciona correctamente no obligatorio meter todas porque
        //cada apartado en el front lo tratamos en función del usuario que nos llega
        //.antMatchers(HttpMethod.GET, "/actividad/listaActividades").hasAuthority("ROLE_ALUMNO")
        //Del mismo modo que en un simple login pillamos usuario obtenemos rol y a partir del rol obtenemos lo que queremos
        http.httpBasic()
                .authenticationEntryPoint(customBasicAuthenticationEntryPoint)
                .and()
                .authorizeRequests()
                //.antMatchers(HttpMethod.POST, "/actividad/crearActividad").hasAuthority("ROLE_PROFE")
                //.antMatchers(HttpMethod.GET, "/actividad/listaActividades").hasAuthority("ROLE_ALUMNO")
                .anyRequest().permitAll()
                .and().csrf().disable()
                .addFilterBefore(jwtTokenFilter(), AnonymousAuthenticationFilter.class);

        //   La de la guía en la que se pasa el nombre de usuario y el password .addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        //.addFilterBefore(authenticationFilter, AnonymousAuthenticationFilter.class);*/

    }

    //Método para que no se realicen peticiones si no estás autorizado
    @SneakyThrows
    private void unauthorized(
            HttpServletRequest req, HttpServletResponse res, AuthenticationException e) {
        PowerResponse powerResponse = new PowerResponse(res);
        PowerException powerException = new PowerException(e);
        log.warn(powerException.getFullMessage());
        CustomErrorDTO customErrorDTO = new CustomErrorDTO(powerException);
        powerResponse.sendJson(customErrorDTO, HttpStatus.UNAUTHORIZED.value());
    }
}

///OTRA CONFIGURACIÓN

 /*   @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }*/


  /*  protected void configure(HttpSecurity http) throws Exception {
 http.cors().and().csrf().disable();
       http.csrf().disable();
               http.httpBasic().disable();
               http.formLogin().disable();
               http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
               http.exceptionHandling().authenticationEntryPoint(this::unauthorized);

               http.antMatcher("/api/**")
               .authorizeRequests()
               .antMatchers("/api/v0/oauth/**")
               .permitAll()
               .anyRequest()
               .authenticated()
               .and()
               .addFilterBefore(jwtTokenFilter(), AnonymousAuthenticationFilter.class);
 La de la guía en la que se pasa el nombre de usuario y el password .addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
 .addFilterBefore(authenticationFilter, AnonymousAuthenticationFilter.class);*/
