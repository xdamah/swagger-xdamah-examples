package com.example.validationextn;

import io.github.xdamah.validatorextn.ValidationResult;

public class SimpleValidationResult implements ValidationResult {
	private boolean result;

	public SimpleValidationResult(boolean result) {
		super();
		this.result = result;
	}

	@Override
	public boolean isValid() {

		return result;
	}

}
