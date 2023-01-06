package com.kcbgroup.utilities;


import jakarta.xml.bind.DatatypeConverter;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Builder
@Configuration
@Slf4j
public class USSDutility {

    public String getTimeStamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(new Date());
    }

    public String standardizeMSISDN( String phoneNumber ){
     
        if (phoneNumber != null && phoneNumber.length() > 0) {
            phoneNumber = removeSpeacialXters(phoneNumber);
            if (phoneNumber.length() >= 9)
                phoneNumber = "254" + phoneNumber.substring(phoneNumber.length() - 9);
            return phoneNumber;
        } else {
            return phoneNumber;
        }
    }

    public String removeSpeacialXters(String myString) {
        return myString.replaceAll("[^\\dA-Za-z ]", "");
    }

    public String encryptToBase(String username,String password, String timestamp) throws NoSuchAlgorithmException {
        String plainPassword = username+password+timestamp;
        String hashed256 = "";
        MessageDigest msgdigest = MessageDigest.getInstance("SHA-256");
        msgdigest.update(plainPassword.getBytes(StandardCharsets.UTF_8));
        byte[] digestedBytes = msgdigest.digest();
        hashed256 = DatatypeConverter.printHexBinary(digestedBytes);
        String hashedPasswordBase64 = Base64.encodeBase64String(hashed256.getBytes(StandardCharsets.UTF_8)).toUpperCase();
        return hashedPasswordBase64;
//        System.out.println("Password Hashed  "+hashedPasswordBase64);
        //System.out.println("hashed Password "+hashed256);
    }

    @Bean
    public RestTemplate restTemplate(){

        return new RestTemplate();
    }

}
