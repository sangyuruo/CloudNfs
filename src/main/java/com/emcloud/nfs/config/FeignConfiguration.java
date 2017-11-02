package com.emcloud.nfs.config;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.emcloud.nfs")
public class FeignConfiguration {

}
