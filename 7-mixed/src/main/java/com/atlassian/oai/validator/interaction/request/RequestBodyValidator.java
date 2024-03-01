package com.atlassian.oai.validator.interaction.request;

import static com.atlassian.oai.validator.report.ValidationReport.empty;
import static com.atlassian.oai.validator.util.ContentTypeUtils.findMostSpecificMatch;
import static com.atlassian.oai.validator.util.ContentTypeUtils.isFormDataContentType;
import static com.atlassian.oai.validator.util.ContentTypeUtils.isJsonContentType;
import static com.atlassian.oai.validator.util.HttpParsingUtils.parseUrlEncodedFormDataBodyAsJsonNode;
import static java.lang.Boolean.TRUE;
import static java.util.Objects.requireNonNull;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.function.Function;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;

import com.atlassian.oai.validator.model.Body;
import com.atlassian.oai.validator.model.Request;
import com.atlassian.oai.validator.report.MessageResolver;
import com.atlassian.oai.validator.report.ValidationReport;
import com.atlassian.oai.validator.report.ValidationReport.MessageContext;
import com.atlassian.oai.validator.schema.SchemaValidator;
import com.atlassian.oai.validator.springmvc.XdamahBody;
import com.fasterxml.jackson.databind.JsonNode;

import io.github.xdamah.config.NonSpringHolder;
import io.github.xdamah.util.ValidationReportInput;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.RequestBody;

/**
 * Validation for a request body.
 * <p>
 * The schema to validate is selected based on the content-type header of the
 * incoming request. This class is a mopdifiction of original class of same name
 * and package
 */
class RequestBodyValidator {

	private static final Logger log = getLogger(RequestBodyValidator.class);

	private final MessageResolver messages;

	private final SchemaValidator schemaValidator;

	RequestBodyValidator(@Nullable final MessageResolver messages, final SchemaValidator schemaValidator) {

		this.schemaValidator = requireNonNull(schemaValidator, "A schema validator is required");
		this.messages = messages == null ? new MessageResolver() : messages;
	}

	@Nonnull
	ValidationReport validateRequestBody(final Request request, @Nullable final RequestBody apiRequestBodyDefinition) {
		
		final Optional<Body> requestBody = request.getRequestBody();
		final boolean hasBody = requestBody.map(Body::hasBody).orElse(false);

		if (apiRequestBodyDefinition == null) {
			// A request body exists, but no request body is defined in the spec
			if (hasBody) {
				return ValidationReport.singleton(messages.get("validation.request.body.unexpected"));
			}

			// No request body exists, and no request body is defined in the spec. Nothing
			// to do.
			return empty();
		}

		ValidationReport.MessageContext context = ValidationReport.MessageContext.create()
				.withApiRequestBodyDefinition(apiRequestBodyDefinition).build();

		if (!hasBody) {
			// No request body, but is required in the spec
			if (TRUE.equals(apiRequestBodyDefinition.getRequired())) {
				return ValidationReport.singleton(messages.get("validation.request.body.missing"))
						.withAdditionalContext(context);
			}

			// No request body, and isn't required. Nothing to do.
			return empty();
		}

		final Optional<Pair<String, MediaType>> maybeApiMediaTypeForRequest = findApiMediaTypeForRequest(request,
				apiRequestBodyDefinition);

		// No matching media type found. Validation of mismatched content-type is
		// handled elsewhere. Nothing to do.
		if (!maybeApiMediaTypeForRequest.isPresent()) {
			return empty();
		}

		context = ValidationReport.MessageContext.from(context)
				.withMatchedApiContentType(maybeApiMediaTypeForRequest.get().getLeft()).build();
		String contentType = request.getContentType().get();

		ValidationReportInput validationReportInput = new ValidationReportInput(apiRequestBodyDefinition, context,
				maybeApiMediaTypeForRequest,  contentType, 
				requestBody);
		if (isJsonContentType(request)) {
			//later when we reread
			//ValidationReport report = process(validationReportInput, this::originalJson);
			ValidationReport report =originalJson(validationReportInput);
			if(report!=null)
			{
				return report;
			}
		}

		if (isFormDataContentType(request)) {
			ValidationReport report = process(validationReportInput, this::originalForm);
			if(report!=null)
			{
				return report;
			}
		}

		if (request.getContentType().isPresent()) {
			
			
			// unnecessary nul check but
			if (contentType != null) {
				contentType = contentType.toLowerCase();
				if (contentType.equals(org.springframework.http.MediaType.APPLICATION_XML_VALUE)) {
					//later when we reread
					//ValidationReport report = process(validationReportInput, this::originalXml);
					ValidationReport report =originalXml(validationReportInput);
					if(report!=null)
					{
						return report;
					}

					
				}
				else if(contentType.startsWith(org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE))
				{
					ValidationReport report = process(validationReportInput, null);
					if(report!=null)
					{
						return report;
					}
					
					
					
				}
			}
		}

	

		log.info("Validation of '{}' not supported. Request body not validated.",
				maybeApiMediaTypeForRequest.get().getLeft());
		return empty();
	}

	private ValidationReport originalForm(ValidationReportInput validationReportInput) {
		return schemaValidator
		            .validate(() -> parseUrlEncodedFormDataBodyAsJsonNode(validationReportInput.getRequestBody().get().toString(StandardCharsets.UTF_8)),
		            		validationReportInput.getMaybeApiMediaTypeForRequest().get().getRight().getSchema(),
		                    "request.body")
		            .withAdditionalContext(validationReportInput.getContext());
	}

	private ValidationReport originalJson(ValidationReportInput validationReportInput) {
		return schemaValidator
		        .validate(() -> validationReportInput.getRequestBody().get().toJsonNode(),
		        		validationReportInput.getMaybeApiMediaTypeForRequest().get().getRight().getSchema(),
		                "request.body")
		        .withAdditionalContext(validationReportInput.getContext());
	}
	
	
	
	private ValidationReport process(ValidationReportInput validationReportInput,
			Function<ValidationReportInput, ValidationReport> fn) {
		ValidationReport report = null;
		if(validationReportInput.getRequestBody().isPresent())
		{
			Body body = validationReportInput.getRequestBody().get();
			if(body.hasBody())
			{
				MessageContext context = validationReportInput.getContext();
				Optional<Pair<String, MediaType>> maybeApiMediaTypeForRequest = validationReportInput.getMaybeApiMediaTypeForRequest();
				if(body instanceof XdamahBody )
				{
					
					XdamahBody xdamahBody=(com.atlassian.oai.validator.springmvc.XdamahBody) body;
					report = extracted(xdamahBody, context, maybeApiMediaTypeForRequest);
					
				}
				else
				{
					if(fn!=null)
					{
						report = fn.apply(validationReportInput);
					}
				}
			}
		}
	
		return report;
	}

	private ValidationReport originalXml(ValidationReportInput validationReportInput) {
		String xml = null;
		try {
			xml = validationReportInput.getRequestBody().get().toString(Charset.defaultCharset());
		} catch (IOException e) {
			log.error("Unable to extract xml", e);
		}

		JsonNode readValue = NonSpringHolder.INSTANCE.xmlToJsonNode(validationReportInput.getApiRequestBodyDefinition(),
				validationReportInput.getContentType(), xml);
		Schema schema = validationReportInput.getMaybeApiMediaTypeForRequest().get().getRight().getSchema();
		System.out.println("schema="+schema.getName()+",ref="+schema.get$ref());
		return schemaValidator.validate(() -> readValue,
				schema, "request.body")
				.withAdditionalContext(validationReportInput.getContext());
	}

	
	
	

	private ValidationReport extracted(XdamahBody xdamahBody, ValidationReport.MessageContext context,
			final Optional<Pair<String, MediaType>> maybeApiMediaTypeForRequest) {
		ValidationReport report=null;
		JsonNode x;
		try {
			x = xdamahBody.toJsonNode();
			Schema schema = maybeApiMediaTypeForRequest.get().getRight().getSchema();
		
			report= schemaValidator
					.validate(
							() -> x,
							schema, "request.body")
					.withAdditionalContext(context);
		} catch (IOException e) {
			log.error("Unable to extract json", e);
		}
		return report;
	}

	private Optional<Pair<String, MediaType>> findApiMediaTypeForRequest(final Request request,
			@Nullable final RequestBody apiRequestBodyDefinition) {
		return Optional.ofNullable(apiRequestBodyDefinition).map(RequestBody::getContent)
				.flatMap(content -> findMostSpecificMatch(request, content.keySet())
						.map(mostSpecificMatch -> Pair.of(mostSpecificMatch, content.get(mostSpecificMatch))));
	}

}
