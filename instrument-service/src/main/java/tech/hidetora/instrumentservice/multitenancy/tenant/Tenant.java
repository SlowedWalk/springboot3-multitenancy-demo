package tech.hidetora.instrumentservice.multitenancy.tenant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Tenant {
    @Id
    @Column(name = "tenant_id", nullable = false, unique = true)
    private String tenantId;
    private String schema;
    private String password;
    boolean enabled;
    @CreatedDate
    private Instant createdAt;
}
