package com.example.validationextn;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SimpleEmailValidator implements IValidator{
	Pattern pattern = Pattern.compile("^(.+)@(\\S+)$");

	@Override
	public ValidationResult isValid(String string) {
		Matcher matcher = pattern.matcher(string);
		return new SimpleValidationResult(matcher.matches());
	}

}
