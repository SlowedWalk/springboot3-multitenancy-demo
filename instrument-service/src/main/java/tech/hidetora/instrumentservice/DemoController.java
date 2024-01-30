package tech.hidetora.instrumentservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.hidetora.instrumentservice.multitenancy.context.TenantContext;

@RestController
public class DemoController {
    @GetMapping("tenant")
    String getTenant() {
        return TenantContext.getTenantId();
    }
}
