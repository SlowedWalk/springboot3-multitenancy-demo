package tech.hidetora.instrumentservice.multitenancy.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;

@ConfigurationProperties(prefix = "multitenancy.security")
public record TenantSecurityProperties(
        URI issuerBaseUri
){}
