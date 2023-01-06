package com.kcbgroup.controllers;


import com.kcbgroup.models.ChannelUSSDPushrequest;
import com.kcbgroup.models.USSDPushrequest;
import com.kcbgroup.services.USSDPushService;
import freemarker.template.TemplateException;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@Data
@Builder
@RestController
@RequestMapping("api/v1/ussd-push")
public class USSDPushController {

    private USSDPushService ussdpushService;

    @PostMapping("/sendout")
    public void processUSSDPush(@RequestBody ChannelUSSDPushrequest channelUssdPushrequest, USSDPushrequest ussdPushrequest) throws NoSuchAlgorithmException, TemplateException, IOException {
        ussdpushService.processUSSDPushrequest(channelUssdPushrequest, ussdPushrequest);
    }


}
