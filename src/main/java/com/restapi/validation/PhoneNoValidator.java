package com.restapi.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class PhoneNoValidator implements 
ConstraintValidator<PhoneNoConstraint, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		PhoneNumber NumberProto = null;
		PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
		try {
			NumberProto = phoneUtil.parse(value, "IN");
			boolean isValid = phoneUtil.isValidNumber(NumberProto);
			return isValid;
		} catch (Exception e) {
			return false;
		} 
		
	}

}
