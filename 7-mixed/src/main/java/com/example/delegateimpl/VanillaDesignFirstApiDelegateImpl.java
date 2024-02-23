package com.example.delegateimpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.example.api.VanillaDesignFirstApiDelegate;
import com.example.model.Person;
@Component("vanillaDesignFirstApiDelegate")
public class VanillaDesignFirstApiDelegateImpl implements VanillaDesignFirstApiDelegate {

	@Override
	public ResponseEntity<Person> vanilladesignfirst1(Integer abc, Person body) {
		body.setId(abc.longValue());
		return new ResponseEntity<Person>(body, HttpStatus.OK);
	}

}
