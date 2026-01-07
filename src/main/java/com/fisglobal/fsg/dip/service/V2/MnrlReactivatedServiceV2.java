package com.fisglobal.fsg.dip.service.V2;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fisglobal.fsg.dip.core.config.MnrlLoadData;
import com.fisglobal.fsg.dip.core.convertor.CryptoApp;
import com.fisglobal.fsg.dip.core.service.CommonMethodUtils;
import com.fisglobal.fsg.dip.core.utils.Constants;
import com.fisglobal.fsg.dip.entity.FriCountDetails_DAO;
import com.fisglobal.fsg.dip.entity.MnrlCountDetails_DAO;
import com.fisglobal.fsg.dip.entity.ReactivatedCountDetails_DAO;
import com.fisglobal.fsg.dip.entity.ReactivatedData_DAO;
import com.fisglobal.fsg.dip.entity.ReactivatedReqData_DAO;
import com.fisglobal.fsg.dip.entity.impl.ReactivatedCountDetailsImpl;
import com.fisglobal.fsg.dip.entity.repo.ReactivatedCountDetails_Repo;
import com.fisglobal.fsg.dip.entity.repo.ReactivatedDataRepo;
import com.fisglobal.fsg.dip.entity.repo.ReactivatedReqDataRepo;
import com.fisglobal.fsg.dip.req.res.entity.V2.MNRLFetchDataRequestV2;
import com.fisglobal.fsg.dip.req.res.entity.V2.MNRLHeadersV2;
import com.fisglobal.fsg.dip.req.res.entity.V2.MnrlDataBodyV2;
import com.fisglobal.fsg.dip.req.res.entity.V2.MnrlDataPayloadV2;
import com.fisglobal.fsg.dip.req.res.entity.V2.MnrlReactivatedCountRequestV2;
import com.fisglobal.fsg.dip.req.res.entity.V2.MnrlReactivatedDataRequestV2;
import com.fisglobal.fsg.dip.req.res.entity.V2.MnrlReactivatedDataResponseV2;
import com.fisglobal.fsg.dip.request.entity.MnrlTask_Vo;
import com.fisglobal.fsg.dip.response.entity.MrnlDecryptedData_VO;

@Service
public class MnrlReactivatedServiceV2 {

	private static final Logger LOGGER = LoggerFactory.getLogger(MnrlReactivatedServiceV2.class);

	List<ReactivatedData_DAO> reactivatedDataListtotal = new ArrayList<>();

	@Inject
	private MnrlReactivatedDataServiceV2 mnrlReactivatedDataService;

	@Inject
	private CryptoApp utilService;

	@Inject
	private ReactivatedDataRepo reactivatedDataRepo;

	@Inject
	private ReactivatedReqDataRepo reactivatedReqDataRepo;

	@Inject
	private MnrlAuthServiceV2 mnrlAuthService;

	@Inject
	private MnrlReactivatedCountServiceV2 mnrlReactivatedCountServiceV2;

	@Inject
	private CommonMethodUtils commonMethodUtils;

	@Inject
	private TaskScheduler datatask;

	// private long timeOut = 30000;

	@Inject
	private ApplicationContext context;

	@Inject
	private ReactivatedCountDetails_Repo countRepo;

	@Inject
	private ReactivatedCountDetailsImpl reactivatedCountDetailsImpl;

	// @Scheduled(initialDelay = 20000, fixedRateString = "300000")
	@Scheduled(cron = "${reactivated.v2.cron:0 56 11 1/1 * ?}")
	public void mnrlDataDownload() {
		// LOGGER.info("trigger");
		// trigger(null);

		LOGGER.info("trigger");
		if ("Y".equals(MnrlLoadData.propdetails.getAppProperty().getMnrlReactivatedCountDateFlag())) {
			// trigger(null,
			// MnrlLoadData.propdetails.getAppProperty().getMnrlReactivatedCountDate());

			String manualDate = MnrlLoadData.propdetails.getAppProperty().getMnrlReactivatedCountDate();

			List<ReactivatedCountDetails_DAO> inProgressList = reactivatedCountDetailsImpl
					.getReactivatedCountData(manualDate, Constants.INPROGRESS, LocalDate.now());

			int inProgressCnt = inProgressList.size();
			if (inProgressCnt > 0) {
				LOGGER.info("For given date [{}], download is in progress ", manualDate);
				return;
			}
			List<ReactivatedCountDetails_DAO> completedList = reactivatedCountDetailsImpl
					.getReactivatedCountData(manualDate, Constants.COMPLETED, LocalDate.now());

			int completedCnt = completedList.size();

			if (completedCnt == 0) {
				trigger(null, manualDate);
			} else {
				LOGGER.info("Given date [{}] has been downloaded already ", manualDate);
			}
		} else {
			ReactivatedCountDetails_DAO maxDateObj = countRepo.getMaxFetchDate();
			if (maxDateObj != null) {
				// String maxDateStr = "2025-10-20"; // Replace with actual value

				String maxDateStr = maxDateObj.getFetchDate();
				String status = maxDateObj.getStatus();
				LocalDate entryDate = maxDateObj.getEntryDate().toLocalDate();

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

				// Parse max date
				LocalDate maxDate = LocalDate.parse(maxDateStr, formatter);
				LocalDate currentDate = LocalDate.now().minusDays(1);
				LocalDate startDate = null;
				LOGGER.info("entryDate [{}] current date [{}]", entryDate, LocalDate.now());

				if (StringUtils.isNotBlank(status) && status.equals(Constants.INPROGRESS)
						&& (LocalDate.now().isEqual(entryDate))) {
					LOGGER.info("Currently download is in progress for the date[{}]", maxDate);
					return;
				}
				if (StringUtils.isNotBlank(status) && status.equals(Constants.INPROGRESS)
						&& !(LocalDate.now().isEqual(entryDate))) {

					startDate = maxDate;
					LOGGER.info("maxDate [{}]is in [{}]", startDate, status);
				} else {
					startDate = maxDate.plusDays(1);
				}
				// Print dates from maxDate to currentDate in YYYY-MM-DD format
				LOGGER.info("startDate [{}] current date [{}]", startDate, currentDate);
				if ((!startDate.isAfter(currentDate) && (reactivatedDataListtotal.size() == 0))) {
					LOGGER.info("list size [{}]", reactivatedDataListtotal.size());
					// System.out.println(maxDate.format(formatter));
					String fetchDate = startDate.format(formatter);

					trigger(null, fetchDate);
					// startDate = startDate.plusDays(1);
				} else {
					LOGGER.info("Download has been completed till date");
				}
			} else {
				trigger(null, commonMethodUtils.getCurrentDateAsString());
			}

		}
	}

	public void trigger(String token, String fetchDate) {

		int rateLimit = MnrlLoadData.propdetails.getAppProperty().getReactivatedReqCount();
		int j = 0;
		int count = -1;
		try {

			// boolean exportfile=false;
			boolean partialCnt = false;

			MnrlReactivatedCountRequestV2 mnrlReactivatedCountRequest = new MnrlReactivatedCountRequestV2();
			if (StringUtils.isBlank(token)) {
				token = mnrlAuthService.getMnrlAuthtoken(MnrlLoadData.tokenReqDetails.getEmail(),
						MnrlLoadData.tokenReqDetails.getSecureterm());
			}

			if (StringUtils.isNotBlank(token)) {
				mnrlReactivatedCountRequest = mnrlReactivatedCountServiceV2.setMnrlReactivatedCountRequest(token,
						fetchDate);

				count = mnrlReactivatedCountServiceV2.getMnrlReactivatedCount(mnrlReactivatedCountRequest)
						.getMNRLReactiveNumResponse().getBody().getPayload().getCount();
				// int count=1198;
				LOGGER.info("Mrnl Reactiavted count [{}]", count);
				ReactivatedCountDetails_DAO countDao = saveReactivatedCount(fetchDate, count, Constants.INPROGRESS);
				int countPerOffset = 1000;
				if (MnrlLoadData.propdetails.getAppProperty().getReactivatedCountPerOffset() != countPerOffset
						&& MnrlLoadData.propdetails.getAppProperty().getReactivatedCountPerOffset() > 0) {

					countPerOffset = MnrlLoadData.propdetails.getAppProperty().getReactivatedCountPerOffset();
				}

				if (count > 0) {
					int cnt = count / countPerOffset;//
					int cnt2 = count % countPerOffset;
					if (cnt2 > 0) {
						cnt = cnt + 1;
						partialCnt = true;
					}
					// cnt=180 ratelimit=55 set4

					int set = cnt / rateLimit;
					int setRemaining = cnt % rateLimit;
					if (setRemaining > 0) {
						set = set + 1;
					}
					// set 4
					LOGGER.info("cnt [{}] cnt2[{}] set[{}]", cnt, cnt2, set);
					int rateCount = 0;

					if (set == 1) {
						rateCount = cnt;
						// exportfile=true;

					} else {
						rateCount = rateLimit;
					}
					try {
						for (j = 1; j <= rateCount; j++) {
							MnrlReactivatedDataRequestV2 mnrlDataRequest = new MnrlReactivatedDataRequestV2();
							MNRLFetchDataRequestV2 mnrlFetchData_Request = new MNRLFetchDataRequestV2();
							MnrlDataBodyV2 body = new MnrlDataBodyV2();
							MNRLHeadersV2 headers = new MNRLHeadersV2();
							MnrlDataPayloadV2 payload = new MnrlDataPayloadV2();
							payload.setBankId(MnrlLoadData.tokenReqDetails.getBankId());
							if (j == 1) {
								payload.setOffset(j - 1);
							} else {
								payload.setOffset(countPerOffset * (j - 1));
							}
							LOGGER.debug("Offset [{}]", payload.getOffset());
							payload.setDate(mnrlReactivatedCountRequest.getMNRLReactiveNumRequest().getBody()
									.getPayload().getDate());

							if (partialCnt && j == (cnt)) {
								LOGGER.info("partially count");
								payload.setCount(cnt2);
							} else {
								payload.setCount(countPerOffset);
							}
							headers.setAuthorization(token);
							body.setPayload(payload);
							mnrlFetchData_Request.setBody(body);
							mnrlFetchData_Request.setHeaders(headers);
							mnrlDataRequest.setMNRLReactiveDataRequest(mnrlFetchData_Request);
							LOGGER.info("Reactiavated Data request [{}]", MnrlLoadData.gson.toJson(mnrlDataRequest));
							ReactivatedReqData_DAO reactivatedReqData_DAO = saveReactivatedRequestData(mnrlDataRequest);
							MnrlReactivatedDataResponseV2 mnrlresponse = new MnrlReactivatedDataResponseV2();
							String resData = mnrlReactivatedDataService.getMnrlReactivatedData(mnrlDataRequest);
							if ((StringUtils.isNotBlank(resData) && resData.contains("!_!"))) {
								String errorarray[] = resData.split("!_!");
								LOGGER.info("Response Failure with status code [{}] Response [{}]", errorarray[0],
										errorarray[1]);
								if (errorarray[0].equals("403")) {
									token = mnrlAuthService.getMnrlAuthtoken(MnrlLoadData.tokenReqDetails.getEmail(),
											MnrlLoadData.tokenReqDetails.getSecureterm());
									if (StringUtils.isBlank(token)) {
										LOGGER.info("retry block");
										callMnrlTaskScheduler(j - 1, countPerOffset, j - 1, rateLimit, cnt,
												reactivatedDataListtotal,
												mnrlReactivatedCountRequest.getMNRLReactiveNumRequest().getBody()
														.getPayload().getDate(),
												cnt2, false, token, MnrlLoadData.propdetails.getAppProperty()
														.getReactivatedRetryIntervalTime(),
												partialCnt, countDao);
										/*
										 * MnrlTask_Vo task_vo = new MnrlTask_Vo(); task_vo.setFromCount(j - 1);
										 * task_vo.setOffLimit(countPerOffset); task_vo.setOffsetNumber(j - 1);
										 * task_vo.setRateLimit(rateLimit); task_vo.setTotalSize(cnt);
										 * task_vo.setMnrlDataListtotal(mnrlDataListtotal);
										 * task_vo.setReqdate(mnrlCountRequest.getMNRLFetchCount_Request().getBody()
										 * .getPayload().getDate()); if (partialCnt) { task_vo.setPartialLimit(cnt2); }
										 * task_vo.setRetryDataDownload(false); task_vo.setToken(token); Date date = new
										 * Date(System.currentTimeMillis() +
										 * MnrlLoadData.propdetails.getAppProperty().getRetryIntervalTime());
										 * 
										 * MnrlTaskService service = context.getBean(MnrlTaskService.class);
										 * service.setMrnlTaskVo(task_vo); datatask.schedule(service, date);
										 */
										break;
									} else {
										j = j - 1;
									}
								} else {
									LOGGER.info("retry block");
									callMnrlTaskScheduler(j - 1, countPerOffset, j - 1, rateLimit, cnt,
											reactivatedDataListtotal,
											mnrlReactivatedCountRequest.getMNRLReactiveNumRequest().getBody()
													.getPayload().getDate(),
											cnt2, true, token,
											MnrlLoadData.propdetails.getAppProperty().getReactivatedRetryIntervalTime(),
											partialCnt, countDao);

									break;
								}
							} else {
								mnrlresponse = MnrlLoadData.gson.fromJson(resData, MnrlReactivatedDataResponseV2.class);

								String decryptData = utilService.decryptMnrlData(resData, "MNRLREACTIVATEDDATAV2");

								MrnlDecryptedData_VO decryptDataObj = MnrlLoadData.gson.fromJson(decryptData,
										MrnlDecryptedData_VO.class);

								LOGGER.info("Decrypt Data [{}]", decryptDataObj.getData().size());

								List<ReactivatedData_DAO> mnrlDataList = saveReactivatedData(decryptDataObj,
										String.valueOf(reactivatedReqData_DAO.getId()), payload.getDate());

								reactivatedDataListtotal.addAll(mnrlDataList);

								LOGGER.info("MNRL Reactivated Data Saved successfully");
								if (j == cnt) {
									LOGGER.info("total  size [{}}", reactivatedDataListtotal.size());
									updateReactivatedCountStatus(countDao, Constants.COMPLETED);
									reactivatedDataListtotal.clear();
									break;
								}
								LOGGER.info("J values [{}]", j);
								if (j == rateLimit) {
									LOGGER.info("inside schedular ");
									callMnrlTaskScheduler(j, countPerOffset, j, rateLimit, cnt,
											reactivatedDataListtotal,
											mnrlReactivatedCountRequest.getMNRLReactiveNumRequest().getBody()
													.getPayload().getDate(),
											cnt2, true, token,
											MnrlLoadData.propdetails.getAppProperty().getReactivatedReqIntervalTime(),
											partialCnt, countDao);

								}

							}
						}
					} catch (Exception e) {
						LOGGER.info("retry block in exception", e);
						LOGGER.info(
								"J [{}] countPerOffset [{}] rateLimit [{}] cnt[{}] rateLimit [{}] mnrlDataListtotal size [{}] cnt2[{}] fetch date[{}]",
								j, countPerOffset, rateLimit, cnt, rateLimit, reactivatedDataListtotal.size(), cnt2,
								mnrlReactivatedCountRequest.getMNRLReactiveNumRequest().getBody().getPayload()
										.getDate());
						callMnrlTaskScheduler(j - 1, countPerOffset, j - 1, rateLimit, cnt, reactivatedDataListtotal,
								mnrlReactivatedCountRequest.getMNRLReactiveNumRequest().getBody().getPayload()
										.getDate(),
								cnt2, true, token,
								MnrlLoadData.propdetails.getAppProperty().getReactivatedRetryIntervalTime(), partialCnt,
								countDao);
						/*
						 * MnrlTask_Vo task_vo = new MnrlTask_Vo(); task_vo.setFromCount(j - 1);
						 * task_vo.setOffLimit(countPerOffset); task_vo.setOffsetNumber(j - 1);
						 * task_vo.setRateLimit(rateLimit); task_vo.setTotalSize(cnt);
						 * task_vo.setMnrlDataListtotal(mnrlDataListtotal); task_vo.setReqdate(
						 * mnrlCountRequest.getMNRLFetchCount_Request().getBody().getPayload().getDate()
						 * ); if (partialCnt) { task_vo.setPartialLimit(cnt2); } Date date = new
						 * Date(System.currentTimeMillis() +
						 * MnrlLoadData.propdetails.getAppProperty().getRetryIntervalTime());
						 * 
						 * MnrlTaskService service = context.getBean(MnrlTaskService.class);
						 * service.setMrnlTaskVo(task_vo); datatask.schedule(service, date);
						 */
					}

				} else {
					LOGGER.info("No Data for the date [{}]",
							mnrlReactivatedCountRequest.getMNRLReactiveNumRequest().getBody().getPayload().getDate());
				}
			} else {
				LOGGER.info("Received Token Empty");
				callMnrlTaskScheduler(0, 0, 0, 0, 0, reactivatedDataListtotal, null, 0, false, token,
						MnrlLoadData.propdetails.getAppProperty().getReactivatedRetryIntervalTime(), partialCnt, null);
				/*
				 * MnrlTask_Vo task_vo = new MnrlTask_Vo(); task_vo.setRetryDataDownload(false);
				 * // task_vo.setToken(token); Date date = new Date( System.currentTimeMillis()
				 * + MnrlLoadData.propdetails.getAppProperty().getRetryIntervalTime());
				 * 
				 * MnrlTaskService service = context.getBean(MnrlTaskService.class);
				 * service.setMrnlTaskVo(task_vo); datatask.schedule(service, date);
				 */
			}

		} catch (Exception e) {
			LOGGER.error("Exception ", e);
			e.printStackTrace();
			callMnrlTaskScheduler(0, 0, 0, 0, 0, reactivatedDataListtotal, fetchDate, 0, false, token,
					MnrlLoadData.propdetails.getAppProperty().getReactivatedRetryIntervalTime(), false, null);
			/*
			 * MnrlTask_Vo task_vo = new MnrlTask_Vo(); task_vo.setRetryDataDownload(false);
			 * if (count == -1) { task_vo.setToken(token); } Date date = new Date(
			 * System.currentTimeMillis() +
			 * MnrlLoadData.propdetails.getAppProperty().getRetryIntervalTime());
			 * 
			 * MnrlTaskService service = context.getBean(MnrlTaskService.class);
			 * service.setMrnlTaskVo(task_vo); datatask.schedule(service, date);
			 */
		}

	}

	public List<ReactivatedData_DAO> saveReactivatedData(MrnlDecryptedData_VO decryptDataObj, String id,
			String fetchDate) {

		// StreamBuilder builder=new
		// StreamBuilder("mnrldata").format("csv").addRecord(ATRFileRequest_Vo.class);
		// StreamFactory factory = StreamFactory.newInstance();
		// load the mapping file from the working directory
		// factory.load(MnrlLoadData.propdetails.getAppProperty().getMnrlXMLPath());
		// factory.define(builder);
		// BeanWriter output = factory.createWriter("mnrldata",new
		// File(MnrlLoadData.propdetails.getAppProperty().getMnrlCsvPath()));
		// output.write("header", null);
		List<ReactivatedData_DAO> reactivatedDataList = new ArrayList<>();

		for (int i = 0; i < decryptDataObj.getData().size(); i++) {
			ReactivatedData_DAO dao = new ReactivatedData_DAO();
			dao.setMobileNo(decryptDataObj.getData().get(i).getMobile_no());
			dao.setDateOfReactivation(decryptDataObj.getData().get(i).getDate_of_reactivation());
			dao.setReactivationId(decryptDataObj.getData().get(i).getReactivation_id());
			dao.setReactivatedReqDataId(id);
			dao.setFetchDate(fetchDate);
			if (MnrlLoadData.reactivationMap.containsKey(decryptDataObj.getData().get(i).getReactivation_id())) {
				dao.setReactivationDescription(
						MnrlLoadData.reactivationMap.get(decryptDataObj.getData().get(i).getReactivation_id()));
			}
			dao.setProcessFlag("N");
			dao.setUuid(commonMethodUtils.getUuid());
			// dao.setMnrlReqDataId("1001");

			// mrnlDataRepo.save(dao);

			reactivatedDataList.add(dao);

			/*
			 * ATRFileRequest_Vo atr = new ATRFileRequest_Vo();
			 * atr.setMobile_no(dao.getMobileNo()); atr.setLsa_id(dao.getLsaId());
			 * atr.setLsa_description(dao.getLsaName()); atr.setTsp_id(dao.getTspId());
			 * atr.setTsp_description(dao.getTspName());
			 * atr.setDisconnection_date(dao.getDateOfDisconnection());
			 * atr.setMnrl_disconnection_reason(dao.getDisconnectingReasonId());
			 * atr.setMnrl_disconnection_reason_decription(dao.getDisconnectingReason());
			 * 
			 * output.write(atr);
			 */
		}

		reactivatedDataRepo.saveAll(reactivatedDataList);
		// output.flush();
		// output.close();

		// exportFile(mnrlDataList);
		return reactivatedDataList;
	}

	public ReactivatedReqData_DAO saveReactivatedRequestData(MnrlReactivatedDataRequestV2 mnrlReactivedDataRequest) {
		ReactivatedReqData_DAO reqDataDao = new ReactivatedReqData_DAO();
		// MnrlReqData_DAO reqDataDao= new MnrlReqData_DAO();
		reqDataDao.setBankId(mnrlReactivedDataRequest.getMNRLReactiveDataRequest().getBody().getPayload().getBankId());
		reqDataDao.setOffset(mnrlReactivedDataRequest.getMNRLReactiveDataRequest().getBody().getPayload().getOffset());
		reqDataDao.setRecCnt(mnrlReactivedDataRequest.getMNRLReactiveDataRequest().getBody().getPayload().getCount());

		reactivatedReqDataRepo.save(reqDataDao);

		return reqDataDao;

	}

	public void callMnrlTaskScheduler(int fromCount, int offLimit, int offSet, int rateLimit, int totalSize,
			List<ReactivatedData_DAO> totalDataList, String reqDate, int partialLimit, boolean retryData, String token,
			long timeInterval, boolean partialCnt, ReactivatedCountDetails_DAO countDao) {
		LOGGER.info("calling task schedular");
		LOGGER.info("fromCount [{}] countPerOffset [{}] offsetNumber [{}] rateLimit [{}] "
				+ "totcalcount[{}]  mnrlReactivatedDataListtotal size [{}] particalcount[{}] fetch date[{}] retryData[{}] token [{}]"
				+ "timeInterval [{}] booleanpartialcnt[{}]", fromCount, offLimit, offSet, rateLimit, totalSize,
				totalDataList.size(), partialLimit, reqDate, retryData, token, timeInterval, partialCnt);
		MnrlTask_Vo task_vo = new MnrlTask_Vo();

		task_vo.setFromCount(fromCount);
		task_vo.setOffLimit(offLimit);
		task_vo.setOffsetNumber(offSet);
		task_vo.setRateLimit(rateLimit);
		task_vo.setTotalSize(totalSize);
		task_vo.setReactivatedDataListtotal(totalDataList);
		task_vo.setReqdate(reqDate);
		if (partialCnt) {
			task_vo.setPartialLimit(partialLimit);
		}
		if (partialLimit > 0) {
			task_vo.setPartialLimit(partialLimit);
		}
		task_vo.setRetryDataDownload(retryData);
		task_vo.setToken(token);
		task_vo.setReactivatedCountDetails(countDao);

		MnrlReactivatedTaskServiceV2 service = context.getBean(MnrlReactivatedTaskServiceV2.class);
		service.setMrnlTaskVo(task_vo);

		Date date = new Date(System.currentTimeMillis() + timeInterval);
		datatask.schedule(service, date);
		LOGGER.info("Task schedular scheduled at [{}]", date);
		// return service;
	}

	public ReactivatedCountDetails_DAO saveReactivatedCount(String fetchDate, int count, String status) {

		ReactivatedCountDetails_DAO countDetails = new ReactivatedCountDetails_DAO();
		countDetails.setFetchDate(fetchDate);
		countDetails.setCount(count);
		if (count == 0) {
			countDetails.setStatus(Constants.COMPLETED);
		} else {
			countDetails.setStatus(status);
		}

		countDetails = countRepo.save(countDetails);

		LOGGER.info("Count Entity [{}]", countDetails.getId());

		return countDetails;

	}

	public ReactivatedCountDetails_DAO updateReactivatedCountStatus(ReactivatedCountDetails_DAO countDetails,
			String status) {

		countDetails.setStatus(status);

		countDetails = countRepo.save(countDetails);

		LOGGER.info("Updated Count Entity [{}][{}][{}][{}]", countDetails.getId(), countDetails.getCount(),
				countDetails.getStatus(), countDetails.getFetchDate());

		return countDetails;

	}
}
