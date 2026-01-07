package com.fisglobal.fsg.dip.core.cbs.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fisglobal.fsg.dip.core.config.MnrlLoadData;
import com.fisglobal.fsg.dip.core.service.CommonMethodUtils;
import com.fisglobal.fsg.dip.entity.MnrlCbsData_DAO;
import com.fisglobal.fsg.dip.entity.impl.MnrlCbsDataImpl;
import com.fisglobal.fsg.dip.entity.repo.MnrlCbsDataRepo;
import com.fisglobal.fsg.dip.request.entity.MnrlAtrData_VO;

@Service
public class ATRUploadService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ATRUploadService.class);

	@Inject
	private MnrlCbsDataRepo cbsDataRepo;

	@Inject
	private MnrlAtrUploadService mnrlAtrUploadService;

	@Inject
	private CommonMethodUtils commonMethodUtils;

	@Inject
	@Qualifier("MNRL_ATR_REQ")
	private MessageSource mnrlReq;

	@Inject
	private MnrlCbsDataImpl mnrlCbsDataImpl;

	@Scheduled(cron = "${mnrl.atr.cron:0 56 11 1/1 * ?}")
	public void processAtrService() {
		// Date date = new Date();
		LOGGER.info("Atr Service schedule started [{}]", LocalDateTime.now());
		//List<MnrlCbsData_DAO> actionData = mnrlCbsDataImpl.getMnrlCbsDataByActionFlag("0");
		//if (actionData.size() == 0) {
			List<MnrlAtrData_VO> atrdatalist = new ArrayList<>();
			List<MnrlCbsData_DAO> cbsdatalist = new ArrayList<>();
			int pagingSize = 10;
			if (MnrlLoadData.propdetails.getAppProperty().getMnrlATRPagingSize() > 0) {
				pagingSize = MnrlLoadData.propdetails.getAppProperty().getMnrlATRPagingSize();
			}
			int threadSize = 3;
			if (MnrlLoadData.propdetails.getAppProperty().getMnrlATRPoolSize() > 0) {
				threadSize = MnrlLoadData.propdetails.getAppProperty().getMnrlATRPoolSize();
			}
			// int atrCount = cbsDataRepo.totalAtrUpload();
			// LOGGER.info("atrCount [{}]", atrCount);

			LOGGER.info("Paging [{}],threadSize [{}]", pagingSize, threadSize);
			Pageable paging = PageRequest.of(0, pagingSize);
			Page<MnrlCbsData_DAO> MNRLDataPage = cbsDataRepo.getAtrCbsData(paging, pagingSize);
			LOGGER.info("Total Number Of Pages[{}] [{}]", MNRLDataPage.getTotalPages(),
					MNRLDataPage.getContent().size());
			// ExecutorService executorService = Executors.newFixedThreadPool(threadSize);

			do {
				MNRLDataPage.getContent().forEach(mnrlcbsentity -> {

					LOGGER.info("mnrlcbsentity [{}]", mnrlcbsentity.getMobileNo());

					MnrlAtrData_VO atrdata = new MnrlAtrData_VO();
					String actionTaken = mnrlReq.getMessage(mnrlcbsentity.getMnrlReason() + ".actiontaken", null, "1",
							Locale.US);
					String investigationDetails = mnrlReq.getMessage(mnrlcbsentity.getMnrlReason() + ".investigation",
							null, "Digital Block done", Locale.US);
					String grievance = mnrlReq.getMessage(mnrlcbsentity.getMnrlReason() + ".grievance", null, "No",
							Locale.US);

					String mobile = commonMethodUtils.getMobileNumberWithoutCC(mnrlcbsentity.getMobileNo());

					atrdata.setMobile_no(mobile);
					atrdata.setAction_taken(actionTaken);
					atrdata.setInvestigation_details(investigationDetails);

					atrdata.setDate_of_action(commonMethodUtils.toChangeDateFormat("yyyy-MM-dd HH:mm:ss", new Date()));

					atrdata.setDisconnectionreason_id(mnrlcbsentity.getMnrlReason());
					atrdata.setGrievance_received(grievance);
					atrdatalist.add(atrdata);
					cbsdatalist.add(mnrlcbsentity);

				});
				try {
					if (atrdatalist.size() > 0) {
						mnrlAtrUploadService.processMnrlAtr(atrdatalist,cbsdatalist);
					} else {
						LOGGER.info("No ATR Data to Upload");
					}

				} catch (Exception e) {
					LOGGER.error("Exception in MNRL ATR", e);
					// e.printStackTrace();
				}
				
				atrdatalist.clear();
				cbsdatalist.clear();
				LOGGER.info("List cleared");
				paging = MNRLDataPage.nextPageable();
			} while (MNRLDataPage.hasNext());
			
		
		LOGGER.info("Atr Service schedule ended [{}]", LocalDateTime.now());
	}

	/*
	 * public MnrlCbsData_DAO UploadAtr(MnrlCbsData_DAO mnrlCbsEntity,int atrCount)
	 * { int countAtrCheck=cbsDataRepo.checkAtrUpload(mnrlCbsEntity.getMobileNo());
	 * // if (mnrlCbsEntity.getAtrUploadFlag().equals("Y") &&
	 * mnrlCbsEntity.getActionFlag().equals("0")) {
	 * LOGGER.info("ATR check [{}]",countAtrCheck); if(countAtrCheck==0 &&
	 * mnrlCbsEntity.getAtrUploadFlag().equals("Y")) { LOGGER.info("upload ATR");
	 * try {
	 * 
	 * 
	 * mnrlCbsEntity = mnrlAtrUploadService.processMnrlAtr(mnrlCbsEntity,atrCount);
	 * //cbsDataRepo.save(mnrlCbsEntity); } catch (Exception e) { // TODO
	 * Auto-generated catch block LOGGER.info("Exception in MNRL ATR", e); //
	 * mnrlCbsEntity.setActionFlag("10"); // cbsDataRepo.save(mnrlCbsEntity); } }
	 * else { if(mnrlCbsEntity.getAtrUploadFlag().equals("Y")) {
	 * mnrlCbsEntity.setAtrUploadFlag("E");
	 * mnrlCbsEntity.setMnrlAtrResponse("Action failed for mobile"); }
	 * LOGGER.info("action 9"); mnrlCbsEntity.setActionFlag("9");
	 * cbsDataRepo.save(mnrlCbsEntity); } return mnrlCbsEntity;
	 * 
	 * }
	 */

	public MnrlCbsData_DAO setAtrRequest(MnrlCbsData_DAO mnrlCbsEntity) {

		return mnrlCbsEntity;

	}

}
