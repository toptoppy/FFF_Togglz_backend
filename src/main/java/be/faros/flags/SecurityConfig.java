package be.faros.flags;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        BasicAuthenticationEntryPoint basicAuthenticationEntryPoint = new BasicAuthenticationEntryPoint();
        basicAuthenticationEntryPoint.setRealmName("application");
        return http
                .authorizeHttpRequests(c -> c
                        .requestMatchers(HttpMethod.POST, "/flags/*/like").permitAll()
                        .requestMatchers(HttpMethod.POST, "/flags").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/flags/*").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/flags").authenticated()
                        .anyRequest().permitAll()
                )
                .userDetailsService(new InMemoryUserDetailsManager(
                        User.withDefaultPasswordEncoder()
                                .username("sheldon")
                                .password("bazinga")
                                .roles("ADMIN")
                                .build(),
                        User.withDefaultPasswordEncoder()
                                .username("leonard")
                                .password("bazinga")
                                .build(),
                        User.withDefaultPasswordEncoder()
                                .username("raj")
                                .password("bazinga")
                                .build(),
                        User.withDefaultPasswordEncoder()
                                .username("howard")
                                .password("bazinga")
                                .build()))
                .httpBasic(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .cors(withDefaults())
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true); // Allow credentials (e.g., cookies, headers)
        corsConfiguration.addAllowedOriginPattern("*"); // Allow all origins or use patterns like "*.example.com"
        corsConfiguration.addAllowedMethod(CorsConfiguration.ALL); // Allow all HTTP methods
        corsConfiguration.addAllowedHeader("*"); // Allow all headers
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }
}
