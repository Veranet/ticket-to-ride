package pl.veranet.tickettoroute.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;

import java.util.List;

@Configuration
public class ApplicationConfig {

    @Autowired
    private ConversionService conversionService;
    @Autowired
    private List<Converter<?, ?>> converters;

    @PostConstruct
    void init() {
        DefaultConversionService conversionService = new DefaultConversionService();
        converters.forEach(conversionService::addConverter);
    }
}
