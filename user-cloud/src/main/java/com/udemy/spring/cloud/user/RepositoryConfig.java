package com.udemy.spring.cloud.user;

import com.udemy.spring.cloud.commons.model.auth.Role;
import com.udemy.spring.cloud.commons.model.auth.User;
import com.udemy.spring.cloud.commons.model.auth.UserRole;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class RepositoryConfig implements RepositoryRestConfigurer {

    // Metodo para exponer el id de las clases en los json de respuesta de los
    // @RepositoryRestResource
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        config.exposeIdsFor(User.class, Role.class, UserRole.class);
    }

}
