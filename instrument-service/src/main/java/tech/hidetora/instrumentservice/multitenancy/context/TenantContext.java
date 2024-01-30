package tech.hidetora.instrumentservice.multitenancy.context;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TenantContext {
    private static final ThreadLocal<String> tenantId = new InheritableThreadLocal<>();

    public static void setTenantId(String tenant) {
//        String currentTenant = tenant != null ? tenant : "PUBLIC";
        log.debug("Setting current tenant to :: {}", tenant);
        tenantId.set(tenant);
    }

    public static String getTenantId() {
        return tenantId.get();
    }

    public static void clearTenant() {
        tenantId.remove();
    }
}
