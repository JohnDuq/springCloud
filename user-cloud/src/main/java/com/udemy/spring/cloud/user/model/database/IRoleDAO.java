package com.udemy.spring.cloud.user.model.database;

import java.util.List;

import com.udemy.spring.cloud.user.model.data.Role;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "role-dao")
public interface IRoleDAO extends PagingAndSortingRepository<Role, Long> {

    @Query("SELECT r FROM Role r WHERE r.idRole IN ("
            + "SELECT ur.role.idRole FROM UserRole ur WHERE ur.user.username = :username)")
    public List<Role> getRolesByUser(@Param("username") String username);

}
