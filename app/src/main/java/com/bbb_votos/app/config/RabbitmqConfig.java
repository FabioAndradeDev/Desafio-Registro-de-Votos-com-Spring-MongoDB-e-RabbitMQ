package com.bbb_votos.app.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {


    @Bean
    public Jackson2JsonMessageConverter convertJsonMessage(){
        return new Jackson2JsonMessageConverter();
    }
}