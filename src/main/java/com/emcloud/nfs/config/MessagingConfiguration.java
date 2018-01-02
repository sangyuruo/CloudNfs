package com.emcloud.nfs.config;

import com.emcloud.nfs.messaging.ConsumerChannel;
import com.emcloud.nfs.messaging.ProducerChannel;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;

@EnableBinding(value = {Source.class, ProducerChannel.class, ConsumerChannel.class})
public class MessagingConfiguration {
}

