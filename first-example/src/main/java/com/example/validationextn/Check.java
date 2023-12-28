package com.example.validationextn;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.LuhnCheckValidator;
//TODO remove this
public class Check {

	public static void main(String[] args) {
		
		EmailValidator vaidator= new EmailValidator();
		boolean valid = vaidator.isValid("abc@abc.com", null);
		System.out.println("valid="+valid);
		
		LuhnCheckValidator v= new LuhnCheckValidator();
		boolean valid2 = v.isValid("4444444444444448", null);
		System.out.println("valid2="+valid2);
	}

}
