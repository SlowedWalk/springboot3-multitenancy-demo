package tech.hidetora.instrumentservice.multitenancy.security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.stereotype.Component;
import tech.hidetora.instrumentservice.multitenancy.context.TenantContextHolder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class TenantAuthenticationManagerResolver implements AuthenticationManagerResolver<HttpServletRequest> {
    private static final Map<String, AuthenticationManager> authenticationManagers = new ConcurrentHashMap<>();
//    private final TenantSecurityProperties tenantSecurityProperties;

    @Override
    public AuthenticationManager resolve(HttpServletRequest request) {
        var tenantId = TenantContextHolder.getRequiredTenantIdentifier();
        return authenticationManagers.computeIfAbsent(tenantId, this::buildAuthenticationManager);
    }

    private AuthenticationManager buildAuthenticationManager(String tenantId) {
        var issuerBaseUri = "http://localhost:8080";
        var jwtAuthenticationprovider = new JwtAuthenticationProvider(JwtDecoders.fromIssuerLocation(issuerBaseUri));
        return jwtAuthenticationprovider::authenticate;
    }
}
