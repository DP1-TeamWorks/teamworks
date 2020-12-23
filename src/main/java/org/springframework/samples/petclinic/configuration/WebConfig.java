package org.springframework.samples.petclinic.configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.FormatterRegistry;
import org.springframework.samples.petclinic.middleware.LoginInterceptor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	GenericIdToEntityConverter idToEntityConverter;

    @Override
    public void addFormatters(FormatterRegistry registry) {

        registry.addConverter(idToEntityConverter);
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/resources/static");
        registry.viewResolver(resolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
            .excludePathPatterns("/api/auth/login")
            .excludePathPatterns("/api/auth/signup");
    }
}
