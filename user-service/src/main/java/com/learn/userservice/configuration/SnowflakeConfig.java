package com.learn.userservice.configuration;

import com.learn.userservice.entity.Result;
import com.learn.userservice.util.SnowflakeIdGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SnowflakeConfig {
    @Value("${snowflake.workerId}")
    private long workerId;

    @Value("${snowflake.datacenterId}")
    private long datacenterId;

    @Bean
    public SnowflakeIdGenerator snowflakeIdGenerator() {
        return new SnowflakeIdGenerator(workerId, datacenterId);
    }

}
