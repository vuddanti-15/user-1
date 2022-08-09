package com.user.service;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.user.dto.UserRequestDTO;
import com.user.model.Role;
import com.user.model.User;
import com.user.model.UserProfile;
import com.user.repository.UserRepository;
import com.user.util.UserUtilComponent;

@Service
@Validated
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserUtilComponent userUtilComponent;

	public String validateAndSave(@Valid UserRequestDTO userRequestDTO) {
		User user = new User();
		BeanUtils.copyProperties(userRequestDTO, user);
		user.setUsername(userRequestDTO.getEmailId());
		user.setPassword(userUtilComponent.encodePassword("Sravan@07"));
		Role superAdminRole = userRepository.findSuperAdminRole(2);
		Set<Role> roles = new HashSet<Role>();
		roles.add(superAdminRole);
		user.setRoles(roles);
		UserProfile userProfile = new UserProfile();
		BeanUtils.copyProperties(userRequestDTO, userProfile);
		userProfile.setUser(user);
		userProfile.setDateOfBirth(userUtilComponent.convertStringToDate(userRequestDTO.getDateOfBirth()));
		user.setUserProfile(userProfile);
		userRepository.save(user);
		return "User Created Successfully";
	}

	public String exception(@Valid UserRequestDTO userRequestDTO) {
		User user = new User();
		user.setEmailId(null);
		user.getEmailId().substring(10);
		return "User Created Successfully";
	}

	public String success(@Valid UserRequestDTO userRequestDTO) {
		return "User Created Successfully";
	}

}
