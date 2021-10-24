package com.udemy.spring.cloud.gateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SpringSecurityConfig {

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public SecurityWebFilterChain configure(ServerHttpSecurity serverHttpSecurity) {
		return serverHttpSecurity.authorizeExchange()
				.pathMatchers("/gateway/api/security/**").permitAll()
				.pathMatchers(HttpMethod.GET, 
								"/gateway/api/producer/findAll",
								"/gateway/api/producer/findById/**",
								"/gateway/api/client/findAll",
								"/gateway/api/client/findByIdAmount/**",
								"/gateway/api/user/user-dao",
								"/gateway/api/user/user-dao/search/findByUsername",
								"/gateway/api/user/role-dao/search/getRolesByUser").permitAll()
				.pathMatchers(HttpMethod.GET, "/gateway/api/user/user-dao/search/find-id").hasAnyRole("ADMIN", "USER")
				.pathMatchers("/gateway/api/producer/**", 
								"/gateway/api/client/**", 
								"/gateway/api/user/**").hasRole("ADMIN")
				.anyExchange().authenticated()
				.and()
				.addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
				.csrf().disable()
				.build();
	}
	
}
