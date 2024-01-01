package com.example.custom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToSampleCustomTypeConverter implements Converter<String, SampleCustomType>{
	private static final Logger logger = LoggerFactory.getLogger(StringToSampleCustomTypeConverter.class);
	@Override
	public SampleCustomType convert(String source) {
		logger.debug("invoking "+this.getClass().getName());
		SampleCustomType rrr = new SampleCustomType();
	
		rrr.setData(source);
		return rrr;
	}

}
