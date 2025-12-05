package com.indra.p8.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Rutas públicas vs. protegidas
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/crearBibliotecario").permitAll() //para pruebas
                        .requestMatchers("/biblioteca/**", "/libro", "/copia").authenticated()
                        .anyRequest().authenticated()
                )

                // Form login: Spring gestiona POST /login y la sesión
                .formLogin(form -> form
                        .loginPage("/login")             // GET para mostrar login.html
                        .loginProcessingUrl("/login")    // POST que procesa credenciales
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/biblioteca", true)
                        .failureUrl("/login?error=true")
                )

                // Logout (por defecto es POST a /logout)
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID", "remember-me")
                )

                // CSRF habilitado (recomendado para formularios Thymeleaf)
                .csrf(Customizer.withDefaults())

                // Gestión de sesión
                .sessionManagement(sess -> sess
                        .sessionFixation().migrateSession()
                );

        return http.build();
    }
}

