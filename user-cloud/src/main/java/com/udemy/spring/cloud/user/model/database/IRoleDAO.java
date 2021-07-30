package com.udemy.spring.cloud.user.model.database;

import com.udemy.spring.cloud.user.model.data.Role;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "role-dao")
public interface IRoleDAO extends PagingAndSortingRepository<Role, Long> {

}
