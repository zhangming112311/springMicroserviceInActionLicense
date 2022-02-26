package com.example.demo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.model.Organization;




@FeignClient("organization-service")
public interface OrganizationFeignClient {
	
	@RequestMapping(method = RequestMethod.GET, value = "/v1/organization/{organizationId}", consumes = "application/json")
	Organization getOrganization(@PathVariable("organizationId") String organizationId);
}