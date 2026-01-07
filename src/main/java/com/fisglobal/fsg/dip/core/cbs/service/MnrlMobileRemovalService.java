package com.fisglobal.fsg.dip.core.cbs.service;

import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.fisglobal.fsg.dip.core.cbs.entity.MobileRemovalResponseVo;
import com.fisglobal.fsg.dip.core.service.CommonMethodUtils;
import com.fisglobal.fsg.dip.entity.MnrlCbsData_DAO;
import com.fisglobal.fsg.dip.entity.repo.MnrlCbsDataRepo;

@Service
public class MnrlMobileRemovalService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MnrlMobileRemovalService.class);

	@Inject
	private MobileNumberRemovalService mobileNumberRemovalService;

	@Inject
	@Qualifier("MNRL_ATR_REQ")
	private MessageSource mnrlReq;

	@Inject
	private CommonMethodUtils commonMethodUtils;
	
	@Value("${error.invalid.header:Invalid Header}")
	private String invalidHeader;
	
	@Inject
	private MnrlCbsDataRepo cbsDataRepo;

	public MnrlCbsData_DAO processMobileRemoval(MnrlCbsData_DAO mnrlCbsEntity) throws KeyManagementException,
			ClientProtocolException, NoSuchAlgorithmException, KeyStoreException, JSONException, IOException {

		MobileRemovalResponseVo mobileRemovalResponse = new MobileRemovalResponseVo();

		BigInteger bigInt = new BigInteger(mnrlCbsEntity.getCif());

		String countryCode = commonMethodUtils.getCountryCode();

		String reason = mnrlReq.getMessage(mnrlCbsEntity.getMnrlReason() + ".mobileremovalreason", null, "others",
				Locale.US);
		mnrlCbsEntity.setMobileRemovalDateTime(LocalDateTime.now());
		cbsDataRepo.save(mnrlCbsEntity);
		
		mobileRemovalResponse = mobileNumberRemovalService.processMobileRemoval(bigInt, countryCode,
				commonMethodUtils.getMobileNumberWithoutCC(mnrlCbsEntity.getMobileNo()), "Y", reason, "MNRL");
		
		mnrlCbsEntity.setMobileRemovalDateTime(LocalDateTime.now());
		
		if (!(Optional.ofNullable(mobileRemovalResponse.getKYCUpdateResponse()).isPresent()
				|| Optional.ofNullable(mobileRemovalResponse.getErrorResponse()).isPresent())) {
			mnrlCbsEntity.setMobileRemovalFlag("EX");
		}

		Optional.ofNullable(mobileRemovalResponse.getKYCUpdateResponse()).ifPresent(m -> {
			Optional.ofNullable(m.getBody()).ifPresent(n -> {
				Optional.ofNullable(n.getPayload()).ifPresent(o -> {
					Optional.ofNullable(o.getStatus()).ifPresent(p -> {
						if (m.getBody().getPayload().getStatus().equals("O.K.")) {
							mnrlCbsEntity.setMobileRemovalFlag("C");
							mnrlCbsEntity.setMobileRemovalResponse(m.getBody().getPayload().getStatus());
							LOGGER.info("mobile removal status [{}]", m.getBody().getPayload().getStatus());
						}
					});

				});
			});
		});

		Optional.ofNullable(mobileRemovalResponse.getErrorResponse()).ifPresent(a -> {
			Optional.ofNullable(a.getAdditionalinfo()).ifPresent(b -> {
				if (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepText())
						&& a.getAdditionalinfo().getExcepText().equals(invalidHeader)) {
					LOGGER.info("Error [{}]", a.getAdditionalinfo().getExcepText());
					mnrlCbsEntity.setMobileRemovalFlag("EX");
					mnrlCbsEntity.setMobileRemovalResponse(a.getAdditionalinfo().getExcepText());

				} else {
					if (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepMetaData())) {
						mnrlCbsEntity.setMobileRemovalFlag("E");
						mnrlCbsEntity.setMobileRemovalResponse(a.getAdditionalinfo().getExcepMetaData());
						LOGGER.info("mobile removal EMetadata [{}]", a.getAdditionalinfo().getExcepMetaData());
					} else if (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepText())) {
						mnrlCbsEntity.setMobileRemovalFlag("E");
						mnrlCbsEntity.setMobileRemovalResponse(a.getAdditionalinfo().getExcepText());
						LOGGER.info("mobile removal Etext [{}]", a.getAdditionalinfo().getExcepText());
					} else if (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepCode())) {
						mnrlCbsEntity.setMobileRemovalFlag("E");
						mnrlCbsEntity.setMobileRemovalResponse(a.getAdditionalinfo().getExcepCode());
						LOGGER.info("mobile removal Ecode [{}]", a.getAdditionalinfo().getExcepCode());
					}
				}
			});
		});

		/*
		 * if (mobileRemovalResponse != null) { mnrlCbsEntity.setMobileRemovalFlag("C");
		 * /*if (mobileRemovalResponse.getKYCUpdateResponse().getBody().getPayload().
		 * getStatus().equals("O.K.")) { mnrlCbsEntity.setMobileRemovalFlag("C");
		 * 
		 * } else { mnrlCbsEntity.setMobileRemovalFlag("F");
		 * mnrlCbsEntity.setActionFlag("6"); } } else {
		 * mnrlCbsEntity.setMobileRemovalFlag("F"); mnrlCbsEntity.setActionFlag("6"); }
		 */

		LOGGER.info("MobileRemovalFlag after response [{}]", mnrlCbsEntity.getMobileRemovalFlag());

		return mnrlCbsEntity;
	}

}
