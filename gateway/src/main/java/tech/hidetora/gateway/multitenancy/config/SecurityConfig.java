package tech.hidetora.gateway.multitenancy.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationEntryPoint;
import tech.hidetora.gateway.multitenancy.security.TenantClientRegistrationRepository;

@Configuration(proxyBeanMethods = false)
public class SecurityConfig {

    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http, TenantClientRegistrationRepository clientRegistrationRepository) {
        return http
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/actuator/**", "/tenant-login/**").permitAll()
                        .anyExchange().authenticated())
                .oauth2Login(oauth2 -> oauth2
                        .clientRegistrationRepository(clientRegistrationRepository))
                .exceptionHandling(exception ->
                        exception.authenticationEntryPoint(new RedirectServerAuthenticationEntryPoint("/tenant-login")))
                .build();
    }

}
