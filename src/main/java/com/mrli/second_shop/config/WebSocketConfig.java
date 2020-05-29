package com.mrli.second_shop.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import java.io.Serializable;

@Configuration
public class WebSocketConfig implements Serializable {

    private static final long serialVersionUID = 7600559593733357846L;

//    @Bean
//    public ServerEndpointExporter serverEndpointExporter() {
//        return new ServerEndpointExporter();
//    }
}
