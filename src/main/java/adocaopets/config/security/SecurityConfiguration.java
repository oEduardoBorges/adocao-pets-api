package adocaopets.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    private final String[] ACESSO = { "/api/v1/auth/**", "/v2/api-docs", "/v3/api-docs", "/v3/api-docs/**",
            "/swagger-resources", "/swagger-resources/**", "/configuration/ui", "/configuration/security",
            "/swagger-ui/**", "/webjars/**", "/swagger-ui.html" };

    private final String[] ACESSO_PUBLIC_CONTROLLERS = { "/login/registro", "/login/logar"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         http
                .cors().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeHttpRequests()
                .requestMatchers(ACESSO).permitAll()
                .requestMatchers(ACESSO_PUBLIC_CONTROLLERS).permitAll()
                .anyRequest().permitAll()
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin(CorsConfiguration.ALL);
        config.addAllowedHeader("*");
        config.addAllowedOriginPattern("*");
        config.setAllowedOrigins(Collections.singletonList("*"));
        config.addExposedHeader("Authorization");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
