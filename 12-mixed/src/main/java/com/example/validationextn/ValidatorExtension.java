package com.example.validationextn;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.example.custom.SampleCustomType;

import io.github.xdamah.validatorextn.BaseValidatorExtension;
import io.github.xdamah.validatorextn.IValidator;
import io.github.xdamah.validatorextn.RegexCardValidator;

//this is just a democustome validator
//its verysimple only for demo purposes
//can be enhanced later for  non string types and container nodes- array node, object node
//when its base class is taking care of all  extension validation requirements can shift the base class to the core library

@Component
public class ValidatorExtension extends BaseValidatorExtension {

	private static final String CREDIT_CARD_EXTN = "x-CreditCardNumber";
	private static final String EMAIL_EXTN = "x-Email";

	@Override
	public void onInitRegisterCustomSchemas() {
		registerCustomSchema(SampleCustomType.class.getSimpleName(), SampleCustomType.class.getName());
	}

	@Override
	protected String[] watchedExtensions() {
		return new String[] { CREDIT_CARD_EXTN, EMAIL_EXTN };
	}

	@Override
	protected HashMap<String, IValidator> mapValidators() {
		HashMap<String, IValidator> hashMap = new HashMap<>();
		hashMap.put(CREDIT_CARD_EXTN, new RegexCardValidator());
		hashMap.put(EMAIL_EXTN, new SimpleEmailValidator());
		return hashMap;
	}

}
