package com.fisglobal.fsg.dip.core.cbs.service;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fisglobal.fsg.dip.core.config.MnrlLoadData;
import com.fisglobal.fsg.dip.core.service.CommonMethodUtils;
import com.fisglobal.fsg.dip.entity.MnrlCbsData_DAO;
import com.fisglobal.fsg.dip.entity.MnrlData_DAO;
import com.fisglobal.fsg.dip.entity.repo.MnrlCbsDataRepo;
import com.fisglobal.fsg.dip.entity.repo.MnrlDataRepo;

@Service
public class AccountService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);

	@Inject
	private MnrlDataRepo dataRepo;

	
	//public HashMap<String, String> cifBlockMap = new HashMap<String, String>();

	//public HashMap<String, String> atrMap = new HashMap<String, String>();

	

	/*
	 * private final Executor middlewareExecutor; private final Executor
	 * coreExecutor;
	 * 
	 * public AccountService(@Qualifier("coreExecutor") Executor coreExecutor,
	 * 
	 * @Qualifier("middleWareExecutor") Executor middleWareExecutor) {
	 * this.middlewareExecutor = middleWareExecutor; this.coreExecutor =
	 * coreExecutor; }
	 */
	@Inject
	private MnrlMobileEnquiryService mnrlMobileEnquiryService;

	@Scheduled(cron = "${mnrl.mob.enq.cron:0 56 11 1/1 * ?}")
	public void processAccountService() {
		
	//	Date date = new Date();
		LOGGER.info("Mobile Enquiry Service schedule started [{}]", LocalDateTime.now());
		int pagingSize = 10;
		if (MnrlLoadData.propdetails.getAppProperty().getMnrlMobileEnquiryPagingSize() > 0) {
			pagingSize = MnrlLoadData.propdetails.getAppProperty().getMnrlMobileEnquiryPagingSize();
		}
		int threadSize = 3;
		if (MnrlLoadData.propdetails.getAppProperty().getMnrlMobileEnquiryThreadPoolSize() > 0) {
			threadSize = MnrlLoadData.propdetails.getAppProperty().getMnrlMobileEnquiryThreadPoolSize();
		}
		Pageable paging = PageRequest.of(0, pagingSize);
		Page<MnrlData_DAO> MNRLDataPage = dataRepo.getMNRLData(paging, pagingSize);
		LOGGER.info("Total Number Of Pages[{}]", MNRLDataPage.getTotalPages());
		//ExecutorService executorService = Executors.newFixedThreadPool(threadSize);
		do {
			MNRLDataPage.getContent().forEach(mnrlentity -> {
				try {
					// .supplyAsync(() -> mobileEnquiry(mnrlentity), executorService)
					// .thenApplyAsync(result -> cifEnquiry(result), executorService)
					// .get();
					// CompletableFuture.supplyAsync(() -> mobileEnquiry(mnrlentity),
					// executorService)
					// .get();
					mnrlentity.setProcessFlag("T");
					dataRepo.save(mnrlentity);
					LOGGER.info("mobile Number [{}] ,Process Flag [{}]",mnrlentity.getMobileNo(),mnrlentity.getProcessFlag());
					mnrlMobileEnquiryService.processMobileEnquriy(mnrlentity);
					//cifBlockMap.clear();
					//atrMap.clear();
				} catch (Exception e) {
					LOGGER.error("Async Error", e);
				}
			});

			paging = MNRLDataPage.nextPageable();
			
		} while (MNRLDataPage.hasNext());
		LOGGER.info("Mobile Enquiry Service schedule ended [{}]", LocalDateTime.now());

	}

	

	public MnrlData_DAO cifEnquiry(MnrlData_DAO result) {
		LOGGER.info("Cif Enquiry [{}]", result.getMobileNo());
		return result;
	}

	public void accountEnquiry(MnrlCbsData_DAO cbsDataEntity) {
		// AccountEnquiryResponse accResponse =new AccountEnquiryResponse();
		// accResponse=accountEnquiryService.getAccountDetails(cbsDataEntity.)
	}

}
