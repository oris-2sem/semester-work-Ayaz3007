package com.example.ludikgames.security.conf;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;

    private final UserDetailsService userDetailsServiceImpl;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests()
                .requestMatchers( "/register", "/").permitAll()
                .requestMatchers("/resources/**", "/static/**","/webjars/**", "/css/**", "js/**", "images/**").permitAll()
                .requestMatchers("/admin").hasAnyAuthority("ADMIN", "MODERATOR")
                .anyRequest().authenticated();

        httpSecurity.formLogin()
                .loginPage("/login")
                .permitAll()
                .usernameParameter("email")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/games/slots", true)
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .permitAll();

//        httpSecurity.csrf()
//                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
        httpSecurity.headers()
                .xssProtection()
                .and()
                .contentSecurityPolicy("script-src 'self'");


        return httpSecurity.build();
    }

    @Autowired
    public void bindUserDetailsServiceAndPasswordEncoder(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder);
    }
}
