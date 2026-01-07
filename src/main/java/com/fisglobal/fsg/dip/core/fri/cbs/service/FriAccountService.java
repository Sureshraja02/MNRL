package com.fisglobal.fsg.dip.core.fri.cbs.service;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fisglobal.fsg.dip.core.config.MnrlLoadData;
import com.fisglobal.fsg.dip.entity.FriData_DAO;
import com.fisglobal.fsg.dip.entity.repo.FriDataRepo;

@Service
public class FriAccountService {

	private static final Logger LOGGER = LoggerFactory.getLogger(FriAccountService.class);

	@Inject
	private FriDataRepo dataRepo;

	
	
	@Inject
	private FriMobileEnquiryService friMobileEnquiryService;

	//public HashMap<String, String> cifBlockMap = new HashMap<String, String>();

	//public HashMap<String, String> atrMap = new HashMap<String, String>();

	@Scheduled(cron = "${fri.mob.enq.cron:0 56 11 1/1 * ?}")
	public void processFriAccountService() {
		
	//	Date date = new Date();
		LOGGER.info("FRI Mobile Enquiry Service schedule started [{}]", LocalDateTime.now());
		
		int pagingSize = 10;
		if (MnrlLoadData.propdetails.getAppProperty().getFriMobileEnquiryPagingSize() > 0) {
			pagingSize = MnrlLoadData.propdetails.getAppProperty().getFriMobileEnquiryPagingSize();
		}
		int threadSize = 3;
		if (MnrlLoadData.propdetails.getAppProperty().getFriMobileEnquiryThreadPoolSize() > 0) {
			threadSize = MnrlLoadData.propdetails.getAppProperty().getFriMobileEnquiryThreadPoolSize();
		}
		LOGGER.info("Page size [{}],Thread Pool size [{}]",pagingSize,threadSize);
		Pageable paging = PageRequest.of(0, pagingSize);
		Page<FriData_DAO> FRIDataPage = dataRepo.getFRIData(paging,pagingSize);
		LOGGER.info("Total Number Of Pages[{}]", FRIDataPage.getTotalPages());
		//ExecutorService executorService = Executors.newFixedThreadPool(threadSize);
		do {
			FRIDataPage.getContent().forEach(frientity -> {
				try {
					// .supplyAsync(() -> mobileEnquiry(mnrlentity), executorService)
					// .thenApplyAsync(result -> cifEnquiry(result), executorService)
					// .get();
					frientity.setProcessFlag("T");
					dataRepo.save(frientity);
					LOGGER.info("mobile Number [{}] ,Process Flag [{}]",frientity.getMobileNo(),frientity.getProcessFlag());
					friMobileEnquiryService.processMobileEnquriy(frientity);
					//cifBlockMap.clear();
					//atrMap.clear();
				} catch (Exception e) {
					LOGGER.error("Async Error", e);
				}
			});

			paging = FRIDataPage.nextPageable();
		} while (FRIDataPage.hasNext());
		LOGGER.info("FRI Mobile Enquiry Service schedule ended [{}]", LocalDateTime.now());
	}

	/*public FriCbsData_DAO mobileEnquiry(FriData_DAO frientity) throws KeyManagementException, ClientProtocolException,
			NoSuchAlgorithmException, KeyStoreException, JSONException, IOException {
		FriCbsData_DAO totalCbsData = new FriCbsData_DAO();
		List<FriCbsData_DAO> cbsDataList = new ArrayList<>();
		MobileEnquiryResponseVo mobileEnquiryResponseVo = new MobileEnquiryResponseVo();
		String countryCode = commonMethodUtils.getCountryCode();
		boolean defaultFlag = true;
		LOGGER.info("Mobile Enquiry [{}]", frientity);
		FriCbsData_DAO mnrlCbsDataEntity = new FriCbsData_DAO();
		// frientity.setProcessFlag("Y");

		String mobileNo=commonMethodUtils.getMobileNumberWithoutCC(frientity.getMobileNo());
		
		mobileEnquiryResponseVo = mobileNumberEnquiryService.getCIFAccountDetails(mobileNo, countryCode,"FRI");

		if (mobileEnquiryResponseVo != null) {
			frientity.setProcessFlag("Y");
			if (mobileEnquiryResponseVo.getCIFAccountEnqResponse().getBody().getPayload().getCollection().getCIFNo()
					.size() > 0) {
				List<String> cif = mobileEnquiryResponseVo.getCIFAccountEnqResponse().getBody().getPayload()
						.getCollection().getCIFNo();
				for (int i = 0; i < cif.size(); i++) {
					FriCbsData_DAO friCbscifEntity = new FriCbsData_DAO();
					friCbscifEntity.setMobileNo(frientity.getMobileNo());
					friCbscifEntity.setCif(cif.get(i));
					// mnrlCbscifEntity.setCifEnquiryFlag("2");
					friCbscifEntity.setAccountNo("777777777777");
					friCbscifEntity.setProcessFlag("0");
					friCbscifEntity.setFriReason(frientity.getFriIndicator());
					friCbscifEntity = updateFlagService.setFriFlags(friCbscifEntity, defaultFlag);
					friCbscifEntity.setActionFlag("0");
					LOGGER.info("CIF Data [{}]", friCbscifEntity.toString());
					cbsDataList.add(friCbscifEntity);
					// cbsDataRepo.save(mnrlCbscifEntity);

				}
			}
			// cbsDataRepo.saveAll(cbsDataList);
			if (mobileEnquiryResponseVo.getCIFAccountEnqResponse().getBody().getPayload().getCollection().getAccountNo()
					.size() > 0) {
				List<String> acc = mobileEnquiryResponseVo.getCIFAccountEnqResponse().getBody().getPayload()
						.getCollection().getAccountNo();
				for (int i = 0; i < acc.size(); i++) {
					FriCbsData_DAO friCbsEntity = new FriCbsData_DAO();
					friCbsEntity.setMobileNo(frientity.getMobileNo());
					friCbsEntity.setAccountNo(acc.get(i));
					friCbsEntity.setCif("777777777777");
					// mnrlCbsEntity.setAccEnquiryFlag("1");
					friCbsEntity.setProcessFlag("0");
					friCbsEntity.setFriReason(frientity.getFriIndicator());
					friCbsEntity = updateFlagService.setFriFlags(friCbsEntity, defaultFlag);
					friCbsEntity.setActionFlag("0");
					LOGGER.info("Account Data [{}]", friCbsEntity.toString());
					cbsDataList.add(friCbsEntity);
					// cbsDataRepo.save(mnrlCbsEntity);
				}
			}
			// cbsDataList.add(mnrlCbsEntity);
			cbsDataRepo.saveAll(cbsDataList);
			dataRepo.save(frientity);

			for (int i = 0; i < cbsDataList.size(); i++) {

				totalCbsData.setMobileNo(cbsDataList.get(i).getMobileNo());
				totalCbsData.setCif(cbsDataList.get(i).getCif());
				totalCbsData.setAccountNo(cbsDataList.get(i).getAccountNo());
			}
		} else {
			frientity.setProcessFlag("Y");
			dataRepo.save(frientity);
			LOGGER.info("No details found  for the mobile number [{}]", frientity.getMobileNo());
		}

// mobile enquiry call and save the respomse in cbs table
// debit freeze call if mobile enquiry sucess and LEA in mnrl data

		return totalCbsData;// should return cbs entity
	}*/
}
