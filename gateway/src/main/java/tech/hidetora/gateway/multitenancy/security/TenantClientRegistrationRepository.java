package tech.hidetora.gateway.multitenancy.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrations;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import tech.hidetora.gateway.multitenancy.TenantSecurityProperties;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TenantClientRegistrationRepository implements ReactiveClientRegistrationRepository {

    private static final Map<String,Mono<ClientRegistration>> clientRegistrations = new ConcurrentHashMap<>();
    private final TenantSecurityProperties tenantSecurityProperties;

    @Value("${tenant.security.issuer-base-uri}")
    private String issuerBaseUri;

    public TenantClientRegistrationRepository(TenantSecurityProperties tenantSecurityProperties) {
        this.tenantSecurityProperties = tenantSecurityProperties;
    }

    @Override
    public Mono<ClientRegistration> findByRegistrationId(String registrationId) {
        return clientRegistrations.computeIfAbsent(registrationId, this::buildClientRegistration);
    }

    private Mono<ClientRegistration> buildClientRegistration(String registrationId) {
        return Mono.just(ClientRegistrations.fromOidcIssuerLocation(issuerBaseUri)
                .registrationId(registrationId)
                .clientId(tenantSecurityProperties.clientId())
                .clientSecret(tenantSecurityProperties.clientSecret())
                .redirectUri("{baseUrl}/login/oauth2/code/" + registrationId)
                .scope("openid")
                .build());
    }

//    private String computeTenantIssuerUri(String tenantId) {
//        var issuerBaseUri = tenantSecurityProperties.issuerBaseUri().toString().strip();
//        return issuerBaseUri + tenantId;
//    }

}
