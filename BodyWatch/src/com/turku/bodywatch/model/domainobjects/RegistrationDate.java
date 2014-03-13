package com.turku.bodywatch.model.domainobjects;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegistrationDate extends ValueObjectBase<Date> {
	
	private DateFormat dateFormat = SimpleDateFormat.getDateInstance();
	
	private final Date registrationDate;

	public RegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	@Override
	public Date getValue() {
		return registrationDate;
	}

	@Override
	public String toString() {
		return dateFormat.format(getValue());
	}
}
