package tech.hidetora.instrumentservice.multitenancy.tenant;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Provides core tenant information.
 */
public record TenantDetails(
        String id,
        String schema,
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        String password,
        boolean enabled
) {}