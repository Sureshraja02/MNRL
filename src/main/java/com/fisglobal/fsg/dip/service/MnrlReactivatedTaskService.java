package com.fisglobal.fsg.dip.service;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import com.fisglobal.fsg.dip.core.config.MnrlLoadData;
import com.fisglobal.fsg.dip.core.convertor.MNRLDecryptionUtils;
import com.fisglobal.fsg.dip.entity.ReactivatedData_DAO;
import com.fisglobal.fsg.dip.entity.ReactivatedReqData_DAO;
import com.fisglobal.fsg.dip.request.entity.MNRLDataBody;
import com.fisglobal.fsg.dip.request.entity.MNRLDataPayload;
import com.fisglobal.fsg.dip.request.entity.MNRLFetchDataRequest;
import com.fisglobal.fsg.dip.request.entity.MNRLHeaders;
import com.fisglobal.fsg.dip.request.entity.MnrlReactivatedDataRequest;
import com.fisglobal.fsg.dip.request.entity.MnrlTask_Vo;
import com.fisglobal.fsg.dip.response.entity.MnrlDataResponse;
import com.fisglobal.fsg.dip.response.entity.MrnlDecryptedData_VO;

@Component
@Scope("prototype")
public class MnrlReactivatedTaskService implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(MnrlReactivatedTaskService.class);

	private MnrlTask_Vo mrnlTaskVo;

	@Inject
	private MnrlReactivatedDataService mnrlReactivatedDataService;

	@Inject
	private MnrlReactivatedService mrnlReactivatedService;

	@Inject
	private MNRLDecryptionUtils mrnlDecryptionUtils;

	@Inject
	private TaskScheduler datatask;

	// private long timeOut = 30000;

	@Inject
	private ApplicationContext context;

	@Inject
	private MnrlAuthService mnrlAuthService;

	@Override
	public void run() {
		int rateLimit = MnrlLoadData.propdetails.getAppProperty().getReactivatedReqCount();
		try {
			String token = mnrlAuthService.getMnrlAuthtoken(mrnlReactivatedService.getMnrlAuth());
			for (int j = mrnlTaskVo.getFromCount() + 1; j <= mrnlTaskVo.getFromCount() + rateLimit; j++) {
				MnrlReactivatedDataRequest mnrlReactivatedDataRequest = new MnrlReactivatedDataRequest();
				MNRLFetchDataRequest mnrlFetchData_Request = new MNRLFetchDataRequest();
				MNRLDataBody body = new MNRLDataBody();
				MNRLHeaders headers = new MNRLHeaders();
				MNRLDataPayload payload = new MNRLDataPayload();
				payload.setBankId(MnrlLoadData.tokenReqDetails.getBankId());
				payload.setOffset(mrnlTaskVo.getOffLimit() * (j - 1));
				payload.setDate(mrnlTaskVo.getReqdate());
				if (mrnlTaskVo.getPartialLimit() > 0 && j == mrnlTaskVo.getTotalSize()) {
					payload.setCount(mrnlTaskVo.getPartialLimit());

				} else {
					payload.setCount(mrnlTaskVo.getOffLimit());
				}
				headers.setAuthorization(token);
				body.setPayload(payload);
				mnrlFetchData_Request.setBody(body);
				mnrlFetchData_Request.setHeaders(headers);
				mnrlReactivatedDataRequest.setMNRLReactiveData_Request(mnrlFetchData_Request);
				LOGGER.info("Reactivated Data request [{}]", MnrlLoadData.gson.toJson(mnrlReactivatedDataRequest));
				ReactivatedReqData_DAO reactivatedReqData_DAO = mrnlReactivatedService
						.saveReactivatedRequestData(mnrlReactivatedDataRequest);
				MnrlDataResponse mnrlresponse = new MnrlDataResponse();
				String resData = mnrlReactivatedDataService.getMnrlReactivatedData(mnrlReactivatedDataRequest);
				if ((StringUtils.isNotBlank(resData) && resData.contains("!_!"))) {
					String errorarray[] = resData.split("!_!");
					LOGGER.info("Response Failure with status code [{}] Response [{}]", errorarray[0], errorarray[1]);
					if (errorarray[0].equals("403")) {
						token = mnrlAuthService.getMnrlAuthtoken(mrnlReactivatedService.getMnrlAuth());
						j = j - 1;
					} else {
						LOGGER.info("retry block");
						MnrlTask_Vo task_vo = new MnrlTask_Vo();
						task_vo.setFromCount(j - 1);

						task_vo.setOffLimit(mrnlTaskVo.getOffLimit());
						task_vo.setOffsetNumber(j - 1);
						task_vo.setRateLimit(mrnlTaskVo.getRateLimit());
						task_vo.setTotalSize(mrnlTaskVo.getTotalSize());
						task_vo.setReactivatedDataListtotal(mrnlTaskVo.getReactivatedDataListtotal());
						task_vo.setReqdate(mrnlTaskVo.getReqdate());
						if (mrnlTaskVo.getPartialLimit() > 0) {
							task_vo.setPartialLimit(mrnlTaskVo.getPartialLimit());
						}
						Date date = new Date(System.currentTimeMillis()
								+ MnrlLoadData.propdetails.getAppProperty().getReactivatedRetryIntervalTime());
						MnrlReactivatedTaskService service = context.getBean(MnrlReactivatedTaskService.class);
						service.setMrnlTaskVo(task_vo);
						datatask.schedule(service, date);
						break;
					}
				} else {
					mnrlresponse = MnrlLoadData.gson.fromJson(resData, MnrlDataResponse.class);

					// LOGGER.debug("Encrypted Key [{}] Encrypted Data[{}], ",
					// mnrlresponse.getEncryptedKey(),
					// mnrlresponse.getEncryptedData());
					// MNRLDecryptionUtils obj = new MNRLDecryptionUtils();

					String AESKey = mrnlDecryptionUtils.getDecryptKey(mnrlresponse.getEncryptedKey());
					String decryptData = mrnlDecryptionUtils.decrypt(AESKey, mnrlresponse.getEncryptedData());

					// LOGGER.debug("Decrypted Key [{}] Decrypted Data[{}], ", AESKey, decryptData);

					MrnlDecryptedData_VO decryptDataObj = MnrlLoadData.gson.fromJson(decryptData,
							MrnlDecryptedData_VO.class);

					LOGGER.info("Decrypt Data [{}]", decryptDataObj.getData().size());

					List<ReactivatedData_DAO> reactivatedDataList = mrnlReactivatedService.saveReactivatedData(
							decryptDataObj, String.valueOf(reactivatedReqData_DAO.getId()), payload.getDate());

					mrnlTaskVo.getReactivatedDataListtotal().addAll(reactivatedDataList);

					LOGGER.info("Reactivated Data Saved successfully");
					if (j == mrnlTaskVo.getTotalSize()) {
						LOGGER.info("total export size [{}}", mrnlTaskVo.getReactivatedDataListtotal().size());
						// mrnlService.exportFile(mrnlTaskVo.getMnrlDataListtotal());
						mrnlTaskVo.getReactivatedDataListtotal().clear();
						break;
					}
					LOGGER.info("Task J values [{}][{}]", j, mrnlTaskVo.getTotalSize());
					if (j == mrnlTaskVo.getFromCount() + rateLimit) {
						LOGGER.info("Task schedular");

						MnrlTask_Vo task_vo = new MnrlTask_Vo();
						task_vo.setFromCount(j);

						task_vo.setOffLimit(mrnlTaskVo.getOffLimit());
						task_vo.setOffsetNumber(j);
						task_vo.setRateLimit(mrnlTaskVo.getRateLimit());
						task_vo.setTotalSize(mrnlTaskVo.getTotalSize());
						task_vo.setReactivatedDataListtotal(mrnlTaskVo.getReactivatedDataListtotal());
						task_vo.setReqdate(mrnlTaskVo.getReqdate());
						if (mrnlTaskVo.getPartialLimit() > 0) {
							task_vo.setPartialLimit(mrnlTaskVo.getPartialLimit());
						}
						Date date = new Date(System.currentTimeMillis()
								+ MnrlLoadData.propdetails.getAppProperty().getReactivatedReqIntervalTime());
						MnrlReactivatedTaskService service = context.getBean(MnrlReactivatedTaskService.class);
						service.setMrnlTaskVo(task_vo);
						datatask.schedule(service, date);

					}

				}
			}

		} catch (Exception e) {
			LOGGER.error("Exception ", e);
			e.printStackTrace();

		}

	}

	public MnrlTask_Vo getMrnlTaskVo() {
		return mrnlTaskVo;
	}

	public void setMrnlTaskVo(MnrlTask_Vo mrnlTaskVo) {
		this.mrnlTaskVo = mrnlTaskVo;
	}

}
