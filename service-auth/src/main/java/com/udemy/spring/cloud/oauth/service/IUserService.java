package com.udemy.spring.cloud.oauth.service;

import com.udemy.spring.cloud.commons.model.auth.User;

public interface IUserService {

    public User findByUsername(String username);

    public User update(User user, Long idUser);

}
