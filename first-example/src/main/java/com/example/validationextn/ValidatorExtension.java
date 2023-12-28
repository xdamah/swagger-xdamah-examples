package com.example.validationextn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.atlassian.oai.validator.interaction.request.CustomRequestValidator;
import com.atlassian.oai.validator.model.ApiOperation;
import com.atlassian.oai.validator.model.Body;
import com.atlassian.oai.validator.model.Request;
import com.atlassian.oai.validator.model.Request.Method;
import com.atlassian.oai.validator.report.ValidationReport;
import com.atlassian.oai.validator.report.ValidationReport.Message;
import com.atlassian.oai.validator.report.ValidationReport.MessageContext;
import com.example.custom.SampleCustomType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.TextNode;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.RequestBody;
import com.github.xdamah.config.IOpenApiValidationConfigOnInitWorkaround;
import com.github.xdamah.config.ModelPackageUtil;
import com.github.xdamah.constants.Constants;
import jakarta.annotation.Nonnull;

//this is just a democustome validator
//its verysimple only for demo purposes
//can be enhanced later for  non string types and container nodes- array node, object node
//when its base class is taking care of all  extension validation requirements can shift the base class to the core library


@Component
public class ValidatorExtension extends BaseValidatorExtension {

	private static final String CREDIT_CARD_EXTN="x-CreditCardNumber";
	private static final String EMAIL_EXTN="x-Email";
	
	@Override
	public void onInitInOpenApiValidationConfig()
	{
		registerCustomSchema(SampleCustomType.class.getSimpleName(), SampleCustomType.class.getName());
	}
	@Override
	protected String[] watchedExtensions()
	{
		return new String[]{CREDIT_CARD_EXTN, EMAIL_EXTN};
	}
	@Override
	protected  HashMap<String, IValidator> mapValidators() {
		HashMap<String, IValidator> hashMap = new HashMap<>();
		hashMap.put(CREDIT_CARD_EXTN, new RegexCardValidator());
		hashMap.put(EMAIL_EXTN, new SimpleEmailValidator());
		return hashMap;
	}
	


}
