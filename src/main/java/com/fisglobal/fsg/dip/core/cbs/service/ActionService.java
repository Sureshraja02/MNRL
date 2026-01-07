package com.fisglobal.fsg.dip.core.cbs.service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fisglobal.fsg.dip.core.config.MnrlLoadData;
import com.fisglobal.fsg.dip.core.service.CommonMethodUtils;
import com.fisglobal.fsg.dip.core.utils.Constants;
import com.fisglobal.fsg.dip.entity.MnrlCbsData_DAO;
import com.fisglobal.fsg.dip.entity.MnrlCountDetails_DAO;
import com.fisglobal.fsg.dip.entity.impl.MnrlCountDetailsImpl;
import com.fisglobal.fsg.dip.entity.repo.MnrlCbsDataRepo;

@Service
public class ActionService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ActionService.class);

	@Inject
	private MnrlCbsDataRepo cbsDataRepo;

	@Inject
	private MnrlDebitFreezeService mnrlDebitFreezeService;

	@Inject
	private MnrlCifBlockService mnrlCifBlockService;

	@Inject
	private MnrlMobileRemovalService mnrlMobileRemovalService;

	@Inject
	private MnrlAtrUploadService mnrlAtrUploadService;

	@Value("#{'${mnrl.debit.freeze.primaryacc}'.split(',')}")
	List<String> debitPrimaryFlagList;

	@Value("#{'${mnrl.debit.freeze.accttype}'.split(',')}")
	List<String> acctTypeList;
	
	@Inject
	private CommonMethodUtils commonMethodUtils;
	
	@Inject
	private MnrlCountDetailsImpl mnrlCountDetailsImpl;

	@Scheduled(cron = "${mnrl.action.cron:0 56 11 1/1 * ?}")
	public void processActionService() {
		// Date date = new Date();
		LOGGER.info("Action Service schedule started [{}]", LocalDateTime.now());
		
		List<MnrlCountDetails_DAO> completedList=mnrlCountDetailsImpl.getMnrlCountData(commonMethodUtils.getCurrentDateAsString(),Constants.COMPLETED,LocalDate.now());
		
		int completedCnt=completedList.size();
		
		if(completedCnt>0) {
		
		int pagingSize = 10;
		if (MnrlLoadData.propdetails.getAppProperty().getMnrlActionPagingSize() > 0) {
			pagingSize = MnrlLoadData.propdetails.getAppProperty().getMnrlActionPagingSize();
		}
		int threadSize = 3;
		if (MnrlLoadData.propdetails.getAppProperty().getMnrlActionThreadPoolSize() > 0) {
			threadSize = MnrlLoadData.propdetails.getAppProperty().getMnrlActionThreadPoolSize();
		}
		Pageable paging = PageRequest.of(0, pagingSize);
		Page<MnrlCbsData_DAO> MNRLDataPage = cbsDataRepo.getActionCbsData(paging, pagingSize);
		LOGGER.info("Total Number Of Pages[{}] [{}]", MNRLDataPage.getTotalPages(), MNRLDataPage.getContent().size());
		ExecutorService executorService = Executors.newFixedThreadPool(threadSize);
		do {
			MNRLDataPage.getContent().forEach(mnrlcbsentity -> {
				try {
					LOGGER.info("mnrlcbsentity [{}]", mnrlcbsentity.getMobileNo());
					CompletableFuture.supplyAsync(() -> callDebitFreeze(mnrlcbsentity), executorService)
							.thenApplyAsync(result -> callCifBlock(mnrlcbsentity), executorService)
							.thenApplyAsync(result -> callMobileRemoval(mnrlcbsentity), executorService)
							.thenApplyAsync(result -> retryException(mnrlcbsentity), executorService)
							.thenApplyAsync(result -> seggrateAtrData(mnrlcbsentity), executorService).get();
				} catch (Exception e) {
					LOGGER.error("Async Error", e);
				}
			});
			paging = MNRLDataPage.nextPageable();
		} while (MNRLDataPage.hasNext());
		}else {
			LOGGER.info("MNRL Download is in progress");
		}
		LOGGER.info("Action Service schedule ended [{}]", LocalDateTime.now());
	}

	public MnrlCbsData_DAO callDebitFreeze(MnrlCbsData_DAO cbsEntity) {
		// LOGGER.info("In Debit Block - acct type
		// [{}]",cbsEntity.getAcctType().substring(0, 2));
		if ((cbsEntity.getDebitFreezeFlag().equals("Y"))
				&& (debitPrimaryFlagList.contains(cbsEntity.getPrimaryAccountFlag()))
				&& (acctTypeList.contains(cbsEntity.getAcctType().substring(0, 2)))) {
			try {
				cbsEntity = mnrlDebitFreezeService.processDebitFreeze(cbsEntity);
				cbsDataRepo.save(cbsEntity);
			} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | JSONException
					| IOException e) {
				// TODO Auto-generated catch block
				LOGGER.error("Exception in debit freeze", e);
				// cbsEntity.setActionFlag("10");
				// cbsDataRepo.save(cbsEntity);
			}
		}
		// else {
		// LOGGER.info("action 3 [{}]",cbsEntity.getActionFlag());
		// cbsEntity.setActionFlag("3");
		// cbsDataRepo.save(cbsEntity);
		// }
		return cbsEntity;

	}

	public MnrlCbsData_DAO callCifBlock(MnrlCbsData_DAO cbsEntity) {
		LOGGER.info("In cif Block");
		if (cbsEntity.getCifBlockFlag().equals("Y") && cbsEntity.getActionFlag().equals("0")) {
			try {
				cbsEntity = mnrlCifBlockService.processCifBlock(cbsEntity);
				cbsDataRepo.save(cbsEntity);
			} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | JSONException
					| IOException e) {
				// TODO Auto-generated catch block
				LOGGER.error("Exception in cif block", e);
				// cbsEntity.setActionFlag("10");
				// cbsDataRepo.save(cbsEntity);
			}
		} /*
			 * else { // LOGGER.info("action 5 [{}]",cbsEntity.getActionFlag()); //
			 * cbsEntity.setActionFlag("5"); // cbsDataRepo.save(cbsEntity); }
			 */
		return cbsEntity;

	}

	public MnrlCbsData_DAO callMobileRemoval(MnrlCbsData_DAO cbsEntity) {
		LOGGER.info("In mobile removal");
		if (cbsEntity.getMobileRemovalFlag().equals("Y") && cbsEntity.getActionFlag().equals("0")) {
			try {
				cbsEntity = mnrlMobileRemovalService.processMobileRemoval(cbsEntity);
				cbsDataRepo.save(cbsEntity);
			} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | JSONException
					| IOException e) {
				// TODO Auto-generated catch block
				LOGGER.error("Exception in mobile removal", e);
				// cbsEntity.setActionFlag("10");
				// cbsDataRepo.save(cbsEntity);
			}
		} /*
			 * else { // LOGGER.info("action 7"); // cbsEntity.setActionFlag("7"); //
			 * cbsDataRepo.save(cbsEntity); }
			 */
		return cbsEntity;

	}

	public MnrlCbsData_DAO retryException(MnrlCbsData_DAO mnrlCbsEntity) {
		if (mnrlCbsEntity.getDebitFreezeFlag().equals("EX") || mnrlCbsEntity.getCifBlockFlag().equals("EX")
				|| mnrlCbsEntity.getMobileRemovalFlag().equals("EX")) {
			if (mnrlCbsEntity.getDebitFreezeFlag().equals("EX")) {
				LOGGER.info("retry debit freeze");
				mnrlCbsEntity.setDebitFreezeFlag("Y");
				mnrlCbsEntity.setActionFlag("0");
				cbsDataRepo.save(mnrlCbsEntity);
			}
			if (mnrlCbsEntity.getCifBlockFlag().equals("EX")) {
				LOGGER.info("retry cif block");
				mnrlCbsEntity.setCifBlockFlag("Y");
				mnrlCbsEntity.setActionFlag("0");
				cbsDataRepo.save(mnrlCbsEntity);
			}
			if (mnrlCbsEntity.getMobileRemovalFlag().equals("EX")) {
				LOGGER.info("retry mobile removal");
				mnrlCbsEntity.setMobileRemovalFlag("Y");
				mnrlCbsEntity.setActionFlag("0");
				cbsDataRepo.save(mnrlCbsEntity);
			}
		} else {
			mnrlCbsEntity.setActionFlag("77");
			cbsDataRepo.save(mnrlCbsEntity);
		}
		return mnrlCbsEntity;

	}

	public MnrlCbsData_DAO seggrateAtrData(MnrlCbsData_DAO mnrlCbsEntity) {
		if (mnrlCbsEntity.getActionFlag().equals("77")) {
			int countAtrCheck = cbsDataRepo.checkAtrUpload(mnrlCbsEntity.getMobileNo());
			// if (mnrlCbsEntity.getAtrUploadFlag().equals("Y") &&
			// mnrlCbsEntity.getActionFlag().equals("0")) {
			LOGGER.info("ATR check [{}]", countAtrCheck);
			if (countAtrCheck == 0 && mnrlCbsEntity.getAtrUploadFlag().equals("Y")) {
				LOGGER.info("upload ATR");
				try {
					//Ready for Upload
					mnrlCbsEntity.setAtrUploadFlag("R");
					// mnrlCbsEntity = mnrlAtrUploadService.processMnrlAtr(mnrlCbsEntity,atrCount);
					 cbsDataRepo.save(mnrlCbsEntity);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					LOGGER.info("Exception in MNRL ATR", e);
					// mnrlCbsEntity.setActionFlag("10");
					// cbsDataRepo.save(mnrlCbsEntity);
				}
			} else {
				if (mnrlCbsEntity.getAtrUploadFlag().equals("Y")) {
					mnrlCbsEntity.setAtrUploadFlag("E");
					mnrlCbsEntity.setMnrlAtrResponse("Action failed for mobile");
				}
				LOGGER.info("action 9");
				mnrlCbsEntity.setActionFlag("9");
				//mnrlCbsEntity.setAtrDateTime(commonMethodUtils.getCurrentDateTime());
				cbsDataRepo.save(mnrlCbsEntity);
			}

		}
		LOGGER.info("atrUploadFlag after response [{}]", mnrlCbsEntity.getAtrUploadFlag());
		return mnrlCbsEntity;
	}

}
