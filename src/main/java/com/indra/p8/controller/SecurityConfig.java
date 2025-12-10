package com.indra.p8.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // PÚBLICO
                        .requestMatchers("/", "/login", "/registro",
                                "/css/**", "/js/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/registro").permitAll()

                        // ADMIN
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // BIBLIOTECARIO (y ADMIN): operaciones de negocio
                        .requestMatchers(
                                // préstamos / devoluciones
                                "/biblioteca/prestamosLector/**",
                                "/biblioteca/prestar",
                                "/biblioteca/devolucion**",
                                "/biblioteca/devolver/**",
                                // gestión de copias
                                "/biblioteca/crearcopia**",
                                "/biblioteca/copia/**",
                                "/biblioteca/pagar_multa/**",
                                "/biblioteca/export/pdf",
                                // gestión de libros
                                "/biblioteca/crearlibro",
                                "/biblioteca/libro/**",
                                "/biblioteca/libro/{idLibro}/update",
                                "/biblioteca/libro/{idLibro}/delete",
                                // gestión de lectores
                                "/biblioteca/lectores/**"
                        ).hasAnyRole("ADMIN", "BIBLIOTECARIO")

                        // SOCIO (y resto): solo consulta de catálogo
                        .requestMatchers(HttpMethod.GET,
                                "/biblioteca",              // vista principal
                                "/libro", "/copia",         // vistas detalle
                                "/biblioteca/libros",
                                "/biblioteca/getLibro**",
                                "/biblioteca/libro/**",
                                "/biblioteca/getCopias**",
                                "/biblioteca/getCopia**"
                        ).hasAnyRole("ADMIN", "BIBLIOTECARIO", "SOCIO")

                        // Por defecto: autenticado (puedes endurecer a ADMIN si quieres)
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/biblioteca", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

