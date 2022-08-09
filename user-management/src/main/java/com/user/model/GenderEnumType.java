package com.user.model;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.EnumType;

import com.user.dto.Gender;

public class GenderEnumType extends EnumType<Gender> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session)
			throws HibernateException, SQLException {
		st.setObject(index, value != null ? ((Gender) value).name() : null, Types.OTHER);
	}

}
