package tech.hidetora.instrumentservice.multitenancy.tenant;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "multitenancy")
public record TenantDetailsProperties(List<TenantDetails> tenants) { }
