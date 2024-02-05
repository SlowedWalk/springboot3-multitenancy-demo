package tech.hidetora.instrumentservice.multitenancy.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import tech.hidetora.instrumentservice.multitenancy.exceptions.TenantNotFoundException;

@Slf4j
public class TenantContextHolder {
    private static final ThreadLocal<String> tenantId = new ThreadLocal<>();

    public static void setTenantId(String tenant) {
//        String currentTenant = tenant != null ? tenant : "PUBLIC";
        Assert.hasText(tenant, "tenant cannot be empty");
        log.debug("Setting current tenant to :: {}", tenant);
        tenantId.set(tenant);
    }

    @Nullable
    public static String getTenantId() {
        return tenantId.get();
    }

    public static String getRequiredTenantIdentifier() {
        var tenant = getTenantId();
        if (!StringUtils.hasText(tenant)) {
            throw new TenantNotFoundException("No tenant found in the current context");
        }
        return tenant;
    }

    public static void clearTenant() {
        log.debug("Clearing current tenant");
        tenantId.remove();
    }
}
