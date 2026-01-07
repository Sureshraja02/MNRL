package com.fisglobal.fsg.dip.core.controller;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fisglobal.fsg.dip.core.utils.DataProcessingException;
import com.fisglobal.fsg.dip.security.SpringConfig;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "UserSecureTermEnc Controller", description = "UserSecureTermEnc Controller")
@RequestMapping(value = "/IB/app/rest/v1.0/ccss/", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserSecureTermEncController {
	private final Logger LOGGER = LoggerFactory.getLogger(UserSecureTermEncController.class);

	
	PasswordEncoder passwordEncoder = null;

	@Operation(summary = "UserSecureTermEncController Controller")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) }),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })

	@RequestMapping(value = "securetermenc", method = RequestMethod.POST)
	public ResponseEntity<?> UserSecureTermMethod(@RequestBody String encSecureTerm)
			throws DataProcessingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, IOException {
		LOGGER.info("::::::::::::::::::::::::::::UserSecureTermEncController Called::::::::::::::::::::::::::::");
		if (StringUtils.isNotBlank(encSecureTerm)) {
			LOGGER.info("Clear value - encSecureTerm [{}]",encSecureTerm);
			encSecureTerm = SpringConfig.passwordEncoderStatic.encode(encSecureTerm);
			LOGGER.info("Encrypted / Hasing value - encSecureTerm [{}]",encSecureTerm);
			return new ResponseEntity<>(encSecureTerm, HttpStatus.OK);
		
		} else {
			LOGGER.info("Clear value - encSecureTerm [{}] Received, so Bad Request",encSecureTerm);
			return new ResponseEntity<>(encSecureTerm, HttpStatus.BAD_REQUEST);
		}
	}
}
