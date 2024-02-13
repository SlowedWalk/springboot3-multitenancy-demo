package tech.hidetora.instrumentservice.multitenancy.filter;

import io.micrometer.common.KeyValue;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.filter.ServerHttpObservationFilter;
import tech.hidetora.instrumentservice.multitenancy.context.TenantContextHolder;
import tech.hidetora.instrumentservice.multitenancy.context.resolver.HttpHeaderTenantResolver;
import tech.hidetora.instrumentservice.multitenancy.exceptions.TenantResolutionException;
import tech.hidetora.instrumentservice.multitenancy.tenant.TenantDetailsService;

import java.io.IOException;

/**
 * Establish a tenant context from an HTTP request, if tenant information is available.
 */
@Component
public class TenantContextFilter extends OncePerRequestFilter {

    private final HttpHeaderTenantResolver httpRequestTenantResolver;
    private final TenantDetailsService tenantDetailsService;

    public TenantContextFilter(HttpHeaderTenantResolver httpHeaderTenantResolver, TenantDetailsService tenantDetailsService) {
        this.httpRequestTenantResolver = httpHeaderTenantResolver;
        this.tenantDetailsService = tenantDetailsService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        var tenantIdentifier = httpRequestTenantResolver.resolveTenantId(request);

        if (StringUtils.hasText(tenantIdentifier) && isTenantValid(tenantIdentifier)) {
            TenantContextHolder.setTenantId(tenantIdentifier);
            configureLogs(tenantIdentifier);
            configureTraces(tenantIdentifier, request);
        } else {
            throw new TenantResolutionException("A valid tenant must be specified for requests to %s".formatted(request.getRequestURI()));
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            clearTenant();
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getRequestURI().startsWith("/actuator");
    }

    private boolean isTenantValid(String tenantIdentifier) {
        var tenantDetails = tenantDetailsService.loadTenantByIdentifier(tenantIdentifier);
        return tenantDetails != null && tenantDetails.enabled();
    }

    private void configureLogs(String tenantId) {
        MDC.put("tenantId", tenantId);
    }

    private void configureTraces(String tenantId, HttpServletRequest request) {
        ServerHttpObservationFilter.findObservationContext(request).ifPresent(context ->
                context.addHighCardinalityKeyValue(KeyValue.of("tenant.id", tenantId)));
    }

    private void clearTenant() {
        MDC.remove("tenantId");
        TenantContextHolder.clearTenant();
    }
}
