package io.github.shirohoo.batch.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableBatchProcessing
@EnableAutoConfiguration
@EnableTransactionManagement
@EntityScan("io.github.shirohoo.batch")
@EnableJpaRepositories("io.github.shirohoo.batch")
public class TestBatchConfiguration {

}
