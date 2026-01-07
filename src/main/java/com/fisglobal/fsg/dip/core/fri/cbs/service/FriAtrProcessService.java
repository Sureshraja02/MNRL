package com.fisglobal.fsg.dip.core.fri.cbs.service;

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

import com.fisglobal.fsg.dip.core.cbs.service.ATRUploadService;
import com.fisglobal.fsg.dip.core.config.MnrlLoadData;
import com.fisglobal.fsg.dip.core.service.CommonMethodUtils;
import com.fisglobal.fsg.dip.entity.FriCbsData_DAO;
import com.fisglobal.fsg.dip.entity.MnrlCbsData_DAO;
import com.fisglobal.fsg.dip.entity.repo.FriCbsDataRepo;
import com.fisglobal.fsg.dip.request.entity.MnrlAtrData_VO;

@Service
public class FriAtrProcessService {

	private static final Logger LOGGER = LoggerFactory.getLogger(FriAtrProcessService.class);

	@Inject
	@Qualifier("FRI_ATR_REQ")
	private MessageSource friReq;

	@Inject
	private CommonMethodUtils commonMethodUtils;

	@Inject
	private FriCbsDataRepo cbsDataRepo;

	@Inject
	private FriAtrUploadService friAtrUploadService;

	@Scheduled(cron = "${fri.atr.cron:0 56 11 1/1 * ?}")
	public void processAtrService() {
		// Date date = new Date();
		LOGGER.info("FRI Atr Service schedule started [{}]", LocalDateTime.now());
		List<FriCbsData_DAO> cbsdatalist = new ArrayList<>();
		// List<MnrlCbsData_DAO> actionData =
		// mnrlCbsDataImpl.getMnrlCbsDataByActionFlag("0");
		// if (actionData.size() == 0) {
		List<MnrlAtrData_VO> atrdatalist = new ArrayList<>();
		int pagingSize = 10;
		if (MnrlLoadData.propdetails.getAppProperty().getFriATRPagingSize() > 0) {
			pagingSize = MnrlLoadData.propdetails.getAppProperty().getFriATRPagingSize();
		}
		int threadSize = 3;
		if (MnrlLoadData.propdetails.getAppProperty().getFriATRPoolSize() > 0) {
			threadSize = MnrlLoadData.propdetails.getAppProperty().getFriATRPoolSize();
		}
		// int atrCount = cbsDataRepo.totalAtrUpload();
		// LOGGER.info("atrCount [{}]", atrCount);

		LOGGER.info("Paging [{}],threadSize [{}]", pagingSize, threadSize);
		Pageable paging = PageRequest.of(0, pagingSize);
		Page<FriCbsData_DAO> FRIDataPage = cbsDataRepo.getAtrCbsData(paging, pagingSize);
		LOGGER.info("Total Number Of Pages[{}] [{}]", FRIDataPage.getTotalPages(), FRIDataPage.getContent().size());
		// ExecutorService executorService = Executors.newFixedThreadPool(threadSize);

		do {
			FRIDataPage.getContent().forEach(friCbsEntity -> {

				LOGGER.info("fricbsentity [{}]", friCbsEntity.getMobileNo());

				MnrlAtrData_VO atrdata = new MnrlAtrData_VO();

				String actionTaken = friReq.getMessage(friCbsEntity.getFriReason() + ".actiontaken", null, "1",
						Locale.US);
				String investigationDetails = friReq.getMessage(friCbsEntity.getFriReason() + ".investigation", null,
						"Digital Block done", Locale.US);
				String grievance = friReq.getMessage(friCbsEntity.getFriReason() + ".grievance", null, "No", Locale.US);

				String mobile = commonMethodUtils.getMobileNumberWithoutCC(friCbsEntity.getMobileNo());

				atrdata.setMobile_no(mobile);

				atrdata.setAction_taken(actionTaken);
				atrdata.setInvestigation_details(investigationDetails);

				atrdata.setDate_of_action(commonMethodUtils.toChangeDateFormat("yyyy-MM-dd HH:mm:ss", new Date()));

				// atrdata.setDisconnectionreason_id(atrList.get(i).getMnrl_disconnection_reason());
				atrdata.setGrievance_received(grievance);
				atrdatalist.add(atrdata);
				cbsdatalist.add(friCbsEntity);
			});
			try {
				if (atrdatalist.size() > 0) {
					friAtrUploadService.processFriAtr(atrdatalist,cbsdatalist);
				} else {
					LOGGER.info("No ATR Data to Upload");
				}

			} catch (Exception e) {
				LOGGER.error("Exception in FRI ATR", e);
				// e.printStackTrace();
			}

			atrdatalist.clear();
			cbsdatalist.clear();
			LOGGER.info("List cleared");
			paging = FRIDataPage.nextPageable();
		} while (FRIDataPage.hasNext());

		LOGGER.info("FRI Atr Service schedule ended [{}]", LocalDateTime.now());
	}

	public MnrlCbsData_DAO setAtrRequest(MnrlCbsData_DAO mnrlCbsEntity) {

		return mnrlCbsEntity;

	}

}
