package com.fisglobal.fsg.dip.core.fri.cbs.service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

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
import com.fisglobal.fsg.dip.core.cbs.service.MobileNumberEnquiryService;
import com.fisglobal.fsg.dip.core.cbs.service.UpdateFlagService;
import com.fisglobal.fsg.dip.core.service.CommonMethodUtils;
import com.fisglobal.fsg.dip.entity.FriCbsData_DAO;
import com.fisglobal.fsg.dip.entity.FriData_DAO;
import com.fisglobal.fsg.dip.entity.impl.FriCbsDataImpl;
import com.fisglobal.fsg.dip.entity.repo.FriCbsDataRepo;
import com.fisglobal.fsg.dip.entity.repo.FriDataRepo;

@Service
public class FriAsyncService {

	private static final Logger LOGGER = LoggerFactory.getLogger(FriAsyncService.class);

	@Inject
	private FriDataRepo dataRepo;

	@Inject
	private FriCbsDataRepo cbsDataRepo;

	@Inject
	private MobileNumberEnquiryService mobileNumberEnquiryService;

	@Inject
	private CommonMethodUtils commonMethodUtils;

	@Inject
	private UpdateFlagService updateFlagService;
	
	@Value("${error.invalid.header:Invalid Header}")
	private String invalidHeader;
	
	

	@Async("fri-mobile-enquiry")
	public CompletableFuture<FriCbsData_DAO> mobileEnquiry(FriData_DAO frientity) throws KeyManagementException, ClientProtocolException,
			NoSuchAlgorithmException, KeyStoreException, JSONException, IOException {
		try {
		FriCbsData_DAO totalCbsData = new FriCbsData_DAO();
		//List<FriCbsData_DAO> cbsDataList = new ArrayList<>();
		MobileEnquiryResponseVo mobileEnquiryResponseVo = new MobileEnquiryResponseVo();
		String countryCode = commonMethodUtils.getCountryCode();
		boolean defaultFlag = true;
		LOGGER.info("Mobile Enquiry [{}]", frientity);
		//FriCbsData_DAO mnrlCbsDataEntity = new FriCbsData_DAO();
		frientity.setMobileEnquiryDateTime(LocalDateTime.now());
		dataRepo.save(frientity);
		frientity.setProcessFlag("Y");


		String mobileNo = commonMethodUtils.getMobileNumberWithoutCC(frientity.getMobileNo());

		mobileEnquiryResponseVo = mobileNumberEnquiryService.getCIFAccountDetails(mobileNo, countryCode, "FRI");
		//LOGGER.info("Flag [{}]",mobileEnquiryResponseVo.getFlag());
		
		frientity.setMobileEnquiryDateTime(LocalDateTime.now());
		if (!(Optional.ofNullable(mobileEnquiryResponseVo.getCIFAccountEnqResponse()).isPresent()
				|| Optional.ofNullable(mobileEnquiryResponseVo.getErrorResponse()).isPresent())) {
			frientity.setProcessFlag("N");
			dataRepo.save(frientity);
			// mnrlCbsEntity.setActionFlag("0");
		}
		
		Optional.ofNullable(mobileEnquiryResponseVo.getCIFAccountEnqResponse()).ifPresent(m -> {
			Optional.ofNullable(m.getBody()).ifPresent(n -> {
				Optional.ofNullable(n.getPayload()).ifPresent(o -> {
					Optional.ofNullable(o.getCollection()).ifPresent(p -> {

						//frientity.setProcessFlag("Y");
						if (m.getBody().getPayload().getCollection().getCIFNo()
								.size() > 0) {
							List<String> cif = m.getBody().getPayload()
									.getCollection().getCIFNo();
							for (int i = 0; i < cif.size(); i++) {
								FriCbsData_DAO friCbscifEntity = new FriCbsData_DAO();
								friCbscifEntity.setUuid(commonMethodUtils.getUuid());
								friCbscifEntity.setDataUuid(frientity.getUuid());
								friCbscifEntity.setMobileNo(frientity.getMobileNo());
								friCbscifEntity.setCif(cif.get(i));
								// mnrlCbscifEntity.setCifEnquiryFlag("2");
								friCbscifEntity.setAccountNo("777777777777");
								friCbscifEntity.setProcessFlag("0");
								friCbscifEntity.setFriReason(frientity.getFriIndicator());
								friCbscifEntity = updateFlagService.setFriFlags(friCbscifEntity);
								friCbscifEntity.setActionFlag("0");
								
								LOGGER.info("CIF Data [{}]", friCbscifEntity.toString());
								//cbsDataList.add(friCbscifEntity);
								 cbsDataRepo.save(friCbscifEntity);

							}
						}
						// cbsDataRepo.saveAll(cbsDataList);
						if (m.getBody().getPayload().getCollection().getAccountNo()
								.size() > 0) {
							List<String> acc = m.getBody().getPayload()
									.getCollection().getAccountNo();
							for (int i = 0; i < acc.size(); i++) {
								FriCbsData_DAO friCbsEntity = new FriCbsData_DAO();
								friCbsEntity.setUuid(commonMethodUtils.getUuid());
								friCbsEntity.setDataUuid(frientity.getUuid());
								friCbsEntity.setMobileNo(frientity.getMobileNo());
								friCbsEntity.setAccountNo(acc.get(i));
								friCbsEntity.setCif("777777777777");
								// mnrlCbsEntity.setAccEnquiryFlag("1");
								friCbsEntity.setProcessFlag("0");
								friCbsEntity.setFriReason(frientity.getFriIndicator());
								friCbsEntity = updateFlagService.setFriFlags(friCbsEntity);
								friCbsEntity.setActionFlag("0");
								
								LOGGER.info("Account Data [{}]", friCbsEntity.toString());
								//cbsDataList.add(friCbsEntity);
								 cbsDataRepo.save(friCbsEntity);
							}
						}
						// cbsDataList.add(mnrlCbsEntity);
					//	cbsDataRepo.saveAll(cbsDataList);
						dataRepo.save(frientity);

							/*
							 * for (int i = 0; i < cbsDataList.size(); i++) {
							 * 
							 * totalCbsData.setMobileNo(cbsDataList.get(i).getMobileNo());
							 * totalCbsData.setCif(cbsDataList.get(i).getCif());
							 * totalCbsData.setAccountNo(cbsDataList.get(i).getAccountNo()); }
							 */
					
					});

				});
			});
		});
		
		Optional.ofNullable(mobileEnquiryResponseVo.getErrorResponse()).ifPresent(a -> {
			Optional.ofNullable(a.getAdditionalinfo()).ifPresent(b -> {
				if (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepText())
						&& a.getAdditionalinfo().getExcepText().equals(invalidHeader)) {
					LOGGER.info("Error [{}]", a.getAdditionalinfo().getExcepText());
					frientity.setProcessFlag("N");
				} else {

					if (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepMetaData())) {
						// mnrlentity.setProcessFlag("Y");
						frientity.setMobileEnquiryResponse(a.getAdditionalinfo().getExcepMetaData());
						LOGGER.info("Mobile Enquiry EMetadata [{}]", a.getAdditionalinfo().getExcepMetaData());
					} else if (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepText())) {
						// mnrlentity.setProcessFlag("Y");
						frientity.setMobileEnquiryResponse(a.getAdditionalinfo().getExcepText());
						LOGGER.info("Mobile Enquiry EText [{}]", a.getAdditionalinfo().getExcepText());
					} else if (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepCode())) {
						// mnrlentity.setProcessFlag("Y");
						frientity.setMobileEnquiryResponse(a.getAdditionalinfo().getExcepCode());
						LOGGER.info("Mobile Enquiry Ecode [{}]", a.getAdditionalinfo().getExcepCode());
					}
				}
			});
			dataRepo.save(frientity);
		});

		if (StringUtils.isNotBlank(mobileEnquiryResponseVo.getErrorMessage())) {
			LOGGER.info("Mobile Enquiry error message [{}]", mobileEnquiryResponseVo.getErrorMessage());
			frientity.setProcessFlag("Y");
			frientity.setMobileEnquiryResponse(mobileEnquiryResponseVo.getErrorMessage());
			dataRepo.save(frientity);

		}
			/*
			 * if (mobileEnquiryResponseVo != null &&
			 * mobileEnquiryResponseVo.getFlag().equals("N")) {
			 * //frientity.setProcessFlag("Y"); if
			 * (mobileEnquiryResponseVo.getCIFAccountEnqResponse().getBody().getPayload().
			 * getCollection().getCIFNo() .size() > 0) { List<String> cif =
			 * mobileEnquiryResponseVo.getCIFAccountEnqResponse().getBody().getPayload()
			 * .getCollection().getCIFNo(); for (int i = 0; i < cif.size(); i++) {
			 * FriCbsData_DAO friCbscifEntity = new FriCbsData_DAO();
			 * friCbscifEntity.setMobileNo(frientity.getMobileNo());
			 * friCbscifEntity.setCif(cif.get(i)); //
			 * mnrlCbscifEntity.setCifEnquiryFlag("2");
			 * friCbscifEntity.setAccountNo("777777777777");
			 * friCbscifEntity.setProcessFlag("0");
			 * friCbscifEntity.setFriReason(frientity.getFriIndicator()); friCbscifEntity =
			 * updateFlagService.setFriFlags(friCbscifEntity, defaultFlag);
			 * friCbscifEntity.setActionFlag("0");
			 * friCbscifEntity.setUuid(commonMethodUtils.getUuid());
			 * LOGGER.info("CIF Data [{}]", friCbscifEntity.toString());
			 * cbsDataList.add(friCbscifEntity); // cbsDataRepo.save(mnrlCbscifEntity);
			 * 
			 * } } // cbsDataRepo.saveAll(cbsDataList); if
			 * (mobileEnquiryResponseVo.getCIFAccountEnqResponse().getBody().getPayload().
			 * getCollection().getAccountNo() .size() > 0) { List<String> acc =
			 * mobileEnquiryResponseVo.getCIFAccountEnqResponse().getBody().getPayload()
			 * .getCollection().getAccountNo(); for (int i = 0; i < acc.size(); i++) {
			 * FriCbsData_DAO friCbsEntity = new FriCbsData_DAO();
			 * friCbsEntity.setMobileNo(frientity.getMobileNo());
			 * friCbsEntity.setAccountNo(acc.get(i)); friCbsEntity.setCif("777777777777");
			 * // mnrlCbsEntity.setAccEnquiryFlag("1"); friCbsEntity.setProcessFlag("0");
			 * friCbsEntity.setFriReason(frientity.getFriIndicator()); friCbsEntity =
			 * updateFlagService.setFriFlags(friCbsEntity, defaultFlag);
			 * friCbsEntity.setActionFlag("0");
			 * friCbsEntity.setUuid(commonMethodUtils.getUuid());
			 * LOGGER.info("Account Data [{}]", friCbsEntity.toString());
			 * cbsDataList.add(friCbsEntity); // cbsDataRepo.save(mnrlCbsEntity); } } //
			 * cbsDataList.add(mnrlCbsEntity); cbsDataRepo.saveAll(cbsDataList);
			 * dataRepo.save(frientity);
			 * 
			 * for (int i = 0; i < cbsDataList.size(); i++) {
			 * 
			 * totalCbsData.setMobileNo(cbsDataList.get(i).getMobileNo());
			 * totalCbsData.setCif(cbsDataList.get(i).getCif());
			 * totalCbsData.setAccountNo(cbsDataList.get(i).getAccountNo()); } } else { if
			 * (mobileEnquiryResponseVo.getFlag().equals("Y")) {
			 * //frientity.setProcessFlag("Y"); dataRepo.save(frientity);
			 * LOGGER.info("No details found  for the mobile number [{}]",
			 * frientity.getMobileNo()); } if
			 * (mobileEnquiryResponseVo.getFlag().equals("H")) {
			 * LOGGER.info("Invalid Header[{}]", frientity.getMobileNo());
			 * frientity.setProcessFlag("N"); dataRepo.save(frientity); }
			 * 
			 * }
			 */
			return CompletableFuture.completedFuture(totalCbsData);// should return cbs entity
		}catch(Exception e) {
			LOGGER.error("Async error in FRI",e);
		}
		return null;
	}
}
