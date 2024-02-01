package tech.hidetora.gateway.multitenancy;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;

@ConfigurationProperties(prefix = "tenant.security")
public record TenantSecurityProperties(
        URI issuerBaseUri,
        String clientId,
        String clientSecret
){}
