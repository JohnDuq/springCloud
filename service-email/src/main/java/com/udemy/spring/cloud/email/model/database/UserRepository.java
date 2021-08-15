package com.udemy.spring.cloud.email.model.database;

import com.udemy.spring.cloud.email.model.data.UserEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserRepository extends CrudRepository<UserEntity, String> {

    UserEntity findByEmailIdIgnoreCase(String emailId);

}