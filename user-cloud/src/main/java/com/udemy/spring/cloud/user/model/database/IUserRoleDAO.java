package com.udemy.spring.cloud.user.model.database;

import com.udemy.spring.cloud.user.model.data.UserRole;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "user-role-dao")
public interface IUserRoleDAO extends PagingAndSortingRepository<UserRole, Long> {

}
