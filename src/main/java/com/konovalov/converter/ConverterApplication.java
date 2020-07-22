package com.konovalov.converter;

import okhttp3.OkHttpClient;
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
@PropertySource("classpath:config.properties")
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
}
