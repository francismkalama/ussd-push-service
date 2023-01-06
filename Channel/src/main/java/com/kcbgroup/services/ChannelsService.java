package com.kcbgroup.services;

import com.kcbgroup.models.Channel;
import com.kcbgroup.repositories.ChannelsRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Builder
@AllArgsConstructor
@Slf4j
public class ChannelsService {

    private final ChannelsRepository channelsRepository;

    public List<Channel> getAllregisteredServices() {
        return  channelsRepository.findAll();
    }

    public void createChannell(Channel channel) {
        //creating a new channel checks if channel exists and proceeds.

        channel.setChannelName(channel.getChannelName().replaceAll("[-+.^*:,]","").toLowerCase());
        channel.setChannelStatus(channel.getChannelStatus().replaceAll("[-+.^*:,]","").toLowerCase());

        Optional<Channel> channelOptional = channelsRepository.findChannelByChannelName(channel.getChannelName());

        if (channelOptional.isPresent()){
            throw new IllegalStateException("The Channel has already been registered");
        }
        System.out.println("Channel class Name check : "+channel.getClass().getTypeName());
        channelsRepository.save(channel);
    }
    public Integer getChannel(String channelName){
        //get and returns Channels Id to push service after search by name.
        Optional<Channel> channelOptional = channelsRepository.findChannelByChannelName(channelName);
        if (!channelOptional.isPresent()){
            throw new IllegalStateException("Channel not registered to send USSD Push");
        }
            String channelstatus = channelOptional.get().getChannelStatus();
            boolean chstatus = channelstatus.equals("active");
            if(!chstatus){
              throw new IllegalStateException("Channel not activated for USSD push");
            }
            System.out.println("channel Id: "+ channelOptional.get().getId());

        return channelOptional.get().getId();
    }
    public void deleteChannel(){
    }

    @Transactional
    public void updateRegisteredChannel(Integer channelId, String channelName, String channelStatus) {
    Channel regchannel = channelsRepository.findById(channelId).orElseThrow(()-> new IllegalStateException(
            "There is no channel with the supplied channel Id "+ channelId
    ));
    log.info("Saved channel on DB : {}",regchannel);

    if ((channelStatus != null && channelStatus.length() > 0 && !Objects.equals(regchannel.getChannelStatus(),channelStatus))){
        regchannel.setChannelStatus(channelStatus);
    }
    if(( channelName != null && channelStatus.length()>0 && !Objects.equals(regchannel.getChannelName(),channelName))){
        Optional<Channel> channelOptional = channelsRepository.findChannelByChannelName(channelName);
        if (channelOptional.isPresent()){
            throw new IllegalStateException("channel name already exist");
        }
        regchannel.setChannelName(channelName);
    }
    channelsRepository.save(regchannel);
    }
}
