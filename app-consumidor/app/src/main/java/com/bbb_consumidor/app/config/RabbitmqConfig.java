package com.bbb_consumidor.app.config;


import ch.qos.logback.classic.pattern.MessageConverter;
import consts.RabbitmqConst;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.cbor.Jackson2CborDecoder;
import org.springframework.http.codec.json.Jackson2JsonDecoder;

@Configuration
public class RabbitmqConfig {


    @Bean
    public Jackson2JsonMessageConverter convertJsonMessage(){
        return new Jackson2JsonMessageConverter();
    }
}
