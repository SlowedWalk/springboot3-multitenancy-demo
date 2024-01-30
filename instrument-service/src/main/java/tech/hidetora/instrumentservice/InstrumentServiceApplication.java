package tech.hidetora.instrumentservice;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.hidetora.instrumentservice.multitenancy.context.TenantContext;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableCaching
public class InstrumentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InstrumentServiceApplication.class, args);
	}

}