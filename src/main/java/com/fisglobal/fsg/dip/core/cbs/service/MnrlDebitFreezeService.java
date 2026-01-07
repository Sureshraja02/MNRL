package com.fisglobal.fsg.dip.core.cbs.service;

import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fisglobal.fsg.dip.core.cbs.entity.DebitFreezeResponse;
import com.fisglobal.fsg.dip.core.service.CommonMethodUtils;
import com.fisglobal.fsg.dip.entity.MnrlCbsData_DAO;
import com.fisglobal.fsg.dip.entity.repo.MnrlCbsDataRepo;

@Service
public class MnrlDebitFreezeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MnrlDebitFreezeService.class);

	@Inject
	private DebitFreezeService debitFreezeService;
	
	@Value("${error.invalid.header:Invalid Header}")
	private String invalidHeader;
	
	@Inject
	private MnrlCbsDataRepo cbsDataRepo;
	
	@Inject
	private CommonMethodUtils commonMethodUtils;

	public MnrlCbsData_DAO processDebitFreeze(MnrlCbsData_DAO mnrlCbsEntity) throws KeyManagementException,
			ClientProtocolException, NoSuchAlgorithmException, KeyStoreException, JSONException, IOException {
		DebitFreezeResponse debitFreezeResp = new DebitFreezeResponse();
		BigInteger bigInt = new BigInteger(mnrlCbsEntity.getAccountNo());
		mnrlCbsEntity.setDebitFreezeDateTime(LocalDateTime.now());
		cbsDataRepo.save(mnrlCbsEntity);
		debitFreezeResp = debitFreezeService.processDebitFreeze(bigInt, "MNRL");
		mnrlCbsEntity.setDebitFreezeDateTime(LocalDateTime.now());
		/*
		 * Optional.ofNullable(debitFreezeResp.getDepositSetStopFrozenHandedOverResponse
		 * ()).ifPresent(m -> { if (m.getBody().getPayload().getStatus().equals("O.K."))
		 * { mnrlCbsEntity.setDebitFreezeFlag("C");; } else {
		 * mnrlCbsEntity.setDebitFreezeFlag("F"); mnrlCbsEntity.setActionFlag("2"); }
		 * });
		 * 
		 * Optional.ofNullable(debitFreezeResp.
		 * getDepositsSetStopFrozenHandedOverResponse()).ifPresent(m -> { if
		 * (m.getBody().getPayload().getStatus().equals("O.K.")) {
		 * mnrlCbsEntity.setDebitFreezeFlag("C");; } else {
		 * mnrlCbsEntity.setDebitFreezeFlag("F"); mnrlCbsEntity.setActionFlag("2"); }
		 * });
		 */

		/*
		 * if (debitFreezeResp != null) { /* if
		 * (debitFreezeResp.getDepositsSetStopFrozenHandedOverResponse().getBody().
		 * getPayload().getStatus().equals("O.K.")) {
		 * mnrlCbsEntity.setDebitFreezeFlag("C");; } else {
		 * mnrlCbsEntity.setDebitFreezeFlag("F"); mnrlCbsEntity.setActionFlag("2"); }
		 * 
		 * mnrlCbsEntity.setDebitFreezeFlag("C"); } else {
		 * mnrlCbsEntity.setDebitFreezeFlag("F"); mnrlCbsEntity.setActionFlag("2"); }
		 */

		if (!(Optional.ofNullable(debitFreezeResp.getDepositsSetStopFrozenHandedOverResponse()).isPresent()
				|| Optional.ofNullable(debitFreezeResp.getErrorResponse()).isPresent())) {
			mnrlCbsEntity.setDebitFreezeFlag("EX");
			// mnrlCbsEntity.setActionFlag("0");
		}

		Optional.ofNullable(debitFreezeResp.getDepositsSetStopFrozenHandedOverResponse()).ifPresent(m -> {
			Optional.ofNullable(m.getBody()).ifPresent(n -> {
				Optional.ofNullable(n.getPayload()).ifPresent(o -> {
					Optional.ofNullable(o.getStatus()).ifPresent(p -> {
						if (m.getBody().getPayload().getStatus().equals("O.K.")) {
							mnrlCbsEntity.setDebitFreezeFlag("C");
							mnrlCbsEntity.setDebitResponse(m.getBody().getPayload().getStatus());
							LOGGER.info("Debit status [{}]", m.getBody().getPayload().getStatus());
						}
					});

				});
			});
		});

		Optional.ofNullable(debitFreezeResp.getErrorResponse()).ifPresent(a -> {
			Optional.ofNullable(a.getAdditionalinfo()).ifPresent(b -> {
				if (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepText())
						&& a.getAdditionalinfo().getExcepText().equals(invalidHeader)) {
					LOGGER.info("Error [{}]",a.getAdditionalinfo().getExcepText());
					mnrlCbsEntity.setDebitFreezeFlag("EX");
					mnrlCbsEntity.setDebitResponse(a.getAdditionalinfo().getExcepText());

				} else {

					if (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepMetaData())) {
						mnrlCbsEntity.setDebitFreezeFlag("E");
						mnrlCbsEntity.setDebitResponse(a.getAdditionalinfo().getExcepMetaData());
						LOGGER.info("Debit EMetadata [{}]", a.getAdditionalinfo().getExcepMetaData());
					} else if (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepText())) {
						mnrlCbsEntity.setDebitFreezeFlag("E");
						mnrlCbsEntity.setDebitResponse(a.getAdditionalinfo().getExcepText());
						LOGGER.info("Debit EText [{}]", a.getAdditionalinfo().getExcepText());
					} else if (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepCode())) {
						mnrlCbsEntity.setDebitFreezeFlag("E");
						mnrlCbsEntity.setDebitResponse(a.getAdditionalinfo().getExcepCode());
						LOGGER.info("Debit Ecode [{}]", a.getAdditionalinfo().getExcepCode());
					}
				}
			});
		});

		LOGGER.info("debitFreezeFlag after response [{}]", mnrlCbsEntity.getDebitFreezeFlag());

		return mnrlCbsEntity;
	}

}
