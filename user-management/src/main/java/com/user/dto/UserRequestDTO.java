package com.user.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserRequestDTO extends UserDTO {

	private Integer id;

}
