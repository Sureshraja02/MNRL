package com.fisglobal.fsg.dip.core.cbs.service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fisglobal.fsg.dip.core.cbs.entity.MobileEnquiryResponseVo;
import com.fisglobal.fsg.dip.core.service.CommonMethodUtils;
import com.fisglobal.fsg.dip.entity.MnrlCbsData_DAO;
import com.fisglobal.fsg.dip.entity.MnrlData_DAO;
import com.fisglobal.fsg.dip.entity.repo.MnrlCbsDataRepo;
import com.fisglobal.fsg.dip.entity.repo.MnrlDataRepo;

@Service
public class MnrlAsyncService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MnrlAsyncService.class);

	@Inject
	private AccountService accountService;

	@Inject
	private MnrlDataRepo dataRepo;

	@Inject
	private MnrlCbsDataRepo cbsDataRepo;

	@Inject
	private MobileNumberEnquiryService mobileNumberEnquiryService;

	@Inject
	private UpdateFlagService updateFlagService;

	public HashMap<String, String> cifBlockMap = new HashMap<String, String>();

	public HashMap<String, String> atrMap = new HashMap<String, String>();

	@Inject
	private CommonMethodUtils commonMethodUtils;

	@Value("${error.invalid.header:Invalid Header}")
	private String invalidHeader;

	@Async("mnrl-mobile-enquiry")
	public CompletableFuture<MnrlCbsData_DAO> mobileEnquiry(MnrlData_DAO mnrlentity) throws KeyManagementException,
			ClientProtocolException, JSONException, NoSuchAlgorithmException, KeyStoreException, IOException {
		try {
			long start = System.currentTimeMillis();

			MnrlCbsData_DAO totalCbsData = new MnrlCbsData_DAO();
			List<MnrlCbsData_DAO> cbsDataList = new ArrayList<>();
			MobileEnquiryResponseVo mobileEnquiryResponseVo = new MobileEnquiryResponseVo();
			String countryCode = commonMethodUtils.getCountryCode();

			boolean defaultFlag = true;
			boolean mobileRemovalFlag = true;
			LOGGER.info("Mobile Enquiry [{}]", mnrlentity);
			 //MnrlCbsData_DAO mnrlCbsDataEntity = new MnrlCbsData_DAO();
			mnrlentity.setMobileEnquiryDateTime(LocalDateTime.now());
			dataRepo.save(mnrlentity);
			mnrlentity.setProcessFlag("Y");
			String mobile = commonMethodUtils.getMobileNumberWithoutCC(mnrlentity.getMobileNo());
			long beforeReq = System.currentTimeMillis();
			mobileEnquiryResponseVo = mobileNumberEnquiryService.getCIFAccountDetails(mobile, countryCode, "MNRL");
			long afterReq = System.currentTimeMillis();
			long elapsedReqRes = afterReq - beforeReq;
			LOGGER.info("Req Res elapsed in seconds [{}]", TimeUnit.MILLISECONDS.toSeconds(elapsedReqRes));
			// LOGGER.info("Flag [{}]", mobileEnquiryResponseVo.getFlag());

			long before = System.currentTimeMillis();
			
			mnrlentity.setMobileEnquiryDateTime(LocalDateTime.now());
			if (!(Optional.ofNullable(mobileEnquiryResponseVo.getCIFAccountEnqResponse()).isPresent()
					|| Optional.ofNullable(mobileEnquiryResponseVo.getErrorResponse()).isPresent())) {
				mnrlentity.setProcessFlag("N");
				dataRepo.save(mnrlentity);
				// mnrlCbsEntity.setActionFlag("0");
			}

			Optional.ofNullable(mobileEnquiryResponseVo.getCIFAccountEnqResponse()).ifPresent(m -> {
				Optional.ofNullable(m.getBody()).ifPresent(n -> {
					Optional.ofNullable(n.getPayload()).ifPresent(o -> {
						Optional.ofNullable(o.getCollection()).ifPresent(p -> {
							mnrlentity.setMobileEnquiryResponse(null);
							if (m.getBody().getPayload().getCollection().getCIFNo().size() > 0) {
								List<String> cif = m.getBody().getPayload().getCollection().getCIFNo();
								for (int i = 0; i < cif.size(); i++) {
									MnrlCbsData_DAO mnrlCbscifEntity = new MnrlCbsData_DAO();
									mnrlCbscifEntity.setUuid(commonMethodUtils.getUuid());
									mnrlCbscifEntity.setDataUuid(mnrlentity.getUuid());
									mnrlCbscifEntity.setMobileNo(mnrlentity.getMobileNo());
									mnrlCbscifEntity.setCif(cif.get(i));
									// mnrlCbscifEntity.setCifEnquiryFlag("2");
									mnrlCbscifEntity.setAccountNo("777777777777");
									mnrlCbscifEntity.setProcessFlag("0");
									mnrlCbscifEntity.setMnrlReason(mnrlentity.getDisconnectingReasonId());
									//mnrlCbscifEntity = updateFlagService.setFlags(mnrlCbscifEntity, defaultFlag,mobileRemovalFlag);
									mnrlCbscifEntity = updateFlagService.setFlags(mnrlCbscifEntity,mobileRemovalFlag);
									mnrlCbscifEntity.setActionFlag("0");
									
									LOGGER.info("CIF Data [{}]", mnrlCbscifEntity.toString());
									//cbsDataList.add(mnrlCbscifEntity);
									 cbsDataRepo.save(mnrlCbscifEntity);

								}
							}
							// cbsDataRepo.saveAll(cbsDataList);
							if (m.getBody().getPayload().getCollection().getAccountNo().size() > 0) {
								List<String> acc = m.getBody().getPayload().getCollection().getAccountNo();
								for (int i = 0; i < acc.size(); i++) {
									MnrlCbsData_DAO mnrlCbsEntity = new MnrlCbsData_DAO();
									mnrlCbsEntity.setUuid(commonMethodUtils.getUuid());
									mnrlCbsEntity.setDataUuid(mnrlentity.getUuid());
									mnrlCbsEntity.setMobileNo(mnrlentity.getMobileNo());
									mnrlCbsEntity.setAccountNo(acc.get(i));
									mnrlCbsEntity.setCif("777777777777");
									// mnrlCbsEntity.setAccEnquiryFlag("1");
									mnrlCbsEntity.setProcessFlag("0");
									mnrlCbsEntity.setMnrlReason(mnrlentity.getDisconnectingReasonId());
									//mnrlCbsEntity = updateFlagService.setFlags(mnrlCbsEntity, defaultFlag,mobileRemovalFlag);
									mnrlCbsEntity = updateFlagService.setFlags(mnrlCbsEntity, mobileRemovalFlag);
									mnrlCbsEntity.setActionFlag("0");
									
									LOGGER.info("Account Data [{}]", mnrlCbsEntity.toString());
									//cbsDataList.add(mnrlCbsEntity);
									 cbsDataRepo.save(mnrlCbsEntity);
								}
							}
							// cbsDataList.add(mnrlCbsEntity);
							long listStart = System.currentTimeMillis();
							//cbsDataRepo.saveAll(cbsDataList);
							dataRepo.save(mnrlentity);
							long listend = System.currentTimeMillis();
							long dbElapsed = listend - listStart;
							LOGGER.info("Data Base elapsed in seconds [{}]",
									TimeUnit.MILLISECONDS.toSeconds(dbElapsed));

							/*
							 * for (int i = 0; i < cbsDataList.size(); i++) {
							 * 
							 * totalCbsData.setMobileNo(cbsDataList.get(i).getMobileNo());
							 * totalCbsData.setCif(cbsDataList.get(i).getCif());
							 * totalCbsData.setAccountNo(cbsDataList.get(i).getAccountNo()); }
							 */

							long after = System.currentTimeMillis();
							long ifblockElapsed = after - before;
							LOGGER.info("loop elapsed in seconds [{}]",
									TimeUnit.MILLISECONDS.toSeconds(ifblockElapsed));

						});

					});
				});
			});

			Optional.ofNullable(mobileEnquiryResponseVo.getErrorResponse()).ifPresent(a -> {
				Optional.ofNullable(a.getAdditionalinfo()).ifPresent(b -> {
					if (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepText())
							&& a.getAdditionalinfo().getExcepText().equals(invalidHeader)) {
						LOGGER.info("Error [{}]", a.getAdditionalinfo().getExcepText());
						mnrlentity.setProcessFlag("N");
						mnrlentity.setMobileEnquiryResponse(a.getAdditionalinfo().getExcepText());
					} else {

						if (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepMetaData())) {
							// mnrlentity.setProcessFlag("Y");
							mnrlentity.setMobileEnquiryResponse(a.getAdditionalinfo().getExcepMetaData());
							LOGGER.info("Mobile Enquiry EMetadata [{}]", a.getAdditionalinfo().getExcepMetaData());
						} else if (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepText())) {
							// mnrlentity.setProcessFlag("Y");
							mnrlentity.setMobileEnquiryResponse(a.getAdditionalinfo().getExcepText());
							LOGGER.info("Mobile Enquiry EText [{}]", a.getAdditionalinfo().getExcepText());
						} else if (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepCode())) {
							// mnrlentity.setProcessFlag("Y");
							mnrlentity.setMobileEnquiryResponse(a.getAdditionalinfo().getExcepCode());
							LOGGER.info("Mobile Enquiry Ecode [{}]", a.getAdditionalinfo().getExcepCode());
						}
					}
				});
				dataRepo.save(mnrlentity);
			});

			if (StringUtils.isNotBlank(mobileEnquiryResponseVo.getErrorMessage())) {
				LOGGER.info("Mobile Enquiry error message [{}]", mobileEnquiryResponseVo.getErrorMessage());
				mnrlentity.setProcessFlag("Y");
				mnrlentity.setMobileEnquiryResponse(mobileEnquiryResponseVo.getErrorMessage());
				dataRepo.save(mnrlentity);
			}
			/*
			 * if (mobileEnquiryResponseVo != null &&
			 * mobileEnquiryResponseVo.getFlag().equals("N")) {
			 * 
			 * if
			 * (mobileEnquiryResponseVo.getCIFAccountEnqResponse().getBody().getPayload().
			 * getCollection().getCIFNo() .size() > 0) { List<String> cif =
			 * mobileEnquiryResponseVo.getCIFAccountEnqResponse().getBody().getPayload()
			 * .getCollection().getCIFNo(); for (int i = 0; i < cif.size(); i++) {
			 * MnrlCbsData_DAO mnrlCbscifEntity = new MnrlCbsData_DAO();
			 * mnrlCbscifEntity.setMobileNo(mnrlentity.getMobileNo());
			 * mnrlCbscifEntity.setCif(cif.get(i)); //
			 * mnrlCbscifEntity.setCifEnquiryFlag("2");
			 * mnrlCbscifEntity.setAccountNo("777777777777");
			 * mnrlCbscifEntity.setProcessFlag("0");
			 * mnrlCbscifEntity.setMnrlReason(mnrlentity.getDisconnectingReasonId());
			 * mnrlCbscifEntity = updateFlagService.setFlags(mnrlCbscifEntity, defaultFlag,
			 * mobileRemovalFlag); mnrlCbscifEntity.setActionFlag("0");
			 * mnrlCbscifEntity.setUuid(commonMethodUtils.getUuid());
			 * LOGGER.info("CIF Data [{}]", mnrlCbscifEntity.toString());
			 * cbsDataList.add(mnrlCbscifEntity); // cbsDataRepo.save(mnrlCbscifEntity);
			 * 
			 * } } // cbsDataRepo.saveAll(cbsDataList); if
			 * (mobileEnquiryResponseVo.getCIFAccountEnqResponse().getBody().getPayload().
			 * getCollection() .getAccountNo().size() > 0) { List<String> acc =
			 * mobileEnquiryResponseVo.getCIFAccountEnqResponse().getBody().getPayload()
			 * .getCollection().getAccountNo(); for (int i = 0; i < acc.size(); i++) {
			 * MnrlCbsData_DAO mnrlCbsEntity = new MnrlCbsData_DAO();
			 * mnrlCbsEntity.setMobileNo(mnrlentity.getMobileNo());
			 * mnrlCbsEntity.setAccountNo(acc.get(i)); mnrlCbsEntity.setCif("777777777777");
			 * // mnrlCbsEntity.setAccEnquiryFlag("1"); mnrlCbsEntity.setProcessFlag("0");
			 * mnrlCbsEntity.setMnrlReason(mnrlentity.getDisconnectingReasonId());
			 * mnrlCbsEntity = updateFlagService.setFlags(mnrlCbsEntity, defaultFlag,
			 * mobileRemovalFlag); mnrlCbsEntity.setActionFlag("0");
			 * mnrlCbsEntity.setUuid(commonMethodUtils.getUuid());
			 * LOGGER.info("Account Data [{}]", mnrlCbsEntity.toString());
			 * cbsDataList.add(mnrlCbsEntity); // cbsDataRepo.save(mnrlCbsEntity); } } //
			 * cbsDataList.add(mnrlCbsEntity); long listStart = System.currentTimeMillis();
			 * cbsDataRepo.saveAll(cbsDataList); dataRepo.save(mnrlentity); long listend =
			 * System.currentTimeMillis(); long dbElapsed = listend - listStart;
			 * LOGGER.info("Data Base elapsed in seconds [{}]",
			 * TimeUnit.MILLISECONDS.toSeconds(dbElapsed));
			 * 
			 * 
			 * for (int i = 0; i < cbsDataList.size(); i++) {
			 * 
			 * totalCbsData.setMobileNo(cbsDataList.get(i).getMobileNo());
			 * totalCbsData.setCif(cbsDataList.get(i).getCif());
			 * totalCbsData.setAccountNo(cbsDataList.get(i).getAccountNo()); }
			 * 
			 * 
			 * long after = System.currentTimeMillis(); long ifblockElapsed = after -
			 * before; LOGGER.info("loop elapsed in seconds [{}]",
			 * TimeUnit.MILLISECONDS.toSeconds(ifblockElapsed)); } else { long elsestart =
			 * System.currentTimeMillis(); if
			 * (mobileEnquiryResponseVo.getFlag().equals("Y")) {
			 * LOGGER.info("No details found  for the mobile number [{}]",
			 * mnrlentity.getMobileNo()); dataRepo.save(mnrlentity); }
			 * 
			 * if (mobileEnquiryResponseVo.getFlag().equals("H")) {
			 * LOGGER.info("Invalid Header[{}]", mnrlentity.getMobileNo());
			 * mnrlentity.setProcessFlag("N"); dataRepo.save(mnrlentity); }
			 * 
			 * else { mnrlentity.setProcessFlag("N"); dataRepo.save(mnrlentity); }
			 * 
			 * long elseend = System.currentTimeMillis(); long elseElapsed = elseend -
			 * elsestart; LOGGER.info("ELSE elapsed in seconds [{}]",
			 * TimeUnit.MILLISECONDS.toSeconds(elseElapsed)); }
			 */
			long end = System.currentTimeMillis();
			long elapsed = end - start;
			LOGGER.info("Mobile Enquiry elapsed in seconds [{}]", TimeUnit.MILLISECONDS.toSeconds(elapsed));
			return CompletableFuture.completedFuture(totalCbsData);// should return cbs entity

			// mobile enquiry call and save the respomse in cbs table
			// debit freeze call if mobile enquiry sucess and LEA in mnrl data
		} catch (Exception e) {
			LOGGER.error("Exception in async", e);
			// mnrlentity.setProcessFlag("N");
			// dataRepo.save(mnrlentity);

		}
		return null;

	}

}
