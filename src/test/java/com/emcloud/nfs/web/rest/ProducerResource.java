package com.emcloud.nfs.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emcloud.nfs.domain.Greeting;
import com.emcloud.nfs.messaging.ProducerChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  测试kafka 消息发送
 */
@RestController
@RequestMapping("/api")
public class ProducerResource {
    private MessageChannel channel;
    public ProducerResource(ProducerChannel channel) {
        this.channel = channel.messageChannel();
    }

    @GetMapping("/greetings/{count}")
    @Timed
    public void produce(@PathVariable int count) {
        while(count > 0) {
            channel.send(MessageBuilder.withPayload(new Greeting().setMessage("Hello world!: " + count)).build());
            count--;
        }
    }
}
