package com.example.demo.kafka;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface CustomChannels {
	@Input("inboundOrgChanges")
	SubscribableChannel orgs();
}