package com.fisglobal.fsg.dip.service.V2;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fisglobal.fsg.dip.core.config.MnrlLoadData;
import com.fisglobal.fsg.dip.core.convertor.CryptoApp;
import com.fisglobal.fsg.dip.core.utils.Constants;
import com.fisglobal.fsg.dip.entity.ReactivatedData_DAO;
import com.fisglobal.fsg.dip.entity.ReactivatedReqData_DAO;
import com.fisglobal.fsg.dip.req.res.entity.V2.MNRLFetchDataRequestV2;
import com.fisglobal.fsg.dip.req.res.entity.V2.MNRLHeadersV2;
import com.fisglobal.fsg.dip.req.res.entity.V2.MnrlDataBodyV2;
import com.fisglobal.fsg.dip.req.res.entity.V2.MnrlDataPayloadV2;
import com.fisglobal.fsg.dip.req.res.entity.V2.MnrlReactivatedDataRequestV2;
import com.fisglobal.fsg.dip.request.entity.MnrlTask_Vo;
import com.fisglobal.fsg.dip.response.entity.MrnlDecryptedData_VO;

@Component
@Scope("prototype")
public class MnrlReactivatedTaskServiceV2 implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(MnrlReactivatedTaskServiceV2.class);

	private MnrlTask_Vo mrnlTaskVo;

	@Inject
	private MnrlReactivatedDataServiceV2 mnrlReactiavtedDataServiceV2;

	@Inject
	private MnrlReactivatedServiceV2 mrnlReactivatedService;

	
	@Inject
	private CryptoApp utilService;


	// private long timeOut = 30000;


	@Inject
	private MnrlAuthServiceV2 mnrlAuthService;

	@Override
	public void run() {
		int j = 0;
		String token = null;
		if (mrnlTaskVo.isRetryDataDownload()) {
			int rateLimit = MnrlLoadData.propdetails.getAppProperty().getReactivatedReqCount();

			try {
				if (StringUtils.isBlank(mrnlTaskVo.getToken())) {
					token = mnrlAuthService.getMnrlAuthtoken(MnrlLoadData.tokenReqDetails.getEmail(),
							MnrlLoadData.tokenReqDetails.getSecureterm());
				} else {
					token = mrnlTaskVo.getToken();
				}
				try {
					if (StringUtils.isNotBlank(token)) {
						for (j = mrnlTaskVo.getFromCount() + 1; j <= mrnlTaskVo.getFromCount() + rateLimit; j++) {
							MnrlReactivatedDataRequestV2 mnrlDataRequest = new MnrlReactivatedDataRequestV2();
							MNRLFetchDataRequestV2 mnrlFetchData_Request = new MNRLFetchDataRequestV2();
							MnrlDataBodyV2 body = new MnrlDataBodyV2();
							MNRLHeadersV2 headers = new MNRLHeadersV2();
							MnrlDataPayloadV2 payload = new MnrlDataPayloadV2();
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
							mnrlDataRequest.setMNRLReactiveDataRequest(mnrlFetchData_Request);
							LOGGER.info("Reactivated Data request [{}]", MnrlLoadData.gson.toJson(mnrlDataRequest));
							ReactivatedReqData_DAO reactivatedReqData_DAO = mrnlReactivatedService
									.saveReactivatedRequestData(mnrlDataRequest);
							//MnrlDataResponseV2 mnrlresponse = new MnrlDataResponseV2();
							String resData = mnrlReactiavtedDataServiceV2.getMnrlReactivatedData(mnrlDataRequest);
							if ((StringUtils.isNotBlank(resData) && resData.contains("!_!"))) {
								String errorarray[] = resData.split("!_!");
								LOGGER.info("Response Failure with status code [{}] Response [{}]", errorarray[0],
										errorarray[1]);
								if (errorarray[0].equals("403")) {
									token = mnrlAuthService.getMnrlAuthtoken(MnrlLoadData.tokenReqDetails.getEmail(),
											MnrlLoadData.tokenReqDetails.getSecureterm());
									if (StringUtils.isBlank(token)) {
										LOGGER.info("retry block for empty token");
										mrnlReactivatedService.callMnrlTaskScheduler(j - 1, mrnlTaskVo.getOffLimit(), j - 1,
												mrnlTaskVo.getRateLimit(), mrnlTaskVo.getTotalSize(),
												mrnlTaskVo.getReactivatedDataListtotal(), mrnlTaskVo.getReqdate(),
												mrnlTaskVo.getPartialLimit(), true, token,
												MnrlLoadData.propdetails.getAppProperty().getReactivatedRetryIntervalTime(),
												false,mrnlTaskVo.getReactivatedCountDetails());
										/*
										 * MnrlTask_Vo task_vo = new MnrlTask_Vo(); task_vo.setFromCount(j - 1);
										 * 
										 * task_vo.setOffLimit(mrnlTaskVo.getOffLimit()); task_vo.setOffsetNumber(j -
										 * 1); task_vo.setRateLimit(mrnlTaskVo.getRateLimit());
										 * task_vo.setTotalSize(mrnlTaskVo.getTotalSize());
										 * task_vo.setMnrlDataListtotal(mrnlTaskVo.getReactivatedDataListtotal());
										 * task_vo.setReqdate(mrnlTaskVo.getReqdate()); if (mrnlTaskVo.getPartialLimit()
										 * > 0) { task_vo.setPartialLimit(mrnlTaskVo.getPartialLimit()); }
										 * task_vo.setRetryDataDownload(true); task_vo.setToken(token); Date date = new
										 * Date(System.currentTimeMillis() +
										 * MnrlLoadData.propdetails.getAppProperty().getReactivatedRetryIntervalTime());
										 * MnrlTaskService service = context.getBean(MnrlTaskService.class);
										 * service.setMrnlTaskVo(task_vo); datatask.schedule(service, date);
										 */
										break;
									} else {
										j = j - 1;
									}
								} else {
									LOGGER.info("retry block");
									mrnlReactivatedService.callMnrlTaskScheduler(j - 1, mrnlTaskVo.getOffLimit(), j - 1,
											mrnlTaskVo.getRateLimit(), mrnlTaskVo.getTotalSize(),
											mrnlTaskVo.getReactivatedDataListtotal(), mrnlTaskVo.getReqdate(),
											mrnlTaskVo.getPartialLimit(), true, token,
											MnrlLoadData.propdetails.getAppProperty().getReactivatedRetryIntervalTime(), false,mrnlTaskVo.getReactivatedCountDetails());
									/*
									 * MnrlTask_Vo task_vo = new MnrlTask_Vo(); task_vo.setFromCount(j - 1);
									 * 
									 * task_vo.setOffLimit(mrnlTaskVo.getOffLimit()); task_vo.setOffsetNumber(j -
									 * 1); task_vo.setRateLimit(mrnlTaskVo.getRateLimit());
									 * task_vo.setTotalSize(mrnlTaskVo.getTotalSize());
									 * task_vo.setMnrlDataListtotal(mrnlTaskVo.getReactivatedDataListtotal());
									 * task_vo.setReqdate(mrnlTaskVo.getReqdate()); if (mrnlTaskVo.getPartialLimit()
									 * > 0) { task_vo.setPartialLimit(mrnlTaskVo.getPartialLimit()); }
									 * task_vo.setRetryDataDownload(true); task_vo.setToken(token); Date date = new
									 * Date(System.currentTimeMillis() +
									 * MnrlLoadData.propdetails.getAppProperty().getReactivatedRetryIntervalTime());
									 * MnrlTaskService service = context.getBean(MnrlTaskService.class);
									 * service.setMrnlTaskVo(task_vo); datatask.schedule(service, date);
									 */
									break;
								}
							} else {
								

								String decryptData = utilService.decryptMnrlData(resData,"MNRLREACTIVATEDDATAV2");

								MrnlDecryptedData_VO decryptDataObj = MnrlLoadData.gson.fromJson(decryptData,
										MrnlDecryptedData_VO.class);

								LOGGER.info("Decrypt Data [{}]", decryptDataObj.getData().size());

								List<ReactivatedData_DAO> reactivatedDataList = mrnlReactivatedService.saveReactivatedData(
										decryptDataObj, String.valueOf(reactivatedReqData_DAO.getId()), payload.getDate());

								mrnlTaskVo.getReactivatedDataListtotal().addAll(reactivatedDataList);

								LOGGER.info("MNRL Reactivated Data Saved successfully");
								if (j == mrnlTaskVo.getTotalSize()) {
									LOGGER.info("total export size [{}}", mrnlTaskVo.getReactivatedDataListtotal().size());
									mrnlReactivatedService.updateReactivatedCountStatus(mrnlTaskVo.getReactivatedCountDetails(), Constants.COMPLETED);
									mrnlTaskVo.getReactivatedDataListtotal().clear();
									
									break;
								}
								LOGGER.info("Task J values [{}][{}]", j, mrnlTaskVo.getTotalSize());
								if (j == mrnlTaskVo.getFromCount() + rateLimit) {
									LOGGER.info("Task schedular");
									mrnlReactivatedService.callMnrlTaskScheduler(j, mrnlTaskVo.getOffLimit(), j,
											mrnlTaskVo.getRateLimit(), mrnlTaskVo.getTotalSize(),
											mrnlTaskVo.getReactivatedDataListtotal(), mrnlTaskVo.getReqdate(),
											mrnlTaskVo.getPartialLimit(), true, token,
											MnrlLoadData.propdetails.getAppProperty().getReactivatedReqIntervalTime(), false,mrnlTaskVo.getReactivatedCountDetails());
									/*
									 * MnrlTask_Vo task_vo = new MnrlTask_Vo(); task_vo.setFromCount(j);
									 * 
									 * task_vo.setOffLimit(mrnlTaskVo.getOffLimit()); task_vo.setOffsetNumber(j);
									 * task_vo.setRateLimit(mrnlTaskVo.getRateLimit());
									 * task_vo.setTotalSize(mrnlTaskVo.getTotalSize());
									 * task_vo.setMnrlDataListtotal(mrnlTaskVo.getReactivatedDataListtotal());
									 * task_vo.setReqdate(mrnlTaskVo.getReqdate()); if (mrnlTaskVo.getPartialLimit()
									 * > 0) { task_vo.setPartialLimit(mrnlTaskVo.getPartialLimit()); }
									 * task_vo.setRetryDataDownload(true); task_vo.setToken(token); Date date = new
									 * Date(System.currentTimeMillis() +
									 * MnrlLoadData.propdetails.getAppProperty().getReactivatedReqIntervalTime());
									 * MnrlTaskService service = context.getBean(MnrlTaskService.class);
									 * service.setMrnlTaskVo(task_vo); datatask.schedule(service, date);
									 */

								}

							}
						}

					} else {
						LOGGER.info("retry block for empty token");
						mrnlReactivatedService.callMnrlTaskScheduler(mrnlTaskVo.getFromCount(), mrnlTaskVo.getOffLimit(),
								mrnlTaskVo.getFromCount(), mrnlTaskVo.getRateLimit(), mrnlTaskVo.getTotalSize(),
								mrnlTaskVo.getReactivatedDataListtotal(), mrnlTaskVo.getReqdate(),
								mrnlTaskVo.getPartialLimit(), true, token,
								MnrlLoadData.propdetails.getAppProperty().getReactivatedRetryIntervalTime(), false,mrnlTaskVo.getReactivatedCountDetails());
						/*
						 * MnrlTask_Vo task_vo = new MnrlTask_Vo();
						 * task_vo.setFromCount(mrnlTaskVo.getFromCount());
						 * 
						 * task_vo.setOffLimit(mrnlTaskVo.getOffLimit());
						 * task_vo.setOffsetNumber(mrnlTaskVo.getFromCount());
						 * task_vo.setRateLimit(mrnlTaskVo.getRateLimit());
						 * task_vo.setTotalSize(mrnlTaskVo.getTotalSize());
						 * task_vo.setMnrlDataListtotal(mrnlTaskVo.getReactivatedDataListtotal());
						 * task_vo.setReqdate(mrnlTaskVo.getReqdate()); if (mrnlTaskVo.getPartialLimit()
						 * > 0) { task_vo.setPartialLimit(mrnlTaskVo.getPartialLimit()); }
						 * task_vo.setRetryDataDownload(true); task_vo.setToken(token); Date date = new
						 * Date(System.currentTimeMillis() +
						 * MnrlLoadData.propdetails.getAppProperty().getReactivatedRetryIntervalTime());
						 * MnrlTaskService service = context.getBean(MnrlTaskService.class);
						 * service.setMrnlTaskVo(task_vo); datatask.schedule(service, date);
						 */
					}
				} catch (Exception e) {
					LOGGER.error("Exception in downloading ", e);
					mrnlReactivatedService.callMnrlTaskScheduler(j-1, mrnlTaskVo.getOffLimit(), j-1,
							mrnlTaskVo.getRateLimit(), mrnlTaskVo.getTotalSize(),
							mrnlTaskVo.getReactivatedDataListtotal(), mrnlTaskVo.getReqdate(),
							mrnlTaskVo.getPartialLimit(), true, token,
							MnrlLoadData.propdetails.getAppProperty().getReactivatedRetryIntervalTime(), false,mrnlTaskVo.getReactivatedCountDetails());
				}
			} catch (Exception e) {
				LOGGER.error("Exception in taskschedular", e);
				mrnlReactivatedService.callMnrlTaskScheduler(mrnlTaskVo.getFromCount(), mrnlTaskVo.getOffLimit(),
						mrnlTaskVo.getFromCount(), mrnlTaskVo.getRateLimit(), mrnlTaskVo.getTotalSize(),
						mrnlTaskVo.getReactivatedDataListtotal(), mrnlTaskVo.getReqdate(), mrnlTaskVo.getPartialLimit(), true,
						token, MnrlLoadData.propdetails.getAppProperty().getReactivatedRetryIntervalTime(), false,mrnlTaskVo.getReactivatedCountDetails());

				/*MnrlTask_Vo task_vo = new MnrlTask_Vo();
				task_vo.setFromCount(j - 1);

				task_vo.setOffLimit(mrnlTaskVo.getOffLimit());
				task_vo.setOffsetNumber(j - 1);
				task_vo.setRateLimit(mrnlTaskVo.getRateLimit());
				task_vo.setTotalSize(mrnlTaskVo.getTotalSize());
				task_vo.setMnrlDataListtotal(mrnlTaskVo.getReactivatedDataListtotal());
				task_vo.setReqdate(mrnlTaskVo.getReqdate());
				if (mrnlTaskVo.getPartialLimit() > 0) {
					task_vo.setPartialLimit(mrnlTaskVo.getPartialLimit());
				}
				task_vo.setRetryDataDownload(true);
				task_vo.setToken(token);
				Date date = new Date(
						System.currentTimeMillis() + MnrlLoadData.propdetails.getAppProperty().getReactivatedRetryIntervalTime());
				MnrlTaskService service = context.getBean(MnrlTaskService.class);
				service.setMrnlTaskVo(task_vo);
				datatask.schedule(service, date);*/
			}

		}

		else

		{
			if (StringUtils.isNotBlank(mrnlTaskVo.getToken())) {
				mrnlReactivatedService.trigger(mrnlTaskVo.getToken(),mrnlTaskVo.getReqdate());
			} else {
				mrnlReactivatedService.trigger(null,mrnlTaskVo.getReqdate());
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
