package com.kcbgroup.controllers;

import com.kcbgroup.models.Channel;
import com.kcbgroup.services.ChannelsService;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Builder
@Slf4j
@RequestMapping("api/v1/channel")
public class ChannelsController {

    private final ChannelsService channelsService;

    @GetMapping("/registered")
    public List<Channel> getRegisteredchannels(){
       return channelsService.getAllregisteredServices();
    }

    @PostMapping("/register")
    public void addChannel(@RequestBody Channel channelrequest){
        log.info("Incoming Channel Registration request {}", channelrequest);
    channelsService.createChannell(channelrequest);
   // return "";
    }

    @GetMapping("/get_channel/{channelname}")
    public Integer getChannelId(@PathVariable("channelname") String channelname){
        log.info("----Channel Status enquiry for Channel => {}",channelname);
        return channelsService.getChannel(channelname);
    }

    @PutMapping("/updatechannel/{channelId}")
    public void updateChannel(
            @PathVariable("channelId") Integer channelId,
            @RequestParam(required = false) String channelName,
            @RequestParam(required = false) String channelStatus
            ){
        log.info("Update channel registration request : {} {} {} ", channelId,channelName,channelStatus);
        channelsService.updateRegisteredChannel(channelId, channelName,channelStatus);

    }
}
