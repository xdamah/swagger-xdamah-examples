package com.example.validationextn;

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
