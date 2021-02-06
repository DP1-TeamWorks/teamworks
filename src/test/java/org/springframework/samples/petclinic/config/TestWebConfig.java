package org.springframework.samples.petclinic.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.samples.petclinic.configuration.GenericIdToEntityConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class TestWebConfig implements WebMvcConfigurer{
	@Autowired
    GenericIdToEntityConverter idToEntityConverter;
	@Override
    public void addFormatters(FormatterRegistry registry) {

        registry.addConverter(idToEntityConverter);
    }
}
