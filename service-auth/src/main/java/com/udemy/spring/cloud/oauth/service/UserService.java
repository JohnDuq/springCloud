package com.udemy.spring.cloud.oauth.service;

import java.util.List;
import java.util.stream.Collectors;

import com.udemy.spring.cloud.commons.model.auth.Role;
import com.udemy.spring.cloud.commons.model.auth.User;
import com.udemy.spring.cloud.oauth.client.IUserCloudClientFeign;
import com.udemy.spring.cloud.oauth.model.Roles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService, IUserService {

    private Logger log = LoggerFactory.getLogger(UserService.class);

    private static final String ENABLE = "ENABLE";
    private static final boolean ACCOUNT_NON_EXPIRED = true;
    private static final boolean CREDENTIALS_NON_EXPIRED = true;
    private static final boolean ACCOUNT_NON_LOCKED = true;

    @Autowired
    private IUserCloudClientFeign iUserCloudClientFeign;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);

        if (user == null) {
            String errorMessage = "User doesnt exist:" + username;
            log.error(errorMessage);
            throw new UsernameNotFoundException(errorMessage);
        }

        Roles roles = iUserCloudClientFeign.getRolesByUser(username);
        List<Role> lRoles = roles.get_embedded().getRoles();

        List<GrantedAuthority> authorities = lRoles
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .peek(authoritie -> log.info("Role: " + authoritie.getAuthority()))
                .collect(Collectors.toList());

        log.info("User authenticated:" + username);

        return new org.springframework.security.core.userdetails.User(
                username, 
                user.getPassword(),
                ENABLE.equals(user.getStatus()), 
                ACCOUNT_NON_EXPIRED, 
                CREDENTIALS_NON_EXPIRED, 
                ACCOUNT_NON_LOCKED,
                authorities);
    }

    @Override
    public User findByUsername(String username) {
        return iUserCloudClientFeign.findByUsername(username);
    }

    @Override
    public User update(User user, Long idUser) {
        return iUserCloudClientFeign.update(user, idUser);
    }

}
