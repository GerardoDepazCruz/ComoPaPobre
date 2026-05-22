package com.ComoPaPobre.main.security;

import com.ComoPaPobre.main.services.CustomUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.context.request.RequestContextListener;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    @Autowired
    private LoginAttemptFilter loginAttemptFilter;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // Configuración para ADMIN con prioridad alta
    @Bean
    @Order(1)
    public SecurityFilterChain adminSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/admin/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/login").permitAll()
                        .anyRequest().hasRole("ADMIN"))

                .sessionManagement(session -> session
                        .invalidSessionUrl("/admin/login?session=expired") // Redirección si expira la sesión
                )

                .formLogin(form -> form
                        .loginPage("/admin/login")
                        .loginProcessingUrl("/admin/login")
                        .defaultSuccessUrl("/admin/gestionar", true)
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/admin/logout")
                        .logoutSuccessUrl("/admin/login?logout")
                        .permitAll())
                .csrf(csrf -> csrf.disable()); // Opcional, valora si lo necesitas
        return http.build();
    }

    // Configuración para usuarios normales con prioridad menor
    @Bean
    @Order(2)
    public SecurityFilterChain userSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(loginAttemptFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/crearcuenta", "/registro", "/css/*", "/js/", "/img/*", "/", "/login",
                                "/contacto", "/inicio", "/menualit", "/menubebid",
                                "/menuhambur", "/menupech", "/menusalchi", "/nosotros", "/preguntasfrecuentes",
                                "/promociones", "/terminosCondiciones")
                        .permitAll()
                        .anyRequest().authenticated())

                .sessionManagement(session -> session
                        .invalidSessionUrl("/login?session=expired") // Redirección si expira la sesión
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll())
                .csrf(csrf -> csrf.disable()); // Opcional, valora si lo necesitas
        return http.build();
    }

    // Configura el AuthenticationManager con el provider
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    // Para que Spring detecte correctamente el cierre por inactividad
    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<>(new HttpSessionEventPublisher());
    }

     @Bean
    public RequestContextListener requestContextListener() {
         return new RequestContextListener();

}

}
 
