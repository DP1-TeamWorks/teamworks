package org.springframework.samples.petclinic.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.samples.petclinic.middleware.ProjectManagerInterceptor;
import org.springframework.samples.petclinic.middleware.DepartmentManagerInterceptor;
import org.springframework.samples.petclinic.middleware.LoginInterceptor;
import org.springframework.samples.petclinic.middleware.ProjectEmployeeInterceptor;
import org.springframework.samples.petclinic.middleware.TeamOwnerInterceptor;
import org.springframework.samples.petclinic.service.*;
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
        registry.addInterceptor(new LoginInterceptor(userTWService)).addPathPatterns("/api/**")
                .excludePathPatterns("/api/auth/login").excludePathPatterns("/api/auth/signup").order(0);
        registry.addInterceptor(new TeamOwnerInterceptor(userTWService)).addPathPatterns("/api/team/**")
                .addPathPatterns("/api/user/create").addPathPatterns("/api/user/update")
                .addPathPatterns("/api/user/delete")
                .addPathPatterns("/api/departments")
                .addPathPatterns("/api/departments/create")
                .addPathPatterns("/api/departments/**/delete").order(1);
        registry.addInterceptor(new DepartmentManagerInterceptor(userTWService, belongsService, departmentService))
                .addPathPatterns("/api/projects").addPathPatterns("/api/departments/update")
                .addPathPatterns("/api/departments/belongs/create")
                .addPathPatterns("/api/departments/belongs/delete")
                .addPathPatterns("/api/projects/create")
                .addPathPatterns("/api/projects/delete").order(2);
        registry.addInterceptor(
                new ProjectManagerInterceptor(userTWService, belongsService, participationService, projectService, milestoneService))
                .addPathPatterns("/api/tags/create").addPathPatterns("/api/tags/delete")
                .addPathPatterns("/api/projects/participation/create")
                .addPathPatterns("/api/projects/participation/delete")
                .addPathPatterns("/api/projects/update")
                .addPathPatterns("/api/toDos/create").addPathPatterns("api/toDos/assign")
                .addPathPatterns("/api/toDos/delete")
                .addPathPatterns("/api/toDos/updateTitle").addPathPatterns("/api/toDos/setTags")
                .excludePathPatterns("/api/milestones/next").order(3);
        registry.addInterceptor(
                new ProjectEmployeeInterceptor(userTWService, milestoneService, toDoService, participationService, belongsService, projectService))
                .addPathPatterns("/api/tags")
                .addPathPatterns("/api/milestones/next")
                .addPathPatterns("/api/toDos").addPathPatterns("/api/toDos/markAsDone")
                .addPathPatterns("/api/toDos/mine").addPathPatterns("/api/toDos/mine/create")
                .addPathPatterns("/api/projects/participation")
                .addPathPatterns("/api/project/details")
                .addPathPatterns("/api/milestones").order(4);
    }
}
