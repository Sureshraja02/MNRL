package com.fisglobal.fsg.dip.fri.service;

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
import com.fisglobal.fsg.dip.core.convertor.CryptoApp;
import com.fisglobal.fsg.dip.core.utils.Constants;
import com.fisglobal.fsg.dip.entity.FriData_DAO;
import com.fisglobal.fsg.dip.entity.FriReqData_DAO;
import com.fisglobal.fsg.dip.fri.entity.FRIBody;
import com.fisglobal.fsg.dip.fri.entity.FRIDataRequest;
import com.fisglobal.fsg.dip.fri.entity.FRIDataResponse;
import com.fisglobal.fsg.dip.fri.entity.FRIDecryptData;
import com.fisglobal.fsg.dip.fri.entity.FRIFetchDataRequest;
import com.fisglobal.fsg.dip.fri.entity.FRIHeaders;
import com.fisglobal.fsg.dip.fri.entity.FRIPayload;
import com.fisglobal.fsg.dip.request.entity.MnrlTask_Vo;

@Component
@Scope("prototype")
public class FriTaskService implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(FriTaskService.class);

	private MnrlTask_Vo mrnlTaskVo;

	@Inject
	private FriDataService friDataService;

	@Inject
	private FRIService friService;

	@Inject
	private CryptoApp utilService;

	@Inject
	private TaskScheduler datatask;

	// private long timeOut = 30000;

	@Inject
	private ApplicationContext context;

	@Inject
	private FriAuthService friAuthService;

	@Override
	public void run() {
		int j = 0;
		String token = null;
		if (mrnlTaskVo.isRetryDataDownload()) {
			int rateLimit = MnrlLoadData.propdetails.getAppProperty().getFriReqCount();
			try {
				if (StringUtils.isBlank(mrnlTaskVo.getToken())) {
					token = friAuthService.getFriAuthtoken(MnrlLoadData.friTokenRequestDetails.getEmail(),
							MnrlLoadData.friTokenRequestDetails.getSecureterm());
				} else {
					token = mrnlTaskVo.getToken();
				}

				try {
					if (StringUtils.isNotBlank(token)) {
						for (j = mrnlTaskVo.getFromCount() + 1; j <= mrnlTaskVo.getFromCount() + rateLimit; j++) {
							FRIDataRequest friDataRequest = new FRIDataRequest();
							FRIFetchDataRequest friFetchData_Request = new FRIFetchDataRequest();
							FRIBody body = new FRIBody();
							FRIHeaders headers = new FRIHeaders();
							FRIPayload payload = new FRIPayload();
							payload.setClientId(MnrlLoadData.friTokenRequestDetails.getBankId());
							payload.setOffset(mrnlTaskVo.getOffLimit() * (j - 1));
							payload.setDate(mrnlTaskVo.getReqdate());
							if (mrnlTaskVo.getPartialLimit() > 0 && j == mrnlTaskVo.getTotalSize()) {
								payload.setCount(mrnlTaskVo.getPartialLimit());

							} else {
								payload.setCount(mrnlTaskVo.getOffLimit());
							}
							headers.setAuthorization(token);
							body.setPayload(payload);
							friFetchData_Request.setBody(body);
							friFetchData_Request.setHeaders(headers);
							friDataRequest.setFRIFetchDataRequest(friFetchData_Request);
							LOGGER.info("FRI Data request [{}]", MnrlLoadData.gson.toJson(friDataRequest));
							FriReqData_DAO friReqData_DAO = friService.saveFriRequestData(friDataRequest);
							FRIDataResponse friresponse = new FRIDataResponse();
							String resData = friDataService.getFRIData(friDataRequest);
							if ((StringUtils.isNotBlank(resData) && resData.contains("!_!"))) {
								String errorarray[] = resData.split("!_!");
								LOGGER.info("Response Failure with status code [{}] Response [{}]", errorarray[0],
										errorarray[1]);
								if (errorarray[0].equals("403")) {
									token = friAuthService.getFriAuthtoken(
											MnrlLoadData.friTokenRequestDetails.getEmail(),
											MnrlLoadData.friTokenRequestDetails.getSecureterm());
									if (StringUtils.isBlank(token)) {
										LOGGER.info("retry block for empty token");
										friService.callFriTaskScheduler(j - 1, mrnlTaskVo.getOffLimit(), j - 1,
												mrnlTaskVo.getRateLimit(), mrnlTaskVo.getTotalSize(),
												mrnlTaskVo.getFriDataListtotal(), mrnlTaskVo.getReqdate(),
												mrnlTaskVo.getPartialLimit(), true, token,
												MnrlLoadData.propdetails.getAppProperty().getFriRetryIntervalTime(),
												false,mrnlTaskVo.getFriCountDetails());
										break;
									} else {
										j = j - 1;
									}
								} else {
									LOGGER.info("retry block");
									friService.callFriTaskScheduler(j - 1, mrnlTaskVo.getOffLimit(), j - 1,
											mrnlTaskVo.getRateLimit(), mrnlTaskVo.getTotalSize(),
											mrnlTaskVo.getFriDataListtotal(), mrnlTaskVo.getReqdate(),
											mrnlTaskVo.getPartialLimit(), true, token,
											MnrlLoadData.propdetails.getAppProperty().getFriRetryIntervalTime(), false,mrnlTaskVo.getFriCountDetails());

									break;
								}
							} else {
								friresponse = MnrlLoadData.gson.fromJson(resData, FRIDataResponse.class);

								// LOGGER.debug("Encrypted Key [{}] Encrypted Data[{}], ",
								// mnrlresponse.getEncryptedKey(),
								// mnrlresponse.getEncryptedData());
								// MNRLDecryptionUtils obj = new MNRLDecryptionUtils();

								String decryptData = utilService.decryptData(resData);

								// LOGGER.debug("Decrypted Key [{}] Decrypted Data[{}], ", AESKey, decryptData);

								FRIDecryptData decryptDataObj = MnrlLoadData.gson.fromJson(decryptData,
										FRIDecryptData.class);

								LOGGER.info("Decrypt Data [{}]", decryptDataObj.getData().size());

								List<FriData_DAO> friDataList = friService.saveFriData(decryptDataObj,
										String.valueOf(friReqData_DAO.getId()), payload.getDate());

								mrnlTaskVo.getFriDataListtotal().addAll(friDataList);

								LOGGER.info("FRI Data Saved successfully");
								if (j == mrnlTaskVo.getTotalSize()) {
									LOGGER.info("total export size [{}}", mrnlTaskVo.getFriDataListtotal().size());
									// mrnlService.exportFile(mrnlTaskVo.getMnrlDataListtotal());
									friService.updateFriCountStatus(mrnlTaskVo.getFriCountDetails(), Constants.COMPLETED);
									mrnlTaskVo.getFriDataListtotal().clear();
									break;
								}
								LOGGER.info("Task J values [{}][{}]", j, mrnlTaskVo.getTotalSize());
								if (j == mrnlTaskVo.getFromCount() + rateLimit) {
									LOGGER.info("Task schedular");
									friService.callFriTaskScheduler(j, mrnlTaskVo.getOffLimit(), j,
											mrnlTaskVo.getRateLimit(), mrnlTaskVo.getTotalSize(),
											mrnlTaskVo.getFriDataListtotal(), mrnlTaskVo.getReqdate(),
											mrnlTaskVo.getPartialLimit(), true, token,
											MnrlLoadData.propdetails.getAppProperty().getFriReqIntervalTime(), false,mrnlTaskVo.getFriCountDetails());
									/*
									 * MnrlTask_Vo task_vo = new MnrlTask_Vo(); task_vo.setFromCount(j);
									 * 
									 * task_vo.setOffLimit(mrnlTaskVo.getOffLimit()); task_vo.setOffsetNumber(j);
									 * task_vo.setRateLimit(mrnlTaskVo.getRateLimit());
									 * task_vo.setTotalSize(mrnlTaskVo.getTotalSize());
									 * task_vo.setFriDataListtotal(mrnlTaskVo.getFriDataListtotal());
									 * task_vo.setReqdate(mrnlTaskVo.getReqdate()); if (mrnlTaskVo.getPartialLimit()
									 * > 0) { task_vo.setPartialLimit(mrnlTaskVo.getPartialLimit()); } Date date =
									 * new Date(System.currentTimeMillis() +
									 * MnrlLoadData.propdetails.getAppProperty().getFriReqIntervalTime());
									 * FriTaskService service = context.getBean(FriTaskService.class);
									 * service.setMrnlTaskVo(task_vo); datatask.schedule(service, date);
									 */

								}

							}
						}
					} else {
						LOGGER.info("retry block for empty token");
						friService.callFriTaskScheduler(mrnlTaskVo.getFromCount(), mrnlTaskVo.getOffLimit(),
								mrnlTaskVo.getFromCount(), mrnlTaskVo.getRateLimit(), mrnlTaskVo.getTotalSize(),
								mrnlTaskVo.getFriDataListtotal(), mrnlTaskVo.getReqdate(), mrnlTaskVo.getPartialLimit(),
								true, token, MnrlLoadData.propdetails.getAppProperty().getFriRetryIntervalTime(), false,mrnlTaskVo.getFriCountDetails());
					}

				} catch (Exception e) {
					LOGGER.error("Exception in downloading ", e);
					friService.callFriTaskScheduler(j - 1, mrnlTaskVo.getOffLimit(), j - 1, mrnlTaskVo.getRateLimit(),
							mrnlTaskVo.getTotalSize(), mrnlTaskVo.getFriDataListtotal(), mrnlTaskVo.getReqdate(),
							mrnlTaskVo.getPartialLimit(), true, token,
							MnrlLoadData.propdetails.getAppProperty().getFriRetryIntervalTime(), false,mrnlTaskVo.getFriCountDetails());
				}
			} catch (Exception e) {
				LOGGER.error("Exception in taskschedular", e);
				friService.callFriTaskScheduler(mrnlTaskVo.getFromCount(), mrnlTaskVo.getOffLimit(),
						mrnlTaskVo.getFromCount(), mrnlTaskVo.getRateLimit(), mrnlTaskVo.getTotalSize(),
						mrnlTaskVo.getFriDataListtotal(), mrnlTaskVo.getReqdate(), mrnlTaskVo.getPartialLimit(), true,
						token, MnrlLoadData.propdetails.getAppProperty().getFriRetryIntervalTime(), false,mrnlTaskVo.getFriCountDetails());
			}

		}

		else

		{
			if (StringUtils.isNotBlank(mrnlTaskVo.getToken())) {
				friService.trigger(mrnlTaskVo.getToken(),mrnlTaskVo.getReqdate());
			} else {
				friService.trigger(null,mrnlTaskVo.getReqdate());
			}
		}

	}

	public MnrlTask_Vo getMrnlTaskVo() {
		return mrnlTaskVo;
	}

	public void setMrnlTaskVo(MnrlTask_Vo mrnlTaskVo) {
		this.mrnlTaskVo = mrnlTaskVo;
	}

}
