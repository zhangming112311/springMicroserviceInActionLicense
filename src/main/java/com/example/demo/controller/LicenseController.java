package com.example.demo.controller;

import java.util.Locale;

import javax.annotation.security.RolesAllowed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.filter.UserContextHolder;
import com.example.demo.model.License;
import com.example.demo.service.LicenseService;

@RestController
@RequestMapping(value = "v1/organization/{organizationId}/license")
public class LicenseController {
	@Autowired
	private LicenseService licenseService;
	@Autowired
	MessageSource messages;
	private static final Logger logger =
			LoggerFactory.getLogger(LicenseController.class);

	@RequestMapping(value = "/{licenseId}", method = RequestMethod.GET)
	public ResponseEntity<License> getLicense(@PathVariable("organizationId") String organizationId,
			@PathVariable("licenseId") String licenseId) {
		logger.debug("LicenseServiceController Correlation id: {}",
				UserContextHolder.getContext().getCorrelationId());
		License license = licenseService.getLicense(licenseId, organizationId);
		return ResponseEntity.ok(license);
	}

	@PutMapping
	public ResponseEntity<License> updateLicense(@PathVariable("organizationId") String organizationId,
			@RequestBody License request) {
		return ResponseEntity.ok(licenseService.updateLicense(request));
	}

	@PostMapping
	public ResponseEntity<License> createLicense(@PathVariable("organizationId") String organizationId,
			@RequestBody License request,@RequestHeader(value = "Accept-Language",required = false) Locale locale) {
		return ResponseEntity.ok(licenseService.createLicense(request));
	}
	@RolesAllowed({ "ADMIN", "USER" })
	@DeleteMapping(value = "/{licenseId}")
	public ResponseEntity<String> deleteLicense(@PathVariable("organizationId") String organizationId,
			@PathVariable("licenseId") String licenseId) {
		return ResponseEntity.ok(licenseService.deleteLicense(licenseId));
	}
	
	@PostMapping(value = "test")
	public ResponseEntity<String> createLicense(@RequestHeader(value = "Accept-Language",required = false) Locale locale) {
		return ResponseEntity.ok(String.format(messages.getMessage("license.delete.message", null, locale)));
	}
}
