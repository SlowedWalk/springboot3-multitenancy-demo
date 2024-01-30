package tech.hidetora.instrumentservice.instrument;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("instruments")
@RequiredArgsConstructor
public class InstrumentController {
    private final InstrumentRepository instrumentRepository;

    @GetMapping
    public List<Instrument> getInstruments() {
        log.info("Returning all instruments");
        return instrumentRepository.findAll();
    }

    @GetMapping("{type}")
    @Cacheable(cacheNames = "instrumentTypes")
    public List<Instrument> getInstrumentByType(@PathVariable String type) {
        log.info("Returning all instrument of type: {}", type);
        return instrumentRepository.findByType(type);
    }

    @PostMapping()
    public Instrument addInstrument(@RequestBody Instrument instrument) {
        log.info("Adding instrument: {}", instrument.getName());
        return instrumentRepository.save(instrument);
    }


}
