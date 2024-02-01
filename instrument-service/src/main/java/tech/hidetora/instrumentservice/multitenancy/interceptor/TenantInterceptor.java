package tech.hidetora.instrumentservice.multitenancy.interceptor;

import io.micrometer.common.KeyValue;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.ServerHttpObservationFilter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import tech.hidetora.instrumentservice.multitenancy.context.TenantContextHolder;
import tech.hidetora.instrumentservice.multitenancy.context.resolver.HttpHeaderTenantResolver;

@Component
@RequiredArgsConstructor
@Slf4j
public class TenantInterceptor implements HandlerInterceptor {
    private final HttpHeaderTenantResolver tenantResolver;

    @Override
    public boolean preHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler
    ) throws Exception {
        var tenantId = tenantResolver.resolveTenantId(request);
        log.info("CURRENT TENANT :: {}", tenantId);
        TenantContextHolder.setTenantId(tenantId);
        MDC.put("tenantId", tenantId);

        ServerHttpObservationFilter.findObservationContext(request).ifPresent(context ->
            context.addHighCardinalityKeyValue(KeyValue.of("tenant.id", tenantId))
        );

        return true;
    }

    @Override
    public void postHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, ModelAndView modelAndView) {
        clearTenant();
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, Exception ex) {
        clearTenant();
    }

    private void clearTenant() {
        MDC.remove("tenantId");
        TenantContextHolder.clearTenant();
    }
}
