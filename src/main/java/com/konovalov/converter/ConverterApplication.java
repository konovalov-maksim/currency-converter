package com.konovalov.converter;

import okhttp3.OkHttpClient;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EntityScan("com.konovalov.converter.entity")
@PropertySource(value = "classpath:config.properties", encoding = "UTF-8")
@EnableJpaRepositories("com.konovalov.converter.repository")
public class ConverterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConverterApplication.class, args);
    }

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
                .setMethodAccessLevel(Configuration.AccessLevel.PUBLIC);
        return mapper;
    }
}
