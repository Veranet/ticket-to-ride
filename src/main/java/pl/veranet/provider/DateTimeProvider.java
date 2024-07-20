package pl.veranet.provider;

import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class DateTimeProvider {
    public Instant now() {
        return Instant.now();
    }
}
