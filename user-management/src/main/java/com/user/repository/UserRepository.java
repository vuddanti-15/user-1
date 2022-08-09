package com.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.user.model.Role;
import com.user.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	User findByUsername(String username);

	@Query(value = "SELECT r FROM Role r where r.id=:id")
	Role findSuperAdminRole(@Param("id") Integer id);

}
