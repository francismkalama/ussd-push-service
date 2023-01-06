package com.kcbgroup.services;

import com.kcbgroup.configs.NetworkInitiatedUSSDPushConfigs;
import com.kcbgroup.models.ChannelResponse;
import com.kcbgroup.models.ChannelUSSDPushrequest;
import com.kcbgroup.models.USSDPushrequest;
import com.kcbgroup.repositories.NIUSSDPushRepository;
import com.kcbgroup.utilities.USSDutility;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Service
@Builder
@Slf4j
public class USSDPushService {

    private final Configuration freeMarkerConfigurations;

    USSDPushrequest ussdpushrequest;

    private final NetworkInitiatedUSSDPushConfigs ussdpushConfigs;

    private final USSDutility ussdutility;

    private final RestTemplate restTemplate;

    private NIUSSDPushRepository niussdPushRepository;


    public void processUSSDPushrequest(ChannelUSSDPushrequest channelUssdPushrequest, USSDPushrequest ussdpushrequest) throws NoSuchAlgorithmException, TemplateException, IOException {
        String channelName = channelUssdPushrequest.getChannelName();
        ChannelResponse channelDetails = restTemplate.getForObject("http://localhost:8089/api/v1/channel/get_channel/{channelname}", ChannelResponse.class,channelName);
        ussdpushrequest.setMsisdn(ussdutility.standardizeMSISDN(channelUssdPushrequest.getMsisdn()));
        ussdpushrequest.setRemoteaddress("192.1.1.15");
        ussdpushrequest.setRequestUrl(ussdpushConfigs.getResultUrl());
        ussdpushrequest.setRequestid(channelUssdPushrequest.getRequestId());
        ussdpushrequest.setTimestamp(ussdutility.getTimeStamp());
        ussdpushrequest.setRequestid(channelUssdPushrequest.getRequestId());
        ussdpushrequest.setMycompanyid(ussdpushConfigs.getCommandid());
        ussdpushrequest.setChannelid(channelDetails.getId());
        ussdpushrequest.setCalleridentity_password(ussdutility.encryptToBase(ussdpushConfigs.getCallerUsername(),ussdpushConfigs.getCallerPassword(),ussdutility.getTimeStamp()));
        ussdpushrequest.setInitiatorpassword(ussdutility.encryptToBase(ussdpushConfigs.getCallerUsername(),ussdpushConfigs.getCallerPassword(),ussdutility.getTimeStamp()));
        ussdpushrequest.setCalleridentity_username(ussdpushConfigs.getInitatorPassword());
        ussdpushrequest.setInitiatorusername(ussdpushConfigs.getInitatorUsername());
        ussdpushrequest.setTransactionmode(ussdpushConfigs.getTransactionMode());
        ussdpushrequest.setTransactiontype(ussdpushConfigs.getTransactionType());
        System.out.println("test : "+ussdpushrequest.getClass().getName());
        System.out.println("-------request send to db-------");
        niussdPushRepository.save(ussdpushrequest);
        System.out.println("-------saved to db-------");
        String soapXML =  createUSSDPushSoap(ussdpushrequest);

       //call safaricom endpoint for processing

    }

    public String createUSSDPushSoap(USSDPushrequest ussdPushrequest) throws IOException, TemplateException {
        log.info("---------------incoming USSD Push request ----------------");
        log.info("---------- USSD Push request details => {}", ussdPushrequest);
        Map ussdSoapdata = new HashMap<>();
        ussdSoapdata.put("timestamp",ussdPushrequest.getTimestamp());
        ussdSoapdata.put("remoteaddress",ussdPushrequest.getRemoteaddress());
        ussdSoapdata.put("mycompanyid",ussdPushrequest.getMycompanyid());
        ussdSoapdata.put("requestid",ussdPushrequest.getRequestid());
        ussdSoapdata.put("calleridentity_username",ussdPushrequest.getCalleridentity_username());
        ussdSoapdata.put("calleridentity_password",ussdPushrequest.getCalleridentity_password());
        ussdSoapdata.put("initiatorusername",ussdPushrequest.getInitiatorusername());
        ussdSoapdata.put("initiatorpassword",ussdPushrequest.getInitiatorpassword());
        ussdSoapdata.put("remoteaddress",ussdPushrequest.getRemoteaddress());
        ussdSoapdata.put("resultURL",ussdPushrequest.getRequestUrl());
        ussdSoapdata.put("msisdn",ussdPushrequest.getMsisdn());
        ussdSoapdata.put("resultURL",ussdPushrequest.getRequestUrl());
        ussdSoapdata.put("transactiontype",ussdPushrequest.getTransactiontype());
        ussdSoapdata.put("transactionmode",ussdPushrequest.getTransactionmode());
        Template template = getTemplate() ;
        String ussdSoaprequest = FreeMarkerTemplateUtils.processTemplateIntoString(template, ussdSoapdata);
        log.info("------------USSD Push Soap Message created successfully--------");
        return ussdSoaprequest;
    }

    public Template getTemplate() throws IOException {
        freeMarkerConfigurations.setClassForTemplateLoading(this.getClass(), "/templates");
        return freeMarkerConfigurations.getTemplate("networkinterfaceussdpushrequest.ftl");

    }
}
