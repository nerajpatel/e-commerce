package com.microshop.apigateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationClass {

    @Value("{kafka.topic}")
    private String topic;

}
