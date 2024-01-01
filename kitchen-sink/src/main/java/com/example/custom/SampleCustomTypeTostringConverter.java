package com.example.custom;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SampleCustomTypeTostringConverter implements Converter<SampleCustomType, String>{

	@Override
	public String convert(SampleCustomType source) {
		System.out.println("invoking "+this.getClass().getName());
		return source.getData();
	}

}
