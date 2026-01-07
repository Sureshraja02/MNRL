package com.fisglobal.fsg.dip.fri.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fisglobal.fsg.dip.core.config.MnrlLoadData;
import com.fisglobal.fsg.dip.core.convertor.CryptoApp;
import com.fisglobal.fsg.dip.core.service.CommonMethodUtils;
import com.fisglobal.fsg.dip.core.utils.Constants;
import com.fisglobal.fsg.dip.entity.FriCountDetails_DAO;
import com.fisglobal.fsg.dip.entity.FriData_DAO;
import com.fisglobal.fsg.dip.entity.FriReqData_DAO;
import com.fisglobal.fsg.dip.entity.MnrlCountDetails_DAO;
import com.fisglobal.fsg.dip.entity.impl.FriCountDetailsImpl;
import com.fisglobal.fsg.dip.entity.repo.FriCountDetails_Repo;
import com.fisglobal.fsg.dip.entity.repo.FriDataRepo;
import com.fisglobal.fsg.dip.entity.repo.FriReqDataRepo;
import com.fisglobal.fsg.dip.fri.entity.FRIBody;
import com.fisglobal.fsg.dip.fri.entity.FRICountRequest;
import com.fisglobal.fsg.dip.fri.entity.FRIDataRequest;
import com.fisglobal.fsg.dip.fri.entity.FRIDataResponse;
import com.fisglobal.fsg.dip.fri.entity.FRIDecryptData;
import com.fisglobal.fsg.dip.fri.entity.FRIFetchDataRequest;
import com.fisglobal.fsg.dip.fri.entity.FRIHeaders;
import com.fisglobal.fsg.dip.fri.entity.FRIPayload;
import com.fisglobal.fsg.dip.request.entity.MnrlTask_Vo;

@Service
public class FRIService {

	private static final Logger LOGGER = LoggerFactory.getLogger(FRIService.class);

	List<FriData_DAO> friDataListtotal = new ArrayList<>();

	@Inject
	private FriDataService friDataService;

	@Inject
	private FriDataRepo friDataRepo;

	@Inject
	private FriReqDataRepo friReqDataRepo;

	@Inject
	private FriAuthService friAuthService;

	@Inject
	private FriCountService friCountService;

	@Inject
	private CommonMethodUtils commonMethodUtils;

	@Inject
	private CryptoApp utilService;

	@Inject
	private TaskScheduler datatask;

	// private long timeOut = 30000;

	@Inject
	private ApplicationContext context;

	@Value("#{'${fri.process.flag}'.split(',')}")
	private List<String> updateProcessFlag;

	@Inject
	private FriCountDetails_Repo countRepo;

	@Inject
	private FriCountDetailsImpl friCountDetailsImpl;

	@Scheduled(cron = "${fri.cron:0 56 11 1/1 * ?}")
	public void friDataDownload() {
		// LOGGER.info("trigger");
		// trigger(null);

		// LOGGER.info("trigger");
		if ("Y".equals(MnrlLoadData.propdetails.getAppProperty().getFriCountDateFlag())) {
			String manualDate = MnrlLoadData.propdetails.getAppProperty().getFriCountDate();

			List<FriCountDetails_DAO> inProgressList = friCountDetailsImpl.getFriCountData(manualDate,
					Constants.INPROGRESS, LocalDate.now());

			int inProgressCnt = inProgressList.size();
			if (inProgressCnt > 0) {
				LOGGER.info("For given date [{}], download is in progress ", manualDate);
				return;
			}

			List<FriCountDetails_DAO> completedList = friCountDetailsImpl.getFriCountData(manualDate,
					Constants.COMPLETED, LocalDate.now());

			int completedCnt = completedList.size();

			if (completedCnt == 0) {
				trigger(null, manualDate);
			} else {
				LOGGER.info("Given date [{}] has been downloaded already ", manualDate);
			}
		} else {
			FriCountDetails_DAO maxDateObj = countRepo.getMaxFetchDate();
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
				if ((!startDate.isAfter(currentDate) && (friDataListtotal.size() == 0))) {
					LOGGER.info("list size [{}]", friDataListtotal.size());
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

		int rateLimit = MnrlLoadData.propdetails.getAppProperty().getFriReqCount();
		int j = 0;
		int count = -1;
		try {
			LOGGER.info("trigger");
			// boolean exportfile=false;
			boolean partialCnt = false;

			FRICountRequest friCountRequest = new FRICountRequest();
			if (StringUtils.isBlank(token)) {
				token = friAuthService.getFriAuthtoken(MnrlLoadData.friTokenRequestDetails.getEmail(),
						MnrlLoadData.friTokenRequestDetails.getSecureterm());
			}

			if (StringUtils.isNotBlank(token)) {
				friCountRequest = friCountService.setFriCountRequest(token, fetchDate);

				count = friCountService.getFriCount(friCountRequest).getCount();
				// int count=1198;
				LOGGER.info("fri count [{}]", count);
				FriCountDetails_DAO countDao = saveFriCount(fetchDate, count, Constants.INPROGRESS);
				int countPerOffset = 1000;
				if (MnrlLoadData.propdetails.getAppProperty().getFriCountPerOffset() != countPerOffset
						&& MnrlLoadData.propdetails.getAppProperty().getFriCountPerOffset() > 0) {

					countPerOffset = MnrlLoadData.propdetails.getAppProperty().getFriCountPerOffset();
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
							FRIDataRequest friDataRequest = new FRIDataRequest();
							FRIFetchDataRequest friFetchData_Request = new FRIFetchDataRequest();
							FRIBody body = new FRIBody();
							FRIHeaders headers = new FRIHeaders();
							FRIPayload payload = new FRIPayload();
							payload.setClientId(MnrlLoadData.friTokenRequestDetails.getBankId());
							if (j == 1) {
								payload.setOffset(j - 1);
							} else {
								payload.setOffset(countPerOffset * (j - 1));
							}
							LOGGER.debug("Offset [{}]", payload.getOffset());
							payload.setDate(friCountRequest.getFRIFetchCountRequest().getBody().getPayload().getDate());

							if (partialCnt && j == (cnt)) {
								LOGGER.info("partially count");
								payload.setCount(cnt2);
							} else {
								payload.setCount(countPerOffset);
							}
							headers.setAuthorization(token);
							body.setPayload(payload);
							friFetchData_Request.setBody(body);
							friFetchData_Request.setHeaders(headers);
							friDataRequest.setFRIFetchDataRequest(friFetchData_Request);
							LOGGER.info("FRI Data request [{}]", MnrlLoadData.gson.toJson(friDataRequest));

							FriReqData_DAO friReqData_DAO = saveFriRequestData(friDataRequest);
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
										LOGGER.info("retry block");
										callFriTaskScheduler(j - 1, countPerOffset, j - 1, rateLimit, cnt,
												friDataListtotal,
												friCountRequest.getFRIFetchCountRequest().getBody().getPayload()
														.getDate(),
												cnt2, false, token,
												MnrlLoadData.propdetails.getAppProperty().getFriRetryIntervalTime(),
												partialCnt, countDao);
										break;
									} else {
										j = j - 1;
									}
								} else {
									LOGGER.info("retry block");
									callFriTaskScheduler(j - 1, countPerOffset, j - 1, rateLimit, cnt, friDataListtotal,
											friCountRequest.getFRIFetchCountRequest().getBody().getPayload().getDate(),
											cnt2, true, token,
											MnrlLoadData.propdetails.getAppProperty().getFriRetryIntervalTime(),
											partialCnt, countDao);

									break;
								}
							} else {
								friresponse = MnrlLoadData.gson.fromJson(resData, FRIDataResponse.class);

								// LOGGER.debug("Encrypted Key [{}] Encrypted Data[{}], ",
								// friresponse.getEncryptedKey(),
								// friresponse.getEncryptedData());
								// MNRLDecryptionUtils obj = new MNRLDecryptionUtils();

								String decryptData = utilService.decryptData(resData);

								// LOGGER.debug("Decrypted Data[{}], ", decryptData);

								FRIDecryptData decryptDataObj = MnrlLoadData.gson.fromJson(decryptData,
										FRIDecryptData.class);

								LOGGER.info("Decrypt Data [{}]", decryptDataObj.getData().size());

								List<FriData_DAO> friDataList = saveFriData(decryptDataObj,
										String.valueOf(friReqData_DAO.getId()), payload.getDate());

								friDataListtotal.addAll(friDataList);

								LOGGER.info("FRI Data Saved successfully");
								if (j == cnt) {
									LOGGER.info("total export size [{}}", friDataListtotal.size());
									// exportFile(mnrlDataListtotal);
									updateFriCountStatus(countDao, Constants.COMPLETED);
									friDataListtotal.clear();
									break;
								}
								LOGGER.info("J values [{}]", j);
								if (j == rateLimit) {
									LOGGER.info("inside schedular ");
									callFriTaskScheduler(j, countPerOffset, j, rateLimit, cnt, friDataListtotal,
											friCountRequest.getFRIFetchCountRequest().getBody().getPayload().getDate(),
											cnt2, true, token,
											MnrlLoadData.propdetails.getAppProperty().getFriReqIntervalTime(),
											partialCnt, countDao);

								}

							}
						}
					} catch (Exception e) {
						LOGGER.info("retry block in exception", e);
						LOGGER.info(
								"J [{}] countPerOffset [{}] rateLimit [{}] cnt[{}] rateLimit [{}] mnrlDataListtotal size [{}] cnt2[{}] fetch date[{}]",
								j, countPerOffset, rateLimit, cnt, rateLimit, friDataListtotal.size(), cnt2,
								friCountRequest.getFRIFetchCountRequest().getBody().getPayload().getDate());
						callFriTaskScheduler(j - 1, countPerOffset, j - 1, rateLimit, cnt, friDataListtotal,
								friCountRequest.getFRIFetchCountRequest().getBody().getPayload().getDate(), cnt2, true,
								token, MnrlLoadData.propdetails.getAppProperty().getFriRetryIntervalTime(), partialCnt,
								countDao);

					}

				} else {
					LOGGER.info("No Data for the date [{}]",
							friCountRequest.getFRIFetchCountRequest().getBody().getPayload().getDate());
				}
			} else {
				LOGGER.info("Received Token Empty");
				callFriTaskScheduler(0, 0, 0, 0, 0, friDataListtotal, null, 0, false, token,
						MnrlLoadData.propdetails.getAppProperty().getFriRetryIntervalTime(), partialCnt, null);
			}

		} catch (Exception e) {
			LOGGER.error("Exception ", e);
			e.printStackTrace();
			callFriTaskScheduler(0, 0, 0, 0, 0, friDataListtotal, fetchDate, 0, false, token,
					MnrlLoadData.propdetails.getAppProperty().getFriRetryIntervalTime(), false, null);
		}

	}

	public List<FriData_DAO> saveFriData(FRIDecryptData decryptDataObj, String id, String fetchDate) {

		// StreamBuilder builder=new
		// StreamBuilder("mnrldata").format("csv").addRecord(ATRFileRequest_Vo.class);
		// StreamFactory factory = StreamFactory.newInstance();
		// load the mapping file from the working directory
		// factory.load(MnrlLoadData.propdetails.getAppProperty().getMnrlXMLPath());
		// factory.define(builder);
		// BeanWriter output = factory.createWriter("mnrldata",new
		// File(MnrlLoadData.propdetails.getAppProperty().getMnrlCsvPath()));
		// output.write("header", null);
		List<FriData_DAO> friDataList = new ArrayList<>();

		for (int i = 0; i < decryptDataObj.getData().size(); i++) {
			FriData_DAO dao = new FriData_DAO();
			dao.setLsaId(decryptDataObj.getData().get(i).getLsaId());
			dao.setMobileNo(decryptDataObj.getData().get(i).getMobileNo());
			if (MnrlLoadData.lsaDataMap.containsKey(decryptDataObj.getData().get(i).getLsaId())) {
				dao.setLsaName(MnrlLoadData.lsaDataMap.get(decryptDataObj.getData().get(i).getLsaId()));
			}

			dao.setTspId(decryptDataObj.getData().get(i).getTspId());
			if (MnrlLoadData.tspDataMap.containsKey(decryptDataObj.getData().get(i).getTspId())) {
				dao.setTspName(MnrlLoadData.tspDataMap.get(decryptDataObj.getData().get(i).getTspId()));
			}
			dao.setFriIndicator(decryptDataObj.getData().get(i).getFri());
			dao.setFetchDate(fetchDate);
			dao.setMnrlReqDataId(id);
			if (updateProcessFlag.contains(decryptDataObj.getData().get(i).getFri())) {
				dao.setProcessFlag("Y");
			} else {
				dao.setProcessFlag("N");
			}
			if (MnrlLoadData.friIndicatorMap.containsKey(decryptDataObj.getData().get(i).getFri())) {
				dao.setFriIndicatorDesc(MnrlLoadData.friIndicatorMap.get(decryptDataObj.getData().get(i).getFri()));
			}
			dao.setStatus(decryptDataObj.getData().get(i).getStatus());
			dao.setUuid(commonMethodUtils.getUuid());
			// dao.setMnrlReqDataId("1001");

			// mrnlDataRepo.save(dao);

			friDataList.add(dao);

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

		friDataRepo.saveAll(friDataList);
		// output.flush();
		// output.close();

		// exportFile(mnrlDataList);
		return friDataList;
	}

	/*
	 * public MnrlDataResponse getFRIAuth() throws Exception {
	 * 
	 * MnrlDataResponse mnrlAuthRequest = new MnrlDataResponse();
	 * 
	 * MnrlTokenRequestDetails tokenReq = new MnrlTokenRequestDetails();
	 * tokenReq.setEmail("NOREPLYCYBERALERT@indianbank.co.in");
	 * tokenReq.setPassword("Securepass@1234");
	 * 
	 * String reqData = gson.toJson(tokenReq);
	 * 
	 * String publicKeyPem = new String(Files.readAllBytes( Paths.get(
	 * "C:\\Users\\e5614002\\Downloads\\FRI-ASSIST\\FRI-ASSIST\\FRI-ASSIST\\diu_rsa_pub_key.pem"
	 * ))); mnrlAuthRequest = utilService.encryptData(publicKeyPem, reqData);
	 * 
	 * return mnrlAuthRequest;
	 * 
	 * }
	 */

	public FriReqData_DAO saveFriRequestData(FRIDataRequest friDataRequest) {
		FriReqData_DAO reqDataDao = new FriReqData_DAO();
		// MnrlReqData_DAO reqDataDao= new MnrlReqData_DAO();
		reqDataDao.setBankId(friDataRequest.getFRIFetchDataRequest().getBody().getPayload().getClientId());
		reqDataDao.setOffset(friDataRequest.getFRIFetchDataRequest().getBody().getPayload().getOffset());
		reqDataDao.setRecCnt(friDataRequest.getFRIFetchDataRequest().getBody().getPayload().getCount());

		friReqDataRepo.save(reqDataDao);

		return reqDataDao;

	}

	public void callFriTaskScheduler(int fromCount, int offLimit, int offSet, int rateLimit, int totalSize,
			List<FriData_DAO> totalDataList, String reqDate, int partialLimit, boolean retryData, String token,
			long timeInterval, boolean partialCnt, FriCountDetails_DAO countDao) {
		LOGGER.info("calling task schedular");
		LOGGER.info("fromCount [{}] countPerOffset [{}] offsetNumber [{}] rateLimit [{}] "
				+ "totcalcount[{}]  friDataListtotal size [{}] particalcount[{}] fetch date[{}] retryData[{}] token [{}]"
				+ "timeInterval [{}] booleanpartialcnt[{}]", fromCount, offLimit, offSet, rateLimit, totalSize,
				totalDataList.size(), partialLimit, reqDate, retryData, token, timeInterval, partialCnt);
		MnrlTask_Vo task_vo = new MnrlTask_Vo();

		task_vo.setFromCount(fromCount);
		task_vo.setOffLimit(offLimit);
		task_vo.setOffsetNumber(offSet);
		task_vo.setRateLimit(rateLimit);
		task_vo.setTotalSize(totalSize);
		task_vo.setFriDataListtotal(totalDataList);
		task_vo.setReqdate(reqDate);
		if (partialCnt) {
			task_vo.setPartialLimit(partialLimit);
		}
		if (partialLimit > 0) {
			task_vo.setPartialLimit(partialLimit);
		}
		task_vo.setRetryDataDownload(retryData);
		task_vo.setToken(token);
		task_vo.setFriCountDetails(countDao);
		FriTaskService service = context.getBean(FriTaskService.class);
		service.setMrnlTaskVo(task_vo);

		Date date = new Date(System.currentTimeMillis() + timeInterval);
		datatask.schedule(service, date);
		LOGGER.info("Task schedular scheduled at [{}]", date);
		// return service;
	}

	public FriCountDetails_DAO saveFriCount(String fetchDate, int count, String status) {

		FriCountDetails_DAO countDetails = new FriCountDetails_DAO();
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

	public FriCountDetails_DAO updateFriCountStatus(FriCountDetails_DAO countDetails, String status) {

		countDetails.setStatus(status);

		countDetails = countRepo.save(countDetails);

		LOGGER.info("Updated Count Entity [{}][{}][{}][{}]", countDetails.getId(), countDetails.getCount(),
				countDetails.getStatus(), countDetails.getFetchDate());

		return countDetails;

	}

}
