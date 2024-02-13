package tech.hidetora.instrumentservice.multitenancy.tenant;

import org.springframework.lang.Nullable;
import tech.hidetora.instrumentservice.multitenancy.exceptions.TenantNotFoundException;

import java.util.List;

/**
 * Core interface which loads tenant-specific data.
 * It is used throughout the framework as a tenant DAO.
 */
public interface TenantDetailsService {
//    TenantDetails saveTenant(TenantDetails tenantDetails);

    List<TenantDetails> loadAllTenants();

    @Nullable
    TenantDetails loadTenantByIdentifier(String identifier) throws TenantNotFoundException;

}
