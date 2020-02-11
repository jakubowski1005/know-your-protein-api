package com.jakubowskiartur.knowyourprotein.config;

import com.jakubowskiartur.knowyourprotein.security.AuthFilter;
import com.jakubowskiartur.knowyourprotein.security.CustomEndpointAuthorization;
import com.jakubowskiartur.knowyourprotein.security.UnauthorizedResponseAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.inject.Inject;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Inject private CustomEndpointAuthorization auth;
    @Inject private UnauthorizedResponseAuthenticationEntryPoint unauthorizedHandler;

    @Bean
    public AuthFilter authFilter() {
        return new AuthFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .requiresChannel()
                    .requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null)
                    .requiresSecure()
                    .and()
                .cors()
                    .and()
                .csrf()
                    .disable()
                .exceptionHandling()
                    .authenticationEntryPoint(unauthorizedHandler)
                    .and()
                .headers()
                    .frameOptions()
                    .disable()
                    .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                .authorizeRequests()
                    .antMatchers("/")
                        .permitAll()
                    .antMatchers("/analyze")
                        .permitAll()
                    .antMatchers("/auth/signup")
                        .permitAll()
                    .antMatchers("/h2-console/**")
                        .permitAll()
                    .antMatchers("/api/users/{userId}/**")
                        .access("@auth.isUserAuthorized(authentication, #userId)")
                    .antMatchers("/api/users?username={usernameOrEmail}")
                        .access("@auth.isUserAuthorizedToGetUserByUsername(authentication, #usernameOrEmail)")
                .anyRequest()
                    .authenticated();

        http.addFilterBefore(authFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}