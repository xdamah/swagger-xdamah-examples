package com.example.delegateimpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.api.DesignfirstApiDelegate;
import com.example.model.Person;

public class DesignfirstApiDelegateImpl implements DesignfirstApiDelegate {

	@Override
	public ResponseEntity<Person> another3(Integer abc, Person body1) {
		body1.setId(abc.longValue());
		return new ResponseEntity<Person>(body1, HttpStatus.OK);
	}

}
