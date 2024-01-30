package tech.hidetora.instrumentservice.multitenancy.resolver;

import org.springframework.lang.NonNull;

@FunctionalInterface
public interface TenantResolver<T> {

    String resolveTenantId(@NonNull T object);

}
