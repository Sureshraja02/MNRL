package com.fisglobal.fsg.dip.service;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import com.fisglobal.fsg.dip.core.config.MnrlLoadData;
import com.fisglobal.fsg.dip.core.convertor.MNRLDecryptionUtils;
import com.fisglobal.fsg.dip.entity.MnrlData_DAO;
import com.fisglobal.fsg.dip.entity.MnrlReqData_DAO;
import com.fisglobal.fsg.dip.request.entity.MNRLDataBody;
import com.fisglobal.fsg.dip.request.entity.MNRLDataPayload;
import com.fisglobal.fsg.dip.request.entity.MNRLFetchDataRequest;
import com.fisglobal.fsg.dip.request.entity.MNRLHeaders;
import com.fisglobal.fsg.dip.request.entity.MnrlDataRequest;
import com.fisglobal.fsg.dip.request.entity.MnrlTask_Vo;
import com.fisglobal.fsg.dip.response.entity.MnrlDataResponse;
import com.fisglobal.fsg.dip.response.entity.MrnlDecryptedData_VO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Component
@Scope("prototype")
public class MnrlTaskService implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(MnrlTaskService.class);

	private MnrlTask_Vo mrnlTaskVo;

	@Inject
	private MnrlDataService mnrlDataService;

	@Inject
	private MnrlService mrnlService;

	@Inject
	private MNRLDecryptionUtils mrnlDecryptionUtils;


	
	@Value("${mnrl.data.export:N}")
	private String mnrlExport;

	// private long timeOut = 30000;


	@Inject
	private MnrlAuthService mnrlAuthService;

	@Override
	public void run() {
		int j = 0;
		String token = null;
		if (mrnlTaskVo.isRetryDataDownload()) {
			int rateLimit = MnrlLoadData.propdetails.getAppProperty().getReqCount();

			try {
				if (StringUtils.isBlank(mrnlTaskVo.getToken())) {
					token = mnrlAuthService.getMnrlAuthtoken(mrnlService.getMnrlAuth());
				} else {
					token = mrnlTaskVo.getToken();
				}
				try {
					if (StringUtils.isNotBlank(token)) {
						for (j = mrnlTaskVo.getFromCount() + 1; j <= mrnlTaskVo.getFromCount() + rateLimit; j++) {
							MnrlDataRequest mnrlDataRequest = new MnrlDataRequest();
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
							LOGGER.debug("Offset [{}]", payload.getOffset());
							headers.setAuthorization(token);
							body.setPayload(payload);
							mnrlFetchData_Request.setBody(body);
							mnrlFetchData_Request.setHeaders(headers);
							mnrlDataRequest.setMNRLFetchData_Request(mnrlFetchData_Request);
							LOGGER.info("Data request [{}]", MnrlLoadData.gson.toJson(mnrlDataRequest));
							MnrlReqData_DAO mnrlReqData_DAO = mrnlService.saveMnrlRequestData(mnrlDataRequest);
							MnrlDataResponse mnrlresponse = new MnrlDataResponse();
							String resData = mnrlDataService.getMnrlData(mnrlDataRequest);
							if ((StringUtils.isNotBlank(resData) && resData.contains("!_!"))) {
								String errorarray[] = resData.split("!_!");
								LOGGER.info("Response Failure with status code [{}] Response [{}]", errorarray[0],
										errorarray[1]);
								if (errorarray[0].equals("403")) {
									token = mnrlAuthService.getMnrlAuthtoken(mrnlService.getMnrlAuth());
									if (StringUtils.isBlank(token)) {
										LOGGER.info("retry block for empty token");
										mrnlService.callMnrlTaskScheduler(j - 1, mrnlTaskVo.getOffLimit(), j - 1,
												mrnlTaskVo.getRateLimit(), mrnlTaskVo.getTotalSize(),
												mrnlTaskVo.getMnrlDataListtotal(), mrnlTaskVo.getReqdate(),
												mrnlTaskVo.getPartialLimit(), true, token,
												MnrlLoadData.propdetails.getAppProperty().getRetryIntervalTime(),
												false);
										/*
										 * MnrlTask_Vo task_vo = new MnrlTask_Vo(); task_vo.setFromCount(j - 1);
										 * 
										 * task_vo.setOffLimit(mrnlTaskVo.getOffLimit()); task_vo.setOffsetNumber(j -
										 * 1); task_vo.setRateLimit(mrnlTaskVo.getRateLimit());
										 * task_vo.setTotalSize(mrnlTaskVo.getTotalSize());
										 * task_vo.setMnrlDataListtotal(mrnlTaskVo.getMnrlDataListtotal());
										 * task_vo.setReqdate(mrnlTaskVo.getReqdate()); if (mrnlTaskVo.getPartialLimit()
										 * > 0) { task_vo.setPartialLimit(mrnlTaskVo.getPartialLimit()); }
										 * task_vo.setRetryDataDownload(true); task_vo.setToken(token); Date date = new
										 * Date(System.currentTimeMillis() +
										 * MnrlLoadData.propdetails.getAppProperty().getRetryIntervalTime());
										 * MnrlTaskService service = context.getBean(MnrlTaskService.class);
										 * service.setMrnlTaskVo(task_vo); datatask.schedule(service, date);
										 */
										break;
									} else {
										j = j - 1;
									}
								} else {
									LOGGER.info("retry block");
									mrnlService.callMnrlTaskScheduler(j - 1, mrnlTaskVo.getOffLimit(), j - 1,
											mrnlTaskVo.getRateLimit(), mrnlTaskVo.getTotalSize(),
											mrnlTaskVo.getMnrlDataListtotal(), mrnlTaskVo.getReqdate(),
											mrnlTaskVo.getPartialLimit(), true, token,
											MnrlLoadData.propdetails.getAppProperty().getRetryIntervalTime(), false);
									/*
									 * MnrlTask_Vo task_vo = new MnrlTask_Vo(); task_vo.setFromCount(j - 1);
									 * 
									 * task_vo.setOffLimit(mrnlTaskVo.getOffLimit()); task_vo.setOffsetNumber(j -
									 * 1); task_vo.setRateLimit(mrnlTaskVo.getRateLimit());
									 * task_vo.setTotalSize(mrnlTaskVo.getTotalSize());
									 * task_vo.setMnrlDataListtotal(mrnlTaskVo.getMnrlDataListtotal());
									 * task_vo.setReqdate(mrnlTaskVo.getReqdate()); if (mrnlTaskVo.getPartialLimit()
									 * > 0) { task_vo.setPartialLimit(mrnlTaskVo.getPartialLimit()); }
									 * task_vo.setRetryDataDownload(true); task_vo.setToken(token); Date date = new
									 * Date(System.currentTimeMillis() +
									 * MnrlLoadData.propdetails.getAppProperty().getRetryIntervalTime());
									 * MnrlTaskService service = context.getBean(MnrlTaskService.class);
									 * service.setMrnlTaskVo(task_vo); datatask.schedule(service, date);
									 */
									break;
								}
							} else {
								mnrlresponse = MnrlLoadData.gson.fromJson(resData, MnrlDataResponse.class);

								LOGGER.debug("Encrypted Key [{}] Encrypted Data[{}], ", mnrlresponse.getEncryptedKey(),
										mnrlresponse.getEncryptedData());
								// MNRLDecryptionUtils obj = new MNRLDecryptionUtils();

								String AESKey = mrnlDecryptionUtils.getDecryptKey(mnrlresponse.getEncryptedKey());
								String decryptData = mrnlDecryptionUtils.decrypt(AESKey,
										mnrlresponse.getEncryptedData());

								LOGGER.debug("Decrypted Key [{}] Decrypted Data[{}], ", AESKey, decryptData);

								MrnlDecryptedData_VO decryptDataObj = MnrlLoadData.gson.fromJson(decryptData,
										MrnlDecryptedData_VO.class);

								LOGGER.info("Decrypt Data [{}]", decryptDataObj.getData().size());

								List<MnrlData_DAO> mnrlDataList = mrnlService.saveMnrlData(decryptDataObj,
										String.valueOf(mnrlReqData_DAO.getId()), payload.getDate());

								mrnlTaskVo.getMnrlDataListtotal().addAll(mnrlDataList);

								LOGGER.info("MNRL Data Saved successfully");
								if (j == mrnlTaskVo.getTotalSize()) {
									LOGGER.info("total export size [{}}", mrnlTaskVo.getMnrlDataListtotal().size());
									if (mnrlExport.equals("Y")) {
									mrnlService.exportFile(mrnlTaskVo.getMnrlDataListtotal());
									}
									mrnlTaskVo.getMnrlDataListtotal().clear();
									
									break;
								}
								LOGGER.info("Task J values [{}][{}]", j, mrnlTaskVo.getTotalSize());
								if (j == mrnlTaskVo.getFromCount() + rateLimit) {
									LOGGER.info("Task schedular");
									mrnlService.callMnrlTaskScheduler(j, mrnlTaskVo.getOffLimit(), j,
											mrnlTaskVo.getRateLimit(), mrnlTaskVo.getTotalSize(),
											mrnlTaskVo.getMnrlDataListtotal(), mrnlTaskVo.getReqdate(),
											mrnlTaskVo.getPartialLimit(), true, token,
											MnrlLoadData.propdetails.getAppProperty().getReqIntervalTime(), false);
									/*
									 * MnrlTask_Vo task_vo = new MnrlTask_Vo(); task_vo.setFromCount(j);
									 * 
									 * task_vo.setOffLimit(mrnlTaskVo.getOffLimit()); task_vo.setOffsetNumber(j);
									 * task_vo.setRateLimit(mrnlTaskVo.getRateLimit());
									 * task_vo.setTotalSize(mrnlTaskVo.getTotalSize());
									 * task_vo.setMnrlDataListtotal(mrnlTaskVo.getMnrlDataListtotal());
									 * task_vo.setReqdate(mrnlTaskVo.getReqdate()); if (mrnlTaskVo.getPartialLimit()
									 * > 0) { task_vo.setPartialLimit(mrnlTaskVo.getPartialLimit()); }
									 * task_vo.setRetryDataDownload(true); task_vo.setToken(token); Date date = new
									 * Date(System.currentTimeMillis() +
									 * MnrlLoadData.propdetails.getAppProperty().getReqIntervalTime());
									 * MnrlTaskService service = context.getBean(MnrlTaskService.class);
									 * service.setMrnlTaskVo(task_vo); datatask.schedule(service, date);
									 */

								}

							}
						}

					} else {
						LOGGER.info("retry block for empty token");
						mrnlService.callMnrlTaskScheduler(mrnlTaskVo.getFromCount(), mrnlTaskVo.getOffLimit(),
								mrnlTaskVo.getFromCount(), mrnlTaskVo.getRateLimit(), mrnlTaskVo.getTotalSize(),
								mrnlTaskVo.getMnrlDataListtotal(), mrnlTaskVo.getReqdate(),
								mrnlTaskVo.getPartialLimit(), true, token,
								MnrlLoadData.propdetails.getAppProperty().getRetryIntervalTime(), false);
						/*
						 * MnrlTask_Vo task_vo = new MnrlTask_Vo();
						 * task_vo.setFromCount(mrnlTaskVo.getFromCount());
						 * 
						 * task_vo.setOffLimit(mrnlTaskVo.getOffLimit());
						 * task_vo.setOffsetNumber(mrnlTaskVo.getFromCount());
						 * task_vo.setRateLimit(mrnlTaskVo.getRateLimit());
						 * task_vo.setTotalSize(mrnlTaskVo.getTotalSize());
						 * task_vo.setMnrlDataListtotal(mrnlTaskVo.getMnrlDataListtotal());
						 * task_vo.setReqdate(mrnlTaskVo.getReqdate()); if (mrnlTaskVo.getPartialLimit()
						 * > 0) { task_vo.setPartialLimit(mrnlTaskVo.getPartialLimit()); }
						 * task_vo.setRetryDataDownload(true); task_vo.setToken(token); Date date = new
						 * Date(System.currentTimeMillis() +
						 * MnrlLoadData.propdetails.getAppProperty().getRetryIntervalTime());
						 * MnrlTaskService service = context.getBean(MnrlTaskService.class);
						 * service.setMrnlTaskVo(task_vo); datatask.schedule(service, date);
						 */
					}
				} catch (Exception e) {
					mrnlService.callMnrlTaskScheduler(j-1, mrnlTaskVo.getOffLimit(), j-1,
							mrnlTaskVo.getRateLimit(), mrnlTaskVo.getTotalSize(),
							mrnlTaskVo.getMnrlDataListtotal(), mrnlTaskVo.getReqdate(),
							mrnlTaskVo.getPartialLimit(), true, token,
							MnrlLoadData.propdetails.getAppProperty().getRetryIntervalTime(), false);
				}
			} catch (Exception e) {
				mrnlService.callMnrlTaskScheduler(mrnlTaskVo.getFromCount(), mrnlTaskVo.getOffLimit(),
						mrnlTaskVo.getFromCount(), mrnlTaskVo.getRateLimit(), mrnlTaskVo.getTotalSize(),
						mrnlTaskVo.getMnrlDataListtotal(), mrnlTaskVo.getReqdate(), mrnlTaskVo.getPartialLimit(), true,
						token, MnrlLoadData.propdetails.getAppProperty().getRetryIntervalTime(), false);

				/*MnrlTask_Vo task_vo = new MnrlTask_Vo();
				task_vo.setFromCount(j - 1);

				task_vo.setOffLimit(mrnlTaskVo.getOffLimit());
				task_vo.setOffsetNumber(j - 1);
				task_vo.setRateLimit(mrnlTaskVo.getRateLimit());
				task_vo.setTotalSize(mrnlTaskVo.getTotalSize());
				task_vo.setMnrlDataListtotal(mrnlTaskVo.getMnrlDataListtotal());
				task_vo.setReqdate(mrnlTaskVo.getReqdate());
				if (mrnlTaskVo.getPartialLimit() > 0) {
					task_vo.setPartialLimit(mrnlTaskVo.getPartialLimit());
				}
				task_vo.setRetryDataDownload(true);
				task_vo.setToken(token);
				Date date = new Date(
						System.currentTimeMillis() + MnrlLoadData.propdetails.getAppProperty().getRetryIntervalTime());
				MnrlTaskService service = context.getBean(MnrlTaskService.class);
				service.setMrnlTaskVo(task_vo);
				datatask.schedule(service, date);*/
			}

		}

		else

		{
			if (StringUtils.isNotBlank(mrnlTaskVo.getToken())) {
				mrnlService.trigger(mrnlTaskVo.getToken());
			} else {
				mrnlService.trigger(null);
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
