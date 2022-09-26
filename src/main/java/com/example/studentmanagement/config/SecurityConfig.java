package com.example.studentmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
public class SecurityConfig {
    private CustomSuccessHandler customSuccessHandler;

    private CustomFailureHandler failureHandler;

    public SecurityConfig(CustomSuccessHandler customSuccessHandler, CustomFailureHandler failureHandler) {
        this.customSuccessHandler = customSuccessHandler;
        this.failureHandler = failureHandler;
    }
//interceptor, filter
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/student/**").hasAnyRole("STUDENT", "ADMIN")
                .antMatchers("/api/**","/swagger_ui/**","api-docs/**").permitAll()
//                .anyRequest().authenticated()
                .and()
//                .cors()
            .formLogin(Customizer.withDefaults())
//                .loginPage("/login")
//                .usernameParameter("email")
//                .passwordParameter("password")
//                .successHandler(customSuccessHandler)
//                .failureHandler(failureHandler)
//                .permitAll()
//                .failureUrl("/login?error")
//                .and()
            .exceptionHandling()
                .accessDeniedPage("/accessDenied")
                .and()
            .logout()
                .permitAll();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
