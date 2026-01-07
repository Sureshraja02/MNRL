package com.fisglobal.fsg.dip.core.cbs.service;

import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
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

import com.fisglobal.fsg.dip.core.cbs.entity.AccountEnquiryResponse;
import com.fisglobal.fsg.dip.core.cbs.entity.CustomerDetail;
import com.fisglobal.fsg.dip.core.service.CommonMethodUtils;
import com.fisglobal.fsg.dip.entity.MnrlCbsData_DAO;
import com.fisglobal.fsg.dip.entity.impl.MnrlCbsDataImpl;
import com.fisglobal.fsg.dip.entity.repo.MnrlCbsDataRepo;

@Service
public class CbsAccService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CbsAccService.class);

	@Inject
	private AccountEnquiryService accountEnquiryService;

	@Inject
	private MnrlCbsDataRepo cbsDataRepo;

	@Inject
	private UpdateFlagService updateFlagService;

	@Value("${error.invalid.header:Invalid Header}")
	private String invalidHeader;

	@Inject
	private CommonMethodUtils commonMethodUtils;

	@Inject
	private MnrlCbsDataImpl mnrlCbsImpl;

	public MnrlCbsData_DAO processAccEnquiry(MnrlCbsData_DAO cbsData)
			throws KeyManagementException, NumberFormatException, ClientProtocolException, JSONException,
			NoSuchAlgorithmException, KeyStoreException, IOException {
		boolean defaultFlag = false;
		AccountEnquiryResponse accResponse = new AccountEnquiryResponse();
		if ("777777777777".equals(cbsData.getCif())) {

			BigInteger bigInt = new BigInteger(cbsData.getAccountNo());
			cbsData.setAccountEnquiryDateTime(LocalDateTime.now());
			cbsDataRepo.save(cbsData);
			accResponse = accountEnquiryService.getAccountDetails(bigInt, "MNRL");
			LocalDateTime date=LocalDateTime.now();
			cbsData.setAccountEnquiryDateTime(date);
			if (!(Optional.ofNullable(accResponse.getcIFAssociatedAccountEnquiryResponse()).isPresent()
					|| Optional.ofNullable(accResponse.getErrorResponse()).isPresent())) {
				cbsData.setProcessFlag("0");
				cbsDataRepo.save(cbsData);
				// mnrlCbsEntity.setActionFlag("0");
			}

			Optional.ofNullable(accResponse.getcIFAssociatedAccountEnquiryResponse()).ifPresent(m -> {
				Optional.ofNullable(m.getBody()).ifPresent(n -> {
					Optional.ofNullable(n.getPayload()).ifPresent(o -> {
						if (o.getCustomerDetails().size() > 0) {

							List<CustomerDetail> cifdetails = o.getCustomerDetails();
							// MnrlCbsDataPK id = new MnrlCbsDataPK();
							// id.setMobileNo(cbsData.getMobileNo());
							// id.setCif(cbsData.getCif());
							// id.setAccountNo(cbsData.getAccountNo());

							// MnrlCbsData_DAO existObj = cbsDataRepo.getOne(id);
							
							// if (existObj.isPresent()) {
							// existObj.setAccountNo(cbsData.getAccountNo());
							// existObj.setProcessFlag("1");
							// existObj.setCif(cifdetails.get(0).getCustomerNo());
							// cbsDataRepo.save(existObj);
							// }

							// cbsData.setCif(cifdetails.get(0).getCustomerNo());
							// cbsData.setProcessFlag("1");
							// cbsDataRepo.save(cbsData);
							for (int i = 1; i < cifdetails.size(); i++) {
								// MnrlCbsDataPK id = new MnrlCbsDataPK();
								// id.setMobileNo(cbsData.getMobileNo());
								// id.setCif(cifdetails.get(i).getCustomerNo());
								// id.setAccountNo(cbsData.getAccountNo());
								// id.setUuid(cbsData.getUuid());

								LOGGER.info("ACC [{}] ,CIF [{}] , MOB [{}] UUID [{}]", cbsData.getAccountNo(),
										cifdetails.get(i).getCustomerNo(), cbsData.getMobileNo(), cbsData.getUuid());
								MnrlCbsData_DAO obj = mnrlCbsImpl.getMnrlCbsData(cbsData.getMobileNo(),
										cifdetails.get(i).getCustomerNo(), cbsData.getAccountNo(), cbsData.getUuid());
								if (obj != null) {
									cbsData.setAccountNo(cbsData.getAccountNo());
									cbsData.setProcessFlag("0");
									cbsData.setCif(cifdetails.get(i).getCustomerNo());
									cbsData.setAccEnquiryFlag("Y");
									cbsDataRepo.save(cbsData);
								} else {
									boolean mobileRemovalFlag = true;
									MnrlCbsData_DAO cbsNewData = new MnrlCbsData_DAO();
									cbsNewData.setUuid(commonMethodUtils.getUuid());
									cbsNewData.setDataUuid(cbsData.getDataUuid());
									cbsNewData.setMobileNo(cbsData.getMobileNo());
									cbsNewData.setCif(cifdetails.get(i).getCustomerNo());
									cbsNewData.setAccountNo(cbsData.getAccountNo());
									cbsNewData.setMnrlReason(cbsData.getMnrlReason());
									// cbsNewData = updateFlagService.setFlags(cbsNewData,
									// defaultFlag,mobileRemovalFlag);
									cbsNewData = updateFlagService.setFlags(cbsNewData, mobileRemovalFlag);
									cbsNewData.setActionFlag("0");
									cbsNewData.setAccEnquiryFlag("Y");
									cbsNewData.setProcessFlag("0");
									cbsNewData.setAccountEnquiryDateTime(date);
									cbsDataRepo.save(cbsNewData);
								}
							}
							
							LOGGER.info("Cif No [{}] [{}]", cbsData.getCif(), cbsData.getMnrlReason());
							int updateRows = cbsDataRepo.updateCifCbsData(cbsData.getAccountNo(), cbsData.getCif(),
									cbsData.getMobileNo(), cifdetails.get(0).getCustomerNo(), "0", cbsData.getUuid(),date);
							LOGGER.info("Updated count[{}]", updateRows);

						} else {
							cbsData.setProcessFlag("5");
							cbsData.setAccEnquiryFlag("Y");
							cbsData.setActionFlag("1");
							cbsDataRepo.save(cbsData);
						}
					});
				});
			});

			Optional.ofNullable(accResponse.getErrorResponse()).ifPresent(a -> {
				Optional.ofNullable(a.getAdditionalinfo()).ifPresent(b -> {
					if (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepText())
							&& a.getAdditionalinfo().getExcepText().equals(invalidHeader)) {
						LOGGER.info("Error [{}]", a.getAdditionalinfo().getExcepText());
						cbsData.setProcessFlag("0");
						cbsDataRepo.save(cbsData);
					} else {

						if (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepMetaData())) {
							cbsData.setAccEnquiryFlag("E");
							cbsData.setAccEnquiryResponse(a.getAdditionalinfo().getExcepMetaData());
							cbsData.setProcessFlag("20");
							cbsDataRepo.save(cbsData);
							LOGGER.info("Acc enquiry EMetadata [{}]", a.getAdditionalinfo().getExcepMetaData());
						} else if (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepText())) {
							cbsData.setAccEnquiryFlag("E");
							cbsData.setAccEnquiryResponse(a.getAdditionalinfo().getExcepText());
							cbsData.setProcessFlag("20");
							cbsDataRepo.save(cbsData);
							LOGGER.info("Acc enquiry EText [{}]", a.getAdditionalinfo().getExcepText());
						} else if (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepCode())) {
							cbsData.setAccEnquiryFlag("E");
							cbsData.setAccEnquiryResponse(a.getAdditionalinfo().getExcepCode());
							cbsData.setProcessFlag("20");
							cbsDataRepo.save(cbsData);
							LOGGER.info("Acc enquiry Ecode [{}]", a.getAdditionalinfo().getExcepCode());
						}

					}
				});
			});

			/*
			 * if (accResponse != null) { List<CustomerDetail> cifdetails =
			 * accResponse.getcIFAssociatedAccountEnquiryResponse().getBody()
			 * .getPayload().getCustomerDetails(); // MnrlCbsDataPK id = new
			 * MnrlCbsDataPK(); // id.setMobileNo(cbsData.getMobileNo()); //
			 * id.setCif(cbsData.getCif()); // id.setAccountNo(cbsData.getAccountNo());
			 * 
			 * // MnrlCbsData_DAO existObj = cbsDataRepo.getOne(id);
			 * LOGGER.info("Cif No [{}] [{}]", cbsData.getCif(), cbsData.getMnrlReason());
			 * int updateRows = cbsDataRepo.updateCifCbsData(cbsData.getAccountNo(),
			 * cbsData.getCif(), cbsData.getMobileNo(), cifdetails.get(0).getCustomerNo(),
			 * "0"); LOGGER.info("Updated count[{}]", updateRows); // if
			 * (existObj.isPresent()) { // existObj.setAccountNo(cbsData.getAccountNo()); //
			 * existObj.setProcessFlag("1"); //
			 * existObj.setCif(cifdetails.get(0).getCustomerNo()); //
			 * cbsDataRepo.save(existObj); // }
			 * 
			 * // cbsData.setCif(cifdetails.get(0).getCustomerNo()); //
			 * cbsData.setProcessFlag("1"); // cbsDataRepo.save(cbsData); for (int i = 1; i
			 * < cifdetails.size(); i++) { MnrlCbsDataPK id = new MnrlCbsDataPK();
			 * id.setMobileNo(cbsData.getMobileNo());
			 * id.setCif(cifdetails.get(i).getCustomerNo());
			 * id.setAccountNo(cbsData.getAccountNo());
			 * 
			 * Optional<MnrlCbsData_DAO> obj = cbsDataRepo.findById(id); if
			 * (obj.isPresent()) { cbsData.setAccountNo(cbsData.getAccountNo());
			 * cbsData.setProcessFlag("0");
			 * cbsData.setCif(cifdetails.get(i).getCustomerNo());
			 * cbsData.setAccEnquiryFlag("Y"); cbsDataRepo.save(cbsData); } else { boolean
			 * mobileRemovalFlag=true; MnrlCbsData_DAO cbsNewData = new MnrlCbsData_DAO();
			 * cbsNewData.setMobileNo(cbsData.getMobileNo());
			 * cbsNewData.setCif(cifdetails.get(i).getCustomerNo());
			 * cbsNewData.setAccountNo(cbsData.getAccountNo());
			 * cbsNewData.setMnrlReason(cbsData.getMnrlReason()); cbsNewData =
			 * updateFlagService.setFlags(cbsNewData, defaultFlag,mobileRemovalFlag);
			 * cbsNewData.setActionFlag("0"); cbsNewData.setAccEnquiryFlag("Y");
			 * cbsNewData.setProcessFlag("0"); cbsDataRepo.save(cbsNewData); } }
			 * 
			 * } else { cbsData.setProcessFlag("5"); cbsData.setAccEnquiryFlag("Y");
			 * cbsData.setActionFlag("1"); cbsDataRepo.save(cbsData); }
			 */
		}
		return cbsData;

	}

}
