package com.user.service;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.user.model.CustomUserDetails;
import com.user.model.User;
import com.user.repository.UserRepository;
import com.user.util.CustomLogger;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

	Logger logger = CustomLogger.addLoggerClass(CustomUserDetailsService.class);

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("{} is trying to login", username);
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User Not Found");
		}

		return new CustomUserDetails(user);
	}

}
