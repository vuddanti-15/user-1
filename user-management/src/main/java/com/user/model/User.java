package com.user.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "user_credential")
@Data
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5792956028407780637L;

	public User() {

	}

	private static final String SEQUENCE = "user_credentails_sequence";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE)
	@SequenceGenerator(name = SEQUENCE, sequenceName = SEQUENCE, allocationSize = 1)
	@Column(name = "user_id")
	private Integer id;

	@Column(name = "user_name", nullable = false, unique = true)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "email_id")
	private String emailId;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "user_role_mapping", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	/**** Removing optional = false will load userProfile object Too ****/
	@PrimaryKeyJoinColumn
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
	private UserProfile userProfile;

}
