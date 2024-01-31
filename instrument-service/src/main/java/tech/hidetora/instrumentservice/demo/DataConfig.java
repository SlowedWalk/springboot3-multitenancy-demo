package tech.hidetora.instrumentservice.demo;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import tech.hidetora.instrumentservice.instrument.Instrument;
import tech.hidetora.instrumentservice.instrument.InstrumentRepository;
import tech.hidetora.instrumentservice.multitenancy.context.TenantContextHolder;

import java.util.List;

@Configuration(proxyBeanMethods = false)
public class DataConfig {

    private final InstrumentRepository instrumentRepository;

    public DataConfig(InstrumentRepository instrumentRepository) {
        this.instrumentRepository = instrumentRepository;
    }

//    @EventListener(ApplicationReadyEvent.class)
    public void loadTestData() {
        TenantContextHolder.setTenantId("dukes");
            var piano = Instrument.builder().name("Steinway").type("piano").build();
            var cello = Instrument.builder().name("Cello").type("string").build();
            var guitar = Instrument.builder().name("Gibson Firebird").type("guitar").build();
            instrumentRepository.saveAll(List.of(piano, cello, guitar));
        TenantContextHolder.clearTenant();

        TenantContextHolder.setTenantId("beans");
            var organ = Instrument.builder().name("Hammond B3").type("organ").build();
            var viola = Instrument.builder().name("Viola").type("organ").build();
            var guitarFake = Instrument.builder().name("Gibson Firebird (Fake)").type("guitar").build();
            instrumentRepository.saveAll(List.of(organ, viola, guitarFake));
        TenantContextHolder.clearTenant();
    }

}
