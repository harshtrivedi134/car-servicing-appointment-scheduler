package com.nielsen.interview.carservicingappointmentsystem.config;

import com.nielsen.interview.carservicingappointmentsystem.constants.JasyptConstants;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JasyptConfig {

    @Bean
    public StringEncryptor getPasswordEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(JasyptConstants.PASSWORD);
        config.setAlgorithm(JasyptConstants.ALGORITHM);
        config.setKeyObtentionIterations(JasyptConstants.KEY_OBTENTION_ITERATIONS);
        config.setPoolSize(JasyptConstants.POOL_SIZE);
        config.setProviderName(JasyptConstants.PROVIDER_NAME);
        config.setSaltGeneratorClassName(JasyptConstants.JASYPT_SALT_RANDOM_SALT_GENERATOR);
        config.setStringOutputType(JasyptConstants.OUTPUT_TYPE);
        encryptor.setConfig(config);
        return encryptor;
    }

}
