package org.springdoc.webmvc.api;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import org.springdoc.core.AbstractRequestService;
import org.springdoc.core.ActuatorProvider;
import org.springdoc.core.GenericResponseService;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.OpenAPIService;
import org.springdoc.core.OperationService;
import org.springdoc.core.RepositoryRestResourceProvider;
import org.springdoc.core.SecurityOAuth2Provider;
import org.springdoc.core.SpringDocConfigProperties;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;

import static org.springdoc.core.Constants.APPLICATION_OPENAPI_YAML;
import static org.springdoc.core.Constants.DEFAULT_API_DOCS_ACTUATOR_URL;
import static org.springframework.util.AntPathMatcher.DEFAULT_PATH_SEPARATOR;

@RestControllerEndpoint(id = DEFAULT_API_DOCS_ACTUATOR_URL)
public class MultipleOpenApiActuatorResource extends MultipleOpenApiResource {

	/**
	 * Instantiates a new Multiple open api resource.
	 *  @param groupedOpenApis the grouped open apis
	 * @param defaultOpenAPIBuilder the default open api builder
	 * @param requestBuilder the request builder
	 * @param responseBuilder the response builder
	 * @param operationParser the operation parser
	 * @param requestMappingHandlerMapping the request mapping handler mapping
	 * @param actuatorProvider the actuator provider
	 * @param springDocConfigProperties the spring doc config properties
	 * @param springSecurityOAuth2Provider the spring security o auth 2 provider
	 * @param routerFunctionProvider the router function provider
	 * @param repositoryRestResourceProvider the repository rest resource provider
	 */
	public MultipleOpenApiActuatorResource(List<GroupedOpenApi> groupedOpenApis,
			ObjectFactory<OpenAPIService> defaultOpenAPIBuilder, AbstractRequestService requestBuilder,
			GenericResponseService responseBuilder, OperationService operationParser,
			RequestMappingInfoHandlerMapping requestMappingHandlerMapping, Optional<ActuatorProvider> actuatorProvider,
			SpringDocConfigProperties springDocConfigProperties, Optional<SecurityOAuth2Provider> springSecurityOAuth2Provider,
			Optional<RouterFunctionProvider> routerFunctionProvider, Optional<RepositoryRestResourceProvider> repositoryRestResourceProvider) {
		super(groupedOpenApis, defaultOpenAPIBuilder, requestBuilder, responseBuilder, operationParser, requestMappingHandlerMapping, actuatorProvider, springDocConfigProperties, springSecurityOAuth2Provider, routerFunctionProvider, repositoryRestResourceProvider);
	}


	/**
	 * Openapi json string.
	 *
	 * @param request the request
	 * @param group the group
	 * @return the string
	 * @throws JsonProcessingException the json processing exception
	 */
	@Operation(hidden = true)
	@GetMapping(value =   "/{group}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String openapiJson(HttpServletRequest request,
			@PathVariable String group)
			throws JsonProcessingException {
		return getOpenApiResourceOrThrow(group).openapiJson(request, "" + DEFAULT_PATH_SEPARATOR + group);
	}

	/**
	 * Openapi yaml string.
	 *
	 * @param request the request
	 * @param group the group
	 * @return the string
	 * @throws JsonProcessingException the json processing exception
	 */
	@Operation(hidden = true)
	@GetMapping(value =  "/{group}/yaml", produces = APPLICATION_OPENAPI_YAML)
	public String openapiYaml(HttpServletRequest request,
			@PathVariable String group)
			throws JsonProcessingException {
		return getOpenApiResourceOrThrow(group).openapiYaml(request, "" + DEFAULT_PATH_SEPARATOR + group);
	}

}
