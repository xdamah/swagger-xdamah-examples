package com.example.custom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;



@Component
public class SampleCustomTypeTostringConverter implements Converter<SampleCustomType, String>{
	private static final Logger logger = LoggerFactory.getLogger(SampleCustomTypeTostringConverter.class);

	@Override
	public String convert(SampleCustomType source) {
		logger.debug("invoking "+this.getClass().getName());
		return source.getData();
	}

}
