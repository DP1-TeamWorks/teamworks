package org.springframework.samples.petclinic.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.samples.petclinic.configuration.GenericIdToEntityConverter;
import org.springframework.samples.petclinic.middleware.DepartmentManagerInterceptor;
import org.springframework.samples.petclinic.middleware.LoginInterceptor;
import org.springframework.samples.petclinic.middleware.ProjectEmployeeInterceptor;
import org.springframework.samples.petclinic.middleware.ProjectManagerInterceptor;
import org.springframework.samples.petclinic.middleware.TeamOwnerInterceptor;
import org.springframework.samples.petclinic.service.BelongsService;
import org.springframework.samples.petclinic.service.DepartmentService;
import org.springframework.samples.petclinic.service.MilestoneService;
import org.springframework.samples.petclinic.service.ParticipationService;
import org.springframework.samples.petclinic.service.ProjectService;
import org.springframework.samples.petclinic.service.ToDoService;
import org.springframework.samples.petclinic.service.UserTWService;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
@Configuration
public class TestInterceptorsWebConfig implements WebMvcConfigurer {
	@Autowired
    GenericIdToEntityConverter idToEntityConverter;

    @Autowired
    UserTWService userTWService;

    @Autowired
    BelongsService belongsService;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    MilestoneService milestoneService;

    @Autowired
    ToDoService toDoService;

    @Autowired
    ParticipationService participationService;

    @Autowired
    ProjectService projectService;

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
        registry.addInterceptor(new LoginInterceptor(userTWService))
        		.addPathPatterns("/api/InterceptorTest/Login").order(0);
        registry.addInterceptor(new TeamOwnerInterceptor(userTWService))
        		.addPathPatterns("/api/InterceptorTest/TeamOwner").order(1);
        registry.addInterceptor(new DepartmentManagerInterceptor(userTWService, belongsService, departmentService))
        		.addPathPatterns("/api/InterceptorTest/DepartmentManager").order(2);
        registry.addInterceptor(new ProjectManagerInterceptor(userTWService, belongsService, participationService, projectService, milestoneService))
                .addPathPatterns("/api/InterceptorTest/ProjectManager").order(3);
        registry.addInterceptor(new ProjectEmployeeInterceptor(userTWService, milestoneService, toDoService, participationService, belongsService, projectService))
                .addPathPatterns("/api/InterceptorTest/ProjectEmployee").order(4);
    }
}
