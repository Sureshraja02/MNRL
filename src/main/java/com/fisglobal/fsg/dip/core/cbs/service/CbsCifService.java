package com.fisglobal.fsg.dip.core.cbs.service;

import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fisglobal.fsg.dip.core.cbs.entity.Collection;
import com.fisglobal.fsg.dip.core.cbs.entity.DetailedCIFEnquiryResponseVo;
import com.fisglobal.fsg.dip.core.service.CommonMethodUtils;
import com.fisglobal.fsg.dip.entity.MnrlCbsData_DAO;
import com.fisglobal.fsg.dip.entity.impl.MnrlCbsDataImpl;
import com.fisglobal.fsg.dip.entity.repo.MnrlCbsDataRepo;

@Service
public class CbsCifService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CbsCifService.class);

	@Inject
	private DetailedCIFEnquiry detailedCIFEnquiry;

	@Inject
	private MnrlCbsDataRepo cbsDataRepo;

	@Inject
	private UpdateFlagService updateFlagService;
	boolean defaultFlag = false;

	@Value("${error.invalid.header:Invalid Header}")
	private String invalidHeader;

	@Inject
	private CommonMethodUtils commonMethodUtils;

	@Inject
	private MnrlCbsDataImpl mnrlCbsImpl;

	public MnrlCbsData_DAO processCifEnquiry(MnrlCbsData_DAO cbsData)
			throws KeyManagementException, NumberFormatException, ClientProtocolException, JSONException,
			NoSuchAlgorithmException, KeyStoreException, IOException {

		// List<MnrlCbsData_DAO> cbsDataList = new ArrayList<>();
		DetailedCIFEnquiryResponseVo cifResponse = new DetailedCIFEnquiryResponseVo();
		if (cbsData.getAccountNo().equals("777777777777")) {
			BigInteger bigInt = new BigInteger(cbsData.getCif());
			cbsData.setCifEnquiryDateTime(LocalDateTime.now());
			cbsDataRepo.save(cbsData);
			cifResponse = detailedCIFEnquiry.getCifDetails(bigInt, "MNRL");
			LocalDateTime date=LocalDateTime.now();
			cbsData.setCifEnquiryDateTime(date);
			if (!(Optional.ofNullable(cifResponse.getCustEnqResponse()).isPresent()
					|| Optional.ofNullable(cifResponse.getErrorResponse()).isPresent())) {
				
				cbsData.setProcessFlag("0");
				cbsDataRepo.save(cbsData);
				// mnrlCbsEntity.setActionFlag("0");
			}

			Optional.ofNullable(cifResponse.getCustEnqResponse()).ifPresent(m -> {
				Optional.ofNullable(m.getBody()).ifPresent(n -> {
					Optional.ofNullable(n.getPayload()).ifPresent(o -> {
						if (o.getCollection().size() > 0) {
							List<Collection> accdetails = o.getCollection();

							/*
							 * MnrlCbsDataPK id = new MnrlCbsDataPK();
							 * id.setMobileNo(cbsData.getMobileNo()); id.setCif(cbsData.getCif());
							 * id.setAccountNo(cbsData.getAccountNo());
							 * 
							 * Optional<MnrlCbsData_DAO> existObj = cbsDataRepo.findById(id);
							 * 
							 * if (existObj.isPresent()) {
							 * existObj.get().setAccountNo(accdetails.get(0).getAcctNumber());
							 * existObj.get().setProcessFlag("2"); existObj.get().setCif(cbsData.getCif());
							 * existObj.get().setPrimaryAccountFlag(accdetails.get(0).getAccountHolder());
							 * cbsDataRepo.save(existObj.get()); }
							 */

							

							// cbsData.setAccountNo(accdetails.get(0).getAcctNumber());
							// cbsData.setProcessFlag("2");
							// cbsData.setPrimaryAccountFlag(accdetails.get(0).getAccountHolder());
							// cbsDataRepo.save(cbsData);

							for (int i = 1; i < accdetails.size(); i++) {
								// MnrlCbsDataPK id = new MnrlCbsDataPK();
								// id.setMobileNo(cbsData.getMobileNo());
								// id.setCif(cbsData.getCif());
								// id.setAccountNo(accdetails.get(i).getAcctNumber());
								// id.setUuid(cbsData.getUuid());
								LOGGER.info("ACC [{}] ,CIF [{}] , MOB [{}] UUID [{}]", accdetails.get(i).getAcctNumber(),
										cbsData.getCif(), cbsData.getMobileNo(),cbsData.getUuid());
								MnrlCbsData_DAO obj = mnrlCbsImpl.getMnrlCbsData(cbsData.getMobileNo(),
										cbsData.getCif(), accdetails.get(i).getAcctNumber(), cbsData.getUuid());
								if (obj != null) {
									cbsData.setAccountNo(accdetails.get(i).getAcctNumber());
									cbsData.setProcessFlag("4");
									cbsData.setPrimaryAccountFlag(accdetails.get(i).getAccountHolder());
									cbsData.setAcctType(accdetails.get(i).getAcctType());
									cbsData.setCifEnquiryFlag("Y");
									cbsData.setCustomerName(accdetails.get(i).getAcctName());
									cbsDataRepo.save(cbsData);
								} else {
									boolean mobileRemovalFlag = false;
									MnrlCbsData_DAO cbsNewData = new MnrlCbsData_DAO();
								//	LOGGER.info("New Entry [{}]",date);
									cbsNewData.setCifEnquiryDateTime(date);
									cbsNewData.setUuid(commonMethodUtils.getUuid());
									cbsNewData.setDataUuid(cbsData.getDataUuid());
									cbsNewData.setMobileNo(cbsData.getMobileNo());
									cbsNewData.setCif(cbsData.getCif());
									cbsNewData.setAccountNo(accdetails.get(i).getAcctNumber());
									cbsNewData.setProcessFlag("3");
									cbsNewData.setMnrlReason(cbsData.getMnrlReason());
									//cbsNewData = updateFlagService.setFlags(cbsNewData, defaultFlag, mobileRemovalFlag);
									cbsNewData = updateFlagService.setFlags(cbsNewData, mobileRemovalFlag);
									cbsNewData.setActionFlag("0");
									cbsNewData.setCifEnquiryFlag("Y");
									cbsNewData.setPrimaryAccountFlag(accdetails.get(i).getAccountHolder());
									cbsNewData.setAcctType(accdetails.get(i).getAcctType());
									cbsNewData.setCustomerName(accdetails.get(i).getAcctName());
									
									cbsDataRepo.save(cbsNewData);
								}

							}
							
							LOGGER.info("Acc No [{}] [{}]", cbsData.getAccountNo(), cbsData.getMnrlReason());
							int updateRows = cbsDataRepo.updateAccCbsData(cbsData.getAccountNo(), cbsData.getCif(),
									cbsData.getMobileNo(), accdetails.get(0).getAcctNumber(), "2",
									accdetails.get(0).getAccountHolder(), accdetails.get(0).getAcctName(),
									accdetails.get(0).getAcctType(), cbsData.getUuid(),date);
							LOGGER.info("Updated count[{}]", updateRows);
						} else {
							cbsData.setProcessFlag("5");
							cbsData.setCifEnquiryFlag("Y");
							cbsData.setActionFlag("0");
							cbsDataRepo.save(cbsData);
						}
					});
				});
			});

			Optional.ofNullable(cifResponse.getErrorResponse()).ifPresent(a -> {
				Optional.ofNullable(a.getAdditionalinfo()).ifPresent(b -> {
					if (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepText())
							&& a.getAdditionalinfo().getExcepText().equals(invalidHeader)) {
						LOGGER.info("Error [{}]", a.getAdditionalinfo().getExcepText());
						cbsData.setProcessFlag("0");
						cbsDataRepo.save(cbsData);
					} else {

						if (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepMetaData())) {
							cbsData.setCifEnquiryFlag("E");
							cbsData.setCifEnquiryResponse(a.getAdditionalinfo().getExcepMetaData());
							cbsData.setProcessFlag("20");
							cbsDataRepo.save(cbsData);
							LOGGER.info("cif enquiry EMetadata [{}]", a.getAdditionalinfo().getExcepMetaData());
						} else if (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepText())) {
							cbsData.setCifEnquiryFlag("E");
							cbsData.setCifEnquiryResponse(a.getAdditionalinfo().getExcepText());
							cbsData.setProcessFlag("20");
							cbsDataRepo.save(cbsData);
							LOGGER.info("cif enquiry EText [{}]", a.getAdditionalinfo().getExcepText());
						} else if (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepCode())) {
							cbsData.setCifEnquiryFlag("E");
							cbsData.setCifEnquiryResponse(a.getAdditionalinfo().getExcepCode());
							cbsData.setProcessFlag("20");
							cbsDataRepo.save(cbsData);
							LOGGER.info("cif enquiry Ecode [{}]", a.getAdditionalinfo().getExcepCode());
						}

					}
				});
			});

			/*
			 * if (cifResponse != null) { List<Collection> accdetails =
			 * cifResponse.getCustEnqResponse().getBody().getPayload().getCollection();
			 * 
			 * /* MnrlCbsDataPK id = new MnrlCbsDataPK();
			 * id.setMobileNo(cbsData.getMobileNo()); id.setCif(cbsData.getCif());
			 * id.setAccountNo(cbsData.getAccountNo());
			 * 
			 * Optional<MnrlCbsData_DAO> existObj = cbsDataRepo.findById(id);
			 * 
			 * if (existObj.isPresent()) {
			 * existObj.get().setAccountNo(accdetails.get(0).getAcctNumber());
			 * existObj.get().setProcessFlag("2"); existObj.get().setCif(cbsData.getCif());
			 * existObj.get().setPrimaryAccountFlag(accdetails.get(0).getAccountHolder());
			 * cbsDataRepo.save(existObj.get()); }
			 * 
			 * 
			 * LOGGER.info("Acc No [{}] [{}]", cbsData.getAccountNo(),
			 * cbsData.getMnrlReason()); int updateRows =
			 * cbsDataRepo.updateAccCbsData(cbsData.getAccountNo(), cbsData.getCif(),
			 * cbsData.getMobileNo(), accdetails.get(0).getAcctNumber(), "2",
			 * accdetails.get(0).getAccountHolder(),
			 * accdetails.get(0).getAcctName(),accdetails.get(0).getAcctType());
			 * LOGGER.info("Updated count[{}]", updateRows);
			 * 
			 * // cbsData.setAccountNo(accdetails.get(0).getAcctNumber()); //
			 * cbsData.setProcessFlag("2"); //
			 * cbsData.setPrimaryAccountFlag(accdetails.get(0).getAccountHolder()); //
			 * cbsDataRepo.save(cbsData);
			 * 
			 * for (int i = 1; i < accdetails.size(); i++) { MnrlCbsDataPK id = new
			 * MnrlCbsDataPK(); id.setMobileNo(cbsData.getMobileNo());
			 * id.setCif(cbsData.getCif());
			 * id.setAccountNo(accdetails.get(i).getAcctNumber());
			 * 
			 * Optional<MnrlCbsData_DAO> obj = cbsDataRepo.findById(id); if
			 * (obj.isPresent()) { cbsData.setAccountNo(accdetails.get(i).getAcctNumber());
			 * cbsData.setProcessFlag("4");
			 * cbsData.setPrimaryAccountFlag(accdetails.get(i).getAccountHolder());
			 * cbsData.setAcctType(accdetails.get(i).getAcctType());
			 * cbsData.setCifEnquiryFlag("Y");
			 * cbsData.setCustomerName(accdetails.get(i).getAcctName());
			 * cbsDataRepo.save(cbsData); } else { boolean mobileRemovalFlag=false;
			 * MnrlCbsData_DAO cbsNewData = new MnrlCbsData_DAO();
			 * cbsNewData.setMobileNo(cbsData.getMobileNo());
			 * cbsNewData.setCif(cbsData.getCif());
			 * cbsNewData.setAccountNo(accdetails.get(i).getAcctNumber());
			 * cbsNewData.setProcessFlag("3");
			 * cbsNewData.setMnrlReason(cbsData.getMnrlReason()); cbsNewData =
			 * updateFlagService.setFlags(cbsNewData, defaultFlag,mobileRemovalFlag);
			 * cbsNewData.setActionFlag("0"); cbsNewData.setCifEnquiryFlag("Y");
			 * cbsNewData.setPrimaryAccountFlag(accdetails.get(i).getAccountHolder());
			 * cbsNewData.setAcctType(accdetails.get(i).getAcctType());
			 * cbsNewData.setCustomerName(accdetails.get(i).getAcctName());
			 * cbsDataRepo.save(cbsNewData); }
			 * 
			 * } } else { cbsData.setProcessFlag("5"); cbsData.setCifEnquiryFlag("Y");
			 * cbsData.setActionFlag("0"); cbsDataRepo.save(cbsData); }
			 */

		}
		return cbsData;

	}

	public MnrlCbsData_DAO processPrimaryAccFlagUpdate(MnrlCbsData_DAO cbsData)
			throws KeyManagementException, NumberFormatException, ClientProtocolException, JSONException,
			NoSuchAlgorithmException, KeyStoreException, IOException {
		List<MnrlCbsData_DAO> cbsDataList = new ArrayList<>();
		DetailedCIFEnquiryResponseVo cifResponse = new DetailedCIFEnquiryResponseVo();
		if ((!cbsData.getAccountNo().equals("777777777777")) && (!cbsData.getCif().equals("777777777777"))
				&& (cbsData.getProcessFlag().equals("0"))) {
			BigInteger bigInt = new BigInteger(cbsData.getCif());
			cbsData.setCifEnquiryDateTime(LocalDateTime.now());
			cbsDataRepo.save(cbsData);
			cifResponse = detailedCIFEnquiry.getCifDetails(bigInt, "MNRL");
			LocalDateTime date=LocalDateTime.now();
			cbsData.setCifEnquiryDateTime(date);
			if (!(Optional.ofNullable(cifResponse.getCustEnqResponse()).isPresent()
					|| Optional.ofNullable(cifResponse.getErrorResponse()).isPresent())) {
				cbsData.setProcessFlag("0");
				cbsDataRepo.save(cbsData);
				// mnrlCbsEntity.setActionFlag("0");
			}

			Optional.ofNullable(cifResponse.getCustEnqResponse()).ifPresent(m -> {
				Optional.ofNullable(m.getBody()).ifPresent(n -> {
					Optional.ofNullable(n.getPayload()).ifPresent(o -> {
						if (o.getCollection().size() > 0) {

							List<Collection> accdetails = o.getCollection();

							/*
							 * MnrlCbsDataPK id = new MnrlCbsDataPK();
							 * id.setMobileNo(cbsData.getMobileNo()); id.setCif(cbsData.getCif());
							 * id.setAccountNo(cbsData.getAccountNo());
							 * 
							 * Optional<MnrlCbsData_DAO> existObj = cbsDataRepo.findById(id);
							 * 
							 * if (existObj.isPresent()) {
							 * existObj.get().setAccountNo(accdetails.get(0).getAcctNumber());
							 * existObj.get().setProcessFlag("2"); existObj.get().setCif(cbsData.getCif());
							 * existObj.get().setPrimaryAccountFlag(accdetails.get(0).getAccountHolder());
							 * cbsDataRepo.save(existObj.get()); }
							 */

							// cbsData.setAccountNo(accdetails.get(0).getAcctNumber());
							// cbsData.setProcessFlag("2");
							// cbsData.setPrimaryAccountFlag(accdetails.get(0).getAccountHolder());
							// cbsDataRepo.save(cbsData);

							for (int i = 0; i < accdetails.size(); i++) {
								LOGGER.info("Cbs ACC[{}] , cif enquiry acc [{}] [{}]", cbsData.getAccountNo(),
										accdetails.get(i).getAcctNumber(), cbsData.getMnrlReason());
								if (cbsData.getAccountNo().equals(accdetails.get(i).getAcctNumber())) {
									// MnrlCbsDataPK id = new MnrlCbsDataPK();
									// id.setMobileNo(cbsData.getMobileNo());
									// id.setCif(cbsData.getCif());
									// id.setAccountNo(accdetails.get(i).getAcctNumber());
									LOGGER.info("ACC [{}] ,CIF [{}] , MOB [{}] UUID [{}]", accdetails.get(i).getAcctNumber(),
											cbsData.getCif(), cbsData.getMobileNo(),cbsData.getUuid());
									MnrlCbsData_DAO obj = mnrlCbsImpl.getMnrlCbsData(cbsData.getMobileNo(),
											cbsData.getCif(), accdetails.get(i).getAcctNumber(), cbsData.getUuid());
									if (obj != null) {
										// cbsData.setAccountNo(accdetails.get(i).getAcctNumber());
										cbsData.setProcessFlag("4");
										cbsData.setPrimaryAccountFlag(accdetails.get(i).getAccountHolder());
										cbsData.setAcctType(accdetails.get(i).getAcctType());
										cbsData.setCifEnquiryFlag("Y");
										cbsData.setCustomerName(accdetails.get(i).getAcctName());
										cbsDataRepo.save(cbsData);
									}

								} else {
									LOGGER.info("Acc not matching with cif enquiry acc");
									/*
									 * MnrlCbsDataPK id = new MnrlCbsDataPK();
									 * id.setMobileNo(cbsData.getMobileNo()); id.setCif(cbsData.getCif());
									 * id.setAccountNo(accdetails.get(i).getAcctNumber());
									 * id.setUuid(cbsData.getUuid()); Optional<MnrlCbsData_DAO> obj =
									 * cbsDataRepo.findById(id); obj.isPresent();
									 */
									LOGGER.info("ACC [{}] ,CIF [{}] , MOB [{}] UUID [{}]", accdetails.get(i).getAcctNumber(),
											cbsData.getCif(), cbsData.getMobileNo(),cbsData.getUuid());
									MnrlCbsData_DAO obj = mnrlCbsImpl.getMnrlCbsData(cbsData.getMobileNo(),
											cbsData.getCif(), accdetails.get(i).getAcctNumber(), cbsData.getUuid());
									if (obj==null) {
										boolean mobileRemovalFlag = false;
										MnrlCbsData_DAO cbsNewData = new MnrlCbsData_DAO();
										//LOGGER.info("New Entry [{}] in primary flag update",date);
										cbsNewData.setCifEnquiryDateTime(date);
										cbsNewData.setUuid(commonMethodUtils.getUuid());
										cbsNewData.setDataUuid(cbsData.getDataUuid());
										cbsNewData.setMobileNo(cbsData.getMobileNo());
										cbsNewData.setCif(cbsData.getCif());
										cbsNewData.setAccountNo(accdetails.get(i).getAcctNumber());
										cbsNewData.setProcessFlag("3");
										cbsNewData.setPrimaryAccountFlag(accdetails.get(i).getAccountHolder());
										cbsNewData.setAcctType(accdetails.get(i).getAcctType());
										cbsNewData.setMnrlReason(cbsData.getMnrlReason());
										//cbsNewData = updateFlagService.setFlags(cbsNewData, defaultFlag,mobileRemovalFlag);
										cbsNewData = updateFlagService.setFlags(cbsNewData, mobileRemovalFlag);
										cbsNewData.setActionFlag("0");
										cbsNewData.setCifEnquiryFlag("Y");
										cbsNewData.setCustomerName(accdetails.get(i).getAcctName());
										
										cbsDataRepo.save(cbsNewData);
										cbsData.setCifEnquiryFlag("Y");
										cbsData.setProcessFlag("11");
										cbsDataRepo.save(cbsData);
									} else {
										// MnrlCbsData_DAO cbsNewData = new MnrlCbsData_DAO();
										cbsData.setProcessFlag("9");
										cbsData.setCifEnquiryFlag("Y");
										cbsData.setMnrlReason(cbsData.getMnrlReason());
										// cbsData.setCustomerName(accdetails.get(i).getAcctName());
										// cbsNewData.setPrimaryAccountFlag(accdetails.get(i).getAccountHolder());
										cbsDataRepo.save(cbsData);
									}

								}
							}
						} else {
							cbsData.setProcessFlag("5");
							cbsData.setCifEnquiryFlag("Y");
							cbsData.setActionFlag("1");
							cbsDataRepo.save(cbsData);

						}
					});
				});
			});

			Optional.ofNullable(cifResponse.getErrorResponse()).ifPresent(a -> {
				Optional.ofNullable(a.getAdditionalinfo()).ifPresent(b -> {
					if (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepText())
							&& a.getAdditionalinfo().getExcepText().equals(invalidHeader)) {
						LOGGER.info("Error [{}]", a.getAdditionalinfo().getExcepText());
						cbsData.setProcessFlag("0");
						cbsDataRepo.save(cbsData);
					} else {

						if (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepMetaData())) {
							cbsData.setCifEnquiryFlag("E");
							cbsData.setCifEnquiryResponse(a.getAdditionalinfo().getExcepMetaData());
							cbsData.setProcessFlag("20");
							cbsDataRepo.save(cbsData);
							LOGGER.info("cif enquiry EMetadata [{}]", a.getAdditionalinfo().getExcepMetaData());
						} else if (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepText())) {
							cbsData.setCifEnquiryFlag("E");
							cbsData.setCifEnquiryResponse(a.getAdditionalinfo().getExcepText());
							cbsData.setProcessFlag("20");
							cbsDataRepo.save(cbsData);
							LOGGER.info("cif enquiry EText [{}]", a.getAdditionalinfo().getExcepText());
						} else if (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepCode())) {
							cbsData.setCifEnquiryFlag("E");
							cbsData.setCifEnquiryResponse(a.getAdditionalinfo().getExcepCode());
							cbsData.setProcessFlag("20");
							cbsDataRepo.save(cbsData);
							LOGGER.info("cif enquiry Ecode [{}]", a.getAdditionalinfo().getExcepCode());
						}

					}
				});
			});

			/*
			 * if (cifResponse != null) { List<Collection> accdetails =
			 * cifResponse.getCustEnqResponse().getBody().getPayload().getCollection();
			 * 
			 * 
			 * MnrlCbsDataPK id = new MnrlCbsDataPK();
			 * id.setMobileNo(cbsData.getMobileNo()); id.setCif(cbsData.getCif());
			 * id.setAccountNo(cbsData.getAccountNo());
			 * 
			 * Optional<MnrlCbsData_DAO> existObj = cbsDataRepo.findById(id);
			 * 
			 * if (existObj.isPresent()) {
			 * existObj.get().setAccountNo(accdetails.get(0).getAcctNumber());
			 * existObj.get().setProcessFlag("2"); existObj.get().setCif(cbsData.getCif());
			 * existObj.get().setPrimaryAccountFlag(accdetails.get(0).getAccountHolder());
			 * cbsDataRepo.save(existObj.get()); }
			 * 
			 * 
			 * // cbsData.setAccountNo(accdetails.get(0).getAcctNumber()); //
			 * cbsData.setProcessFlag("2"); //
			 * cbsData.setPrimaryAccountFlag(accdetails.get(0).getAccountHolder()); //
			 * cbsDataRepo.save(cbsData);
			 * 
			 * for (int i = 0; i < accdetails.size(); i++) {
			 * LOGGER.info("Cbs ACC[{}] , cif enquiry acc [{}] [{}]",
			 * cbsData.getAccountNo(), accdetails.get(i).getAcctNumber(),
			 * cbsData.getMnrlReason()); if
			 * (cbsData.getAccountNo().equals(accdetails.get(i).getAcctNumber())) {
			 * MnrlCbsDataPK id = new MnrlCbsDataPK();
			 * id.setMobileNo(cbsData.getMobileNo()); id.setCif(cbsData.getCif());
			 * id.setAccountNo(accdetails.get(i).getAcctNumber());
			 * LOGGER.info("ACC [{}] ,CIF [{}] , MOB [{}]",
			 * accdetails.get(i).getAcctNumber(), cbsData.getCif(), cbsData.getMobileNo());
			 * Optional<MnrlCbsData_DAO> obj = cbsDataRepo.findById(id); if
			 * (obj.isPresent()) { //
			 * cbsData.setAccountNo(accdetails.get(i).getAcctNumber());
			 * cbsData.setProcessFlag("4");
			 * cbsData.setPrimaryAccountFlag(accdetails.get(i).getAccountHolder());
			 * cbsData.setAcctType(accdetails.get(i).getAcctType());
			 * cbsData.setCifEnquiryFlag("Y");
			 * cbsData.setCustomerName(accdetails.get(i).getAcctName());
			 * cbsDataRepo.save(cbsData); }
			 * 
			 * } else { LOGGER.info("Acc not matching with cif enquiry acc"); MnrlCbsDataPK
			 * id = new MnrlCbsDataPK(); id.setMobileNo(cbsData.getMobileNo());
			 * id.setCif(cbsData.getCif());
			 * id.setAccountNo(accdetails.get(i).getAcctNumber());
			 * LOGGER.info("ACC [{}] ,CIF [{}] , MOB [{}]",
			 * accdetails.get(i).getAcctNumber(), cbsData.getCif(), cbsData.getMobileNo());
			 * Optional<MnrlCbsData_DAO> obj = cbsDataRepo.findById(id); if
			 * (!obj.isPresent()) { boolean mobileRemovalFlag=false; MnrlCbsData_DAO
			 * cbsNewData = new MnrlCbsData_DAO();
			 * cbsNewData.setMobileNo(cbsData.getMobileNo());
			 * cbsNewData.setCif(cbsData.getCif());
			 * cbsNewData.setAccountNo(accdetails.get(i).getAcctNumber());
			 * cbsNewData.setProcessFlag("3");
			 * cbsNewData.setPrimaryAccountFlag(accdetails.get(i).getAccountHolder());
			 * cbsNewData.setAcctType(accdetails.get(i).getAcctType());
			 * cbsNewData.setMnrlReason(cbsData.getMnrlReason()); cbsNewData =
			 * updateFlagService.setFlags(cbsNewData, defaultFlag,mobileRemovalFlag);
			 * cbsNewData.setActionFlag("0"); cbsNewData.setCifEnquiryFlag("Y");
			 * cbsNewData.setCustomerName(accdetails.get(i).getAcctName());
			 * cbsDataRepo.save(cbsNewData); } else { // MnrlCbsData_DAO cbsNewData = new
			 * MnrlCbsData_DAO(); cbsData.setProcessFlag("9");
			 * cbsData.setCifEnquiryFlag("Y");
			 * cbsData.setMnrlReason(cbsData.getMnrlReason()); //
			 * cbsData.setCustomerName(accdetails.get(i).getAcctName()); //
			 * cbsNewData.setPrimaryAccountFlag(accdetails.get(i).getAccountHolder());
			 * cbsDataRepo.save(cbsData); }
			 * 
			 * } } } else { cbsData.setProcessFlag("5"); cbsData.setCifEnquiryFlag("Y");
			 * cbsData.setActionFlag("1"); cbsDataRepo.save(cbsData);
			 * 
			 * }
			 */

		}
		return cbsData;

	}

}
