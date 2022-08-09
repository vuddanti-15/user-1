package com.user.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;

import com.user.configuration.RoleProperties;
import com.user.configuration.UrlPatternProperties;

@Component
public class RequestAntMatcher {

	@Autowired
	private UrlPatternProperties urlPatternProperties;

	@Autowired
	private RoleProperties roleProperties;

	void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers("/oauth/token").permitAll()
				.antMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
				.antMatchers(urlPatternProperties.getSuperadmin()).hasAuthority(roleProperties.getSuperadmin())
				.antMatchers(urlPatternProperties.getAdmin()).hasAnyAuthority(roleProperties.getAdmin())
				.antMatchers(urlPatternProperties.getUser()).hasAnyAuthority(roleProperties.getUser()).anyRequest()
				.authenticated().and().formLogin().disable();
	}

}
