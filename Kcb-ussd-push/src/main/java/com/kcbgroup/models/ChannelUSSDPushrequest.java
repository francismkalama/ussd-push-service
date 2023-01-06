package com.kcbgroup.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChannelUSSDPushrequest {
//    private String timeStamp;
    private String requestId;
    private String remoteAddress;
    private String msisdn;
    private String channelName;

}
