package com.konovalov.converter.config;

import okhttp3.OkHttpClient;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.concurrent.TimeUnit;

@Configuration
public class CommonConfig {

    @Value("${http.readTimeout}")
    private long readTimeout;

    @Value("${http.writeTimeout}")
    private long writeTimeout;

    @Value("${http.connectionTimeout}")
    private long connectionTimeout;

    @Bean
    @Scope(BeanDefinition.SCOPE_SINGLETON)
    public OkHttpClient getClient() {
        return new OkHttpClient.Builder()
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                .connectTimeout(connectionTimeout, TimeUnit.SECONDS)
                .build();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setMethodAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PUBLIC);
        return mapper;
    }

}
