package com.user.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.user.dto.Gender;

import lombok.Data;

@Entity
@Table(name = "user_profile")
@Data
@TypeDef(name = "gender_enum_type", typeClass = GenderEnumType.class)
public class UserProfile {

	@Id
	@Column(name = "user_id")
	private Integer userId;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "mobile_number")
	private String mobileNumber;

	@Column(name = "date_of_birth")
	private Date dateOfBirth;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "gender")
	@Type(type = "gender_enum_type")
	private Gender gender;

	@Column(name = "aadhar_number")
	private String aadharNumber;

	/*
	 * https://thorben-janssen.com/hibernate-tips-same-primary-key-one-to-one-association/
	 */
	@OneToOne
	@JoinColumn(name = "user_id")
	@MapsId
	private User user;

}
