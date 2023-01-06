package com.kcbgroup.repositories;

import com.kcbgroup.models.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChannelsRepository extends JpaRepository<Channel,Integer> {

    Optional<Channel> findChannelByChannelName(String channelName);
}
