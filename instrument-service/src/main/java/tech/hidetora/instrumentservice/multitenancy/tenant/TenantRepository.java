package tech.hidetora.instrumentservice.multitenancy.tenant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface TenantRepository extends JpaRepository<Tenant, String> {
}
