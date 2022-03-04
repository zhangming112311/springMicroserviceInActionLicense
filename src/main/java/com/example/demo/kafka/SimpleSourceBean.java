package com.example.demo.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.example.demo.model.License;

@Component
public class SimpleSourceBean {
	private Source source;
	private static final Logger logger = LoggerFactory.getLogger(SimpleSourceBean.class);

	public SimpleSourceBean(Source source) {
		this.source = source;
	}

	public void publishOrganizationChange(
//			ActionEnum action, 
			String organizationId) {
//		logger.debug("Sending Kafka message {} for Organization Id: {}", action, organizationId);
//		OrganizationChangeModel change = new OrganizationChangeModel(OrganizationChangeModel.class.getTypeName(),
//				action.toString(), organizationId, UserContext.getCorrelationId());
		source.output().send(MessageBuilder.withPayload(new License()).build());
	}
}