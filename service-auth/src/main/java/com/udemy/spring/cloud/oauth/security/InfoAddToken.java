package com.udemy.spring.cloud.oauth.security;

import java.util.HashMap;
import java.util.Map;

import com.udemy.spring.cloud.commons.model.auth.User;
import com.udemy.spring.cloud.oauth.service.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

@Component
public class InfoAddToken implements TokenEnhancer {

    @Autowired
    private IUserService iUserService;

    // Metodo para agregar informacion adicional en el token JWT
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> info = new HashMap<>();
        User user = iUserService.findByUsername(authentication.getName());
        info.put("name", user.getName());
        info.put("lastName", user.getLastName());
        info.put("email", user.getEmail());
        info.put("status", user.getStatus());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
        return accessToken;
    }
    
}
