package com.udemy.spring.cloud.user.model.database;

import com.udemy.spring.cloud.commons.model.auth.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(path = "user-dao")
public interface IUserDAO extends PagingAndSortingRepository<User, Long> {

    public User findByUsername(@Param("username") String username);

    public User findByEmail(@Param("email") String email);

    @RestResource(path = "find-id")
    public User findByIdUser(@Param("id") Long idUser);

    @Query("SELECT u FROM User u WHERE u.username = :username")
    public User getByUsername(@Param("username") String username);

}
