package com.kcbgroup.models;



import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Entity
@Data
@Configuration
@NoArgsConstructor
@Table(
        name = "ussdpushrequest",
        uniqueConstraints = {
                @UniqueConstraint(name = "ussdpush_request_unique",columnNames ="requestid")
        })
public class USSDPushrequest {
        @Id
        @SequenceGenerator(
                name = "ussdpushrequest_sequence",
                sequenceName = "ussdpushrequest_sequence",
                allocationSize = 1
        )
        @GeneratedValue(
                strategy = GenerationType.SEQUENCE,
                generator = "ussdpushrequest_sequence"
        )
        private Long Id;
        @Column(
                nullable = false
        )
        private String timestamp;
       @Transient
        private String mycompanyid;
        @Column(
                nullable = false
        )
        private String requestid;
        @Transient
        private String calleridentity_username;
        @Transient
        private String calleridentity_password;
        private String remoteaddress;
        @Transient
        private String initiatorusername;
        @Transient
        private String initiatorpassword;
        @Column(
                nullable = false
        )
        private String msisdn;
        private String transactiontype;
        private Integer transactionmode;
        @Column(
                nullable = false
        )
        private Integer channelid;
        @Transient
        private String requestUrl;
        private String ackStatus;
        private String responseCode;
        private String responseDesc;


}
