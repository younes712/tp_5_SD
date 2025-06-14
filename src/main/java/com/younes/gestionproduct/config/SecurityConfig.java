package com.younes.gestionproduct.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return new InMemoryUserDetailsManager(
                User.withUsername("admin").password(passwordEncoder.encode("123")).roles("ADMIN").build(),
                User.withUsername("younes").password(passwordEncoder.encode("123")).roles("USER").build(),
                User.withUsername("imad").password(passwordEncoder.encode("123")) .roles("USER").build()
        );
    }

    @Bean
    public SecurityFilterChain springFilterChain(HttpSecurity http) throws Exception {
        return http
                .formLogin(fl->fl.loginPage("/login"))
                .authorizeHttpRequests(ath->ath.requestMatchers("/user/index/**","/user/search/**").hasRole("USER"))
                .authorizeHttpRequests(ath->ath.requestMatchers("/admin/newProduct/**","/admin/save/**","/admin/delete**" ,"/user/search/**","/admin/editProduct/**").hasRole("ADMIN"))
                .authorizeHttpRequests(ath->ath.requestMatchers("/public/**","/login","/webjars/**").permitAll())
                .authorizeHttpRequests(ath->ath.anyRequest().authenticated())
                .exceptionHandling(eh->eh.accessDeniedPage("/notAuthorized"))
                .build();

    }

}
