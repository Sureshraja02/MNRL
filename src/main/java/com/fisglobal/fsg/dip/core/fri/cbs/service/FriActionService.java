package com.fisglobal.fsg.dip.core.fri.cbs.service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
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

import com.fisglobal.fsg.dip.core.config.MnrlLoadData;
import com.fisglobal.fsg.dip.core.service.CommonMethodUtils;
import com.fisglobal.fsg.dip.core.utils.Constants;
import com.fisglobal.fsg.dip.entity.FriCbsData_DAO;
import com.fisglobal.fsg.dip.entity.FriCountDetails_DAO;
import com.fisglobal.fsg.dip.entity.impl.FriCountDetailsImpl;
import com.fisglobal.fsg.dip.entity.repo.FriCbsDataRepo;

@Service
public class FriActionService {

	private static final Logger LOGGER = LoggerFactory.getLogger(FriActionService.class);

	@Inject
	private FriCbsDataRepo cbsDataRepo;

	@Inject
	private FriCifBlockService friCifBlockService;

	@Inject
	private FriAtrUploadService friAtrUploadService;
	
	@Inject
	private CommonMethodUtils commonMethodUtils;
	
	@Inject
	private FriCountDetailsImpl friCountDetailsImpl;

	@Scheduled(cron = "${fri.action.cron:0 56 11 1/1 * ?}")
	public void processActionService() {

		
		LOGGER.info("FRI Action Service schedule started [{}]", LocalDateTime.now());
		
		List<FriCountDetails_DAO> completedList=friCountDetailsImpl.getFriCountData(commonMethodUtils.getCurrentDateAsString(),Constants.COMPLETED,LocalDate.now());
		
		int completedCnt=completedList.size();
		
		if(completedCnt>0) {

		int pagingSize = 10;
		if (MnrlLoadData.propdetails.getAppProperty().getFriActionPagingSize() > 0) {
			pagingSize = MnrlLoadData.propdetails.getAppProperty().getFriActionPagingSize();
		}
		int threadSize = 3;
		if (MnrlLoadData.propdetails.getAppProperty().getFriActionThreadPoolSize() > 0) {
			threadSize = MnrlLoadData.propdetails.getAppProperty().getFriActionThreadPoolSize();
		}
		LOGGER.info("Page size [{}],Thread Pool size [{}]", pagingSize, threadSize);
		Pageable paging = PageRequest.of(0, pagingSize);
		Page<FriCbsData_DAO> FRIDataPage = cbsDataRepo.getFriActionCbsData(paging, pagingSize);
		LOGGER.info("Total Number Of Pages[{}] [{}]", FRIDataPage.getTotalPages(), FRIDataPage.getContent().size());
		ExecutorService executorService = Executors.newFixedThreadPool(threadSize);
		do {
			FRIDataPage.getContent().forEach(fricbsentity -> {
				try {
					LOGGER.info("fricbsentity [{}]", fricbsentity.getMobileNo());
					CompletableFuture.supplyAsync(() -> CifBlock(fricbsentity), executorService)
							.thenApplyAsync(result -> UploadAtr(fricbsentity), executorService).get();
				} catch (Exception e) {
					LOGGER.error("Async Error", e);
				}
			});
			paging = FRIDataPage.nextPageable();
		} while (FRIDataPage.hasNext());
		}else {
			LOGGER.info("Fri Download is in progress");
		}
		LOGGER.info("FRI Action Service schedule ended [{}]", LocalDateTime.now());
	}
	

	public void processFriData(FriCbsData_DAO friCbsEntity) {
		// cif
		// atr
	}

	public FriCbsData_DAO CifBlock(FriCbsData_DAO friCbsEntity) {

		if (friCbsEntity.getCifBlockFlag().equals("Y")) {
			try {
				friCbsEntity = friCifBlockService.processCifBlock(friCbsEntity);
				cbsDataRepo.save(friCbsEntity);
			} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | JSONException
					| IOException e) {
				// TODO Auto-generated catch block
				LOGGER.error("Exception in fri cif block", e);
				// friCbsEntity.setActionFlag("10");
				// cbsDataRepo.save(friCbsEntity);
			}
		} else {
			friCbsEntity.setActionFlag("3");
			cbsDataRepo.save(friCbsEntity);
		}
		return friCbsEntity;

	}

	public FriCbsData_DAO UploadAtr(FriCbsData_DAO friCbsEntity) {

		if (friCbsEntity.getCifBlockFlag().equals("EX")) {
			LOGGER.info("retry cif block");
			friCbsEntity.setCifBlockFlag("Y");
			friCbsEntity.setActionFlag("0");
			cbsDataRepo.save(friCbsEntity);
		} else if (friCbsEntity.getAtrUploadFlag().equals("Y") && friCbsEntity.getCifBlockFlag().equals("C")) {
			try {
				//friCbsEntity = friAtrUploadService.processFriAtr(friCbsEntity);
				friCbsEntity.setAtrUploadFlag("R");
				friCbsEntity.setActionFlag("77");
				cbsDataRepo.save(friCbsEntity);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				LOGGER.info("Exception  in ATR block", e);
				// friCbsEntity.setActionFlag("10");
				// cbsDataRepo.save(friCbsEntity);
			}
		} else {
			friCbsEntity.setActionFlag("3");
			cbsDataRepo.save(friCbsEntity);
		}
		LOGGER.info("atrUploadFlag after response [{}]", friCbsEntity.getAtrUploadFlag());
		return friCbsEntity;

	}
}
