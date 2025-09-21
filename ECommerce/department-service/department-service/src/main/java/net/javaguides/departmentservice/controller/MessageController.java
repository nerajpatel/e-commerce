package net.javaguides.departmentservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/message")
@RefreshScope //force to reload the configuration for this bean
public class MessageController {

    @Value("${spring.boot.message}")
    private String value;

    @GetMapping("value")
    public String message(){
        return value;
    }
}
