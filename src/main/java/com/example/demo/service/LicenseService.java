package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.example.demo.config.ServiceConfig;
import com.example.demo.model.License;
import com.example.demo.repository.LicenseRepository;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class LicenseService {
	@Autowired
	MessageSource messages;
	@Autowired
	private LicenseRepository licenseRepository;
	@Autowired
	ServiceConfig config;

	@CircuitBreaker(name = "licenseService", fallbackMethod = "buildFallbackLicenseList")
	@Retry(name = "retryLicenseService",fallbackMethod= "buildFallbackLicenseList")
	@Bulkhead(name= "bulkheadLicenseService", type = Bulkhead.Type.THREADPOOL,fallbackMethod= "buildFallbackLicenseList")
	@RateLimiter(name = "licenseService",fallbackMethod = "buildFallbackLicenseList")
	public License getLicense(String licenseId, String organizationId) {
		License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
		if (null == license) {
			throw new IllegalArgumentException(String.format(
					messages.getMessage("license.search.error.message", null, null), licenseId, organizationId));
		}

		return license.withComment(config.getProperty());
	}

	private List<License> buildFallbackLicenseList(String licenseId, String organizationId, Throwable t) {
		List<License> fallbackList = new ArrayList<>();
		License license = new License();
		license.setLicenseId("0000000-00-00000");
		license.setOrganizationId(organizationId);
		license.setProductName("Sorry no licensing information currently available");
		fallbackList.add(license);
		return fallbackList;
	}

	public License createLicense(License license) {
		license.setLicenseId(UUID.randomUUID().toString());
		licenseRepository.save(license);
		return license.withComment(config.getProperty());
	}

	public License updateLicense(License license) {
		licenseRepository.save(license);
		return license.withComment(config.getProperty());
	}

	public String deleteLicense(String licenseId) {
		String responseMessage = null;
		License license = new License();
		license.setLicenseId(licenseId);
		licenseRepository.delete(license);
		responseMessage = String.format(messages.getMessage("license.delete.message", null, null), licenseId);
		return responseMessage;
	}
}