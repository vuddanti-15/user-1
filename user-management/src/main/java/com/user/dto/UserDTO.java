package com.user.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.user.custom.annotation.validators.ValidAadharNumber;
import com.user.custom.annotation.validators.ValidDate;
import com.user.custom.annotation.validators.ValidEmail;
import com.user.custom.annotation.validators.ValidInternationalMobileNumber;
import com.user.util.UserUtil;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class UserDTO {

	@ValidEmail
	@NotBlank(message = "{notBlank.emailId}")
	@Setter(value = AccessLevel.NONE)
	private String emailId;

	@NotBlank(message = "{notBlank.firstname}")
	@Size(max = 50, message = "{size.firstname}")
	@Setter(value = AccessLevel.NONE)
	private String firstName;

	@Setter(value = AccessLevel.NONE)
	@Size(max = 50, message = "{size.lastname}")
	private String lastName;

	@NotBlank(message = "{notBlank.mobileNumber}")
	@Setter(value = AccessLevel.NONE)
	@ValidInternationalMobileNumber
	private String mobileNumber;

	@NotBlank(message = "{notBlank.date}")
	@ValidDate
	private String dateOfBirth;

	@NotNull
	private Gender gender;

	@NotBlank(message = "{notBlank.aadharNumber}")
	@ValidAadharNumber
	private String aadharNumber;

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = UserUtil.getTrimmedText(firstName);
	}

	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = UserUtil.getTrimmedText(emailId);
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = UserUtil.getTrimmedText(lastName);
	}

	/**
	 * @param mobileNumber the mobileNumber to set
	 */
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = UserUtil.getTrimmedText(mobileNumber);
	}

}
