package com.udemy.spring.cloud.user.model.database;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.udemy.spring.cloud.commons.model.auth.UserRole;

@RepositoryRestResource(path = "user-role-dao")
public interface IUserRoleDAO extends PagingAndSortingRepository<UserRole, Long> {

    public UserRole save(UserRole userRole);

}
