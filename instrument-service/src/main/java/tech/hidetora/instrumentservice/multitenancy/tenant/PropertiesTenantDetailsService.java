package tech.hidetora.instrumentservice.multitenancy.tenant;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.hidetora.instrumentservice.multitenancy.exceptions.TenantNotFoundException;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertiesTenantDetailsService implements TenantDetailsService {

    private final TenantRepository tenantRepository;

    @Override
    public TenantDetails saveTenant(TenantDetails tenantDetails) {
        Tenant tenant = Tenant.builder()
                .tenantId(tenantDetails.id())
                .password(tenantDetails.password())
                .schema(tenantDetails.id().toUpperCase())
                .enabled(true)
                .createdAt(Instant.now())
                .build();
        Tenant saved = tenantRepository.save(tenant);
        // send event to kafka broker to create a new user for this tenant in the authorization server

        return new TenantDetails(saved.getTenantId(), saved.getSchema(), saved.getPassword(), saved.isEnabled());
    }

    @Override
    public List<TenantDetails> loadAllTenants() {

        return tenantRepository.findAll()
                .stream()
                .map(tenant -> new TenantDetails(tenant.getTenantId(), tenant.getSchema(), tenant.getPassword(), tenant.isEnabled()))
                .toList();
    }

    @Override
    public TenantDetails loadTenantByIdentifier(String identifier) {
        return tenantRepository.findAll().stream()
                .map(tenant -> new TenantDetails(tenant.getTenantId(), tenant.getSchema(), tenant.getPassword(), tenant.isEnabled()))
                .findFirst().orElseThrow(() -> new TenantNotFoundException("tenant doesn't exist"));
    }

}
