package com.udemy.spring.cloud.user.model.database;

import com.udemy.spring.cloud.user.model.data.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "user-dao")
public interface IUserDAO extends PagingAndSortingRepository<User, Long> {

    public User findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.username = :username")
    public User getByUsername(@Param("username") String username);

}
