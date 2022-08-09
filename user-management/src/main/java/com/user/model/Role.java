package com.user.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import lombok.Data;

@Entity
@Table(name = "role")
@Data
@Immutable
public class Role implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4914812966269259417L;
	
	private static final String SEQUENCE = "role_sequence";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE)
	@SequenceGenerator(name = SEQUENCE, sequenceName = SEQUENCE, allocationSize = 1)
	@Column(name = "role_id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "name", unique = true, nullable = false, length = 50)
	private String name;

}
