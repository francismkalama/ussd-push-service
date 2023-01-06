package com.kcbgroup.configs;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("kcb-ussd-service")
public class NetworkInitiatedUSSDPushConfigs {
    private String initatorUsername;
    private String initatorPassword;
    private String callerUsername;
    private String callerPassword;
    private String commandid;
    private String resultUrl;
    private Integer  transactionMode;
    private String transactionType;
    }

