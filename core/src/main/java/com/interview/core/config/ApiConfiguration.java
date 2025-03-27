package com.interview.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "API Configuration", description = "Configuración de la API externa")
public @interface ApiConfiguration {

    @AttributeDefinition(name = "apiUrl", description = "URL de la API externa")
    String apiUrl() default "";

    @AttributeDefinition(name = "xRapidapiHost", description = "Host Api")
    String xRapidapiHost() default "";

    @AttributeDefinition(name = "xRapidapiKey", description = "Api Key")
    String xRapidapiKey() default "";

}


