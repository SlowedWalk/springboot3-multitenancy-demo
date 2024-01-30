package tech.hidetora.instrumentservice.multitenancy.resolver;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import tech.hidetora.instrumentservice.multitenancy.TenantHttpProperties;

@Component
public class HttpHeaderTenantResolver implements TenantResolver<HttpServletRequest> {
    private final TenantHttpProperties tenantHttpProperties;

    public HttpHeaderTenantResolver(TenantHttpProperties tenantHttpProperties) {
        this.tenantHttpProperties = tenantHttpProperties;
    }

    @Override
    public String resolveTenantId(@NonNull HttpServletRequest request) {
        return request.getHeader(tenantHttpProperties.headerName());
    }
}
