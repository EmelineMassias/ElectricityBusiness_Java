package com.example.BC_Revision.configuration;

import javax.crypto.spec.SecretKeySpec; // N'oubliez pas que ceci est 'javax' et non 'jakarta' pour JAXB, si oot 3+ assurez-vous que c'est bien la bonne dépendvous utilisez Spring Bance. Si c'est pour JWT, c'est bon.

import com.example.BC_Revision.filter.JWTTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration; // Importation nécessaire
import org.springframework.web.cors.CorsConfigurationSource; // Importation nécessaire
import org.springframework.web.cors.UrlBasedCorsConfigurationSource; // Importation nécessaire

import java.util.Arrays; // Importation nécessaire
import java.util.List; // Importation nécessaire

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true)
public class SecurityConfig {

    @Autowired
    private JWTTokenFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults()) // Laissez ceci tel quel, mais le bean corsConfigurationSource va le configurer
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers("/**").permitAll() // Soyez prudent avec /**. Il est souvent mieux de spécifier les endpoints
                                // publics et de sécuriser le reste. Pour le moment, si tout est public, ça va.
                                .anyRequest().authenticated()) // Cette ligne rend toutes les autres requêtes authentifiées. C'est contradictoire avec .requestMatchers("/**").permitAll(). Vous devrez choisir.
                // Si vous voulez tout permettre SAUF certains endpoints, vous devriez être plus précis.
                // Si vous voulez sécuriser la plupart des endpoints, mettez les public en premier.

                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    // NOUVEAU BEAN POUR LA CONFIGURATION CORS EXPLICITE
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Remplacez "http://localhost:4200" par l'URL réelle de votre application Angular
        configuration.setAllowedOrigins(List.of("http://localhost:4200")); // Origines autorisées
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")); // Méthodes HTTP autorisées
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept", "X-Requested-With", "origin", "Access-Control-Request-Method", "Access-Control-Request-Headers")); // En-têtes autorisés
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept", "X-Requested-With", "origin", "Access-Control-Request-Method", "Access-Control-Request-Headers")); // En-têtes exposés au client
        configuration.setAllowCredentials(true); // Autorise l'envoi de cookies et d'informations d'authentification
        configuration.setMaxAge(3600L); // Durée de mise en cache de la pré-requête CORS (en secondes)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Applique cette configuration à tous les chemins
        return source;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            HttpSecurity http,
            PasswordEncoder encoder,
            UserDetailsService userDetailsService
    ) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(encoder)
                .and().build();
    }
}