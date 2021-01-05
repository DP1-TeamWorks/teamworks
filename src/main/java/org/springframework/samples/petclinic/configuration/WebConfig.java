package org.springframework.samples.petclinic.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.samples.petclinic.middleware.DepartmentManagerInterceptor;
import org.springframework.samples.petclinic.middleware.LoginInterceptor;
import org.springframework.samples.petclinic.middleware.TeamOwnerInterceptor;
import org.springframework.samples.petclinic.service.BelongsService;
import org.springframework.samples.petclinic.service.UserTWService;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	
	
	
    
	
	@Autowired
	GenericIdToEntityConverter idToEntityConverter;
	@Autowired
	UserTWService userTWService;
	@Autowired
	BelongsService belongsService;
    @Override
    public void addFormatters(FormatterRegistry registry) {

        registry.addConverter(idToEntityConverter);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("/resources/frontend/teamworks/build/");
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setViewClass(InternalResourceView.class);
        registry.viewResolver(resolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
            .addPathPatterns("/api/**")
            .excludePathPatterns("/api/auth/login")
            .excludePathPatterns("/api/auth/signup").order(0);
        registry.addInterceptor(new TeamOwnerInterceptor(userTWService)).addPathPatterns("/api/teams/**").addPathPatterns("/api/userTW").addPathPatterns("/api/departments").order(1);
        registry.addInterceptor(new DepartmentManagerInterceptor(userTWService,belongsService)).addPathPatterns("/api/projects").order(2);    
        }
}
