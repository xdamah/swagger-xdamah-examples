package com.example.custom;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToSampleCustomTypeConverter implements Converter<String, SampleCustomType>{

	@Override
	public SampleCustomType convert(String source) {
		System.out.println("invoking "+this.getClass().getName());
		SampleCustomType rrr = new SampleCustomType();
	
		rrr.setData(source);
		return rrr;
	}

}
