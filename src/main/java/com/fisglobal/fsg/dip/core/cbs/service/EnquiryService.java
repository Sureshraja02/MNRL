package com.fisglobal.fsg.dip.core.cbs.service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import org.joda.time.LocalDateTime;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fisglobal.fsg.dip.core.cbs.entity.MobileEnquiryResponseVo;
import com.fisglobal.fsg.dip.core.config.MnrlLoadData;
import com.fisglobal.fsg.dip.entity.MnrlCbsData_DAO;
import com.fisglobal.fsg.dip.entity.MnrlData_DAO;
import com.fisglobal.fsg.dip.entity.repo.MnrlCbsDataRepo;

@Service
public class EnquiryService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EnquiryService.class);

	@Inject
	private MnrlCbsDataRepo cbsDataRepo;

	@Inject
	private CbsAccService cbsAccService;

	@Inject
	private CbsCifService cbsCifService;

	@Scheduled(cron = "${mnrl.enq.cron:0 56 11 1/1 * ?}")
	public void processEnquiryService() {
		Date date = new Date();
		LOGGER.info("Enquiry Service schedule started [{}]", LocalDateTime.now());
		int pagingSize = 10;
		
		if (MnrlLoadData.propdetails.getAppProperty().getMnrlEnquiryPagingSize() > 0) {
		
			pagingSize = MnrlLoadData.propdetails.getAppProperty().getMnrlEnquiryPagingSize();
		}
		int threadSize = 3;
		
		if (MnrlLoadData.propdetails.getAppProperty().getMnrlEnquiryPoolSize() > 0) {
		
			threadSize = MnrlLoadData.propdetails.getAppProperty().getMnrlEnquiryPoolSize();
		}
		Pageable paging = PageRequest.of(0, pagingSize);
		Page<MnrlCbsData_DAO> MNRLDataPage = cbsDataRepo.getCbsData(paging, pagingSize);
		LOGGER.info("Total Number Of Pages[{}] [{}]", MNRLDataPage.getTotalPages(), MNRLDataPage.getContent().size());
		ExecutorService executorService = Executors.newFixedThreadPool(threadSize);
		do {
			MNRLDataPage.getContent().forEach(mnrlcbsentity -> {
				try {
					LOGGER.info("mnrlcbsentity [{}]", mnrlcbsentity.getMobileNo());
					CompletableFuture.supplyAsync(() -> accEnquiry(mnrlcbsentity), executorService)
							.thenApplyAsync(result -> cifEnquiry(mnrlcbsentity), executorService)
							.thenApplyAsync(result -> primaryAccFlagUpdate(mnrlcbsentity), executorService)
							// .thenApplyAsync(result -> debitFreezeFlagUpdate(mnrlcbsentity),
							// executorService)
							.get();
				} catch (Exception e) {
					LOGGER.error("Async Error", e);
				}
			});
			paging = MNRLDataPage.nextPageable();
		} while (MNRLDataPage.hasNext());
		LOGGER.info("Enquiry Service schedule ended [{}]", LocalDateTime.now());
	}

	public MnrlCbsData_DAO accEnquiry(MnrlCbsData_DAO mnrlentity) {
		// MnrlCbsData_DAO totalCbsData = new MnrlCbsData_DAO();
		LOGGER.info("cbs entity [{}]", mnrlentity.getMobileNo());
		try {
			return cbsAccService.processAccEnquiry(mnrlentity);
		} catch (KeyManagementException | NumberFormatException | JSONException | NoSuchAlgorithmException
				| KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // should return cbs entity
		return mnrlentity;
	}

	public MnrlCbsData_DAO cifEnquiry(MnrlCbsData_DAO mnrlentity) {
		// MnrlCbsData_DAO totalCbsData = new MnrlCbsData_DAO();
		LOGGER.info("Cbs Data acc[{}] cif[{}] mob[{}]", mnrlentity.getAccountNo(), mnrlentity.getCif(),
				mnrlentity.getMobileNo());
		try {
			return cbsCifService.processCifEnquiry(mnrlentity);
		} catch (KeyManagementException | NumberFormatException | JSONException | NoSuchAlgorithmException
				| KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // should return cbs entity
		return mnrlentity;
	}

	public MnrlCbsData_DAO primaryAccFlagUpdate(MnrlCbsData_DAO mnrlentity) {
		// MnrlCbsData_DAO totalCbsData = new MnrlCbsData_DAO();
		LOGGER.info("Cbs Data acc[{}] cif[{}] mob[{}]", mnrlentity.getAccountNo(), mnrlentity.getCif(),
				mnrlentity.getMobileNo());

		try {
			return cbsCifService.processPrimaryAccFlagUpdate(mnrlentity);
		} catch (KeyManagementException | NumberFormatException | JSONException | NoSuchAlgorithmException
				| KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // should return cbs entity
		return mnrlentity;
	}

}
