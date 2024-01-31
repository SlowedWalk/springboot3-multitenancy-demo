package tech.hidetora.instrumentservice.multitenancy.data.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.stereotype.Component;
import tech.hidetora.instrumentservice.multitenancy.context.TenantContextHolder;

import java.lang.reflect.Method;

/**
 * An implementation of {@link KeyGenerator} that generates cache keys combining the
 * current tenant identifier with the given method and parameters.
 */
@Component
@Slf4j
public final class TenantKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        Object key = SimpleKeyGenerator.generateKey(TenantContextHolder.getTenantId(), params);
        log.info("Generated cache key: {}", key);
        return SimpleKeyGenerator.generateKey(TenantContextHolder.getTenantId(), params);
    }

}
