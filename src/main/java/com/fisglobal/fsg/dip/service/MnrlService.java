package com.fisglobal.fsg.dip.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.beanio.BeanWriter;
import org.beanio.StreamFactory;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fisglobal.fsg.dip.core.config.MnrlLoadData;
import com.fisglobal.fsg.dip.core.convertor.MNRLDecryptionUtils;
import com.fisglobal.fsg.dip.core.service.CommonMethodUtils;
import com.fisglobal.fsg.dip.core.service.RestClient;
import com.fisglobal.fsg.dip.core.utils.Constants;
import com.fisglobal.fsg.dip.entity.MnrlData_DAO;
import com.fisglobal.fsg.dip.entity.MnrlReqData_DAO;
import com.fisglobal.fsg.dip.entity.repo.MnrlDataRepo;
import com.fisglobal.fsg.dip.entity.repo.MnrlReqDataRepo;
import com.fisglobal.fsg.dip.request.entity.ATRFileRequest_Vo;
import com.fisglobal.fsg.dip.request.entity.MNRLDataBody;
import com.fisglobal.fsg.dip.request.entity.MNRLDataPayload;
import com.fisglobal.fsg.dip.request.entity.MNRLFetchDataRequest;
import com.fisglobal.fsg.dip.request.entity.MNRLHeaders;
import com.fisglobal.fsg.dip.request.entity.MnrlAuthRequest;
import com.fisglobal.fsg.dip.request.entity.MnrlCountRequest;
import com.fisglobal.fsg.dip.request.entity.MnrlDataRequest;
import com.fisglobal.fsg.dip.request.entity.MnrlTask_Vo;
import com.fisglobal.fsg.dip.response.entity.MnrlDataResponse;
import com.fisglobal.fsg.dip.response.entity.MrnlDecryptedData_VO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class MnrlService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MnrlService.class);

	List<MnrlData_DAO> mnrlDataListtotal = new ArrayList<>();

	@Inject
	private MnrlDataService mnrlDataService;

	@Inject
	private MNRLDecryptionUtils mrnlDecryptionUtils;

	@Inject
	private MnrlDataRepo mrnlDataRepo;

	@Inject
	private MnrlReqDataRepo mrnlReqDataRepo;

	@Inject
	private MnrlAuthService mnrlAuthService;

	@Inject
	private MnrlCountService mnrlCountService;

	@Inject
	private CommonMethodUtils commonMethodUtils;

	@Inject
	private TaskScheduler datatask;

	// private long timeOut = 30000;

	@Inject
	private ApplicationContext context;

	@Value("${mnrl.data.export:N}")
	private String mnrlExport;

	@Value("#{'${mnrl.process.flag}'.split(',')}")
	private List<String> updateProcessFlag;

	// @Scheduled(initialDelay = 20000, fixedRateString = "300000")
	//@Scheduled(cron = "${mnrl.cron:0 56 11 1/1 * ?}")
	public void mnrlDataDownload() {
		LOGGER.info("trigger");
		trigger(null);
	}

	public void trigger(String token) {

		int rateLimit = MnrlLoadData.propdetails.getAppProperty().getReqCount();
		int j = 0;
		int count = -1;
		try {
			
			// boolean exportfile=false;
			boolean partialCnt = false;

			MnrlCountRequest mnrlCountRequest = new MnrlCountRequest();
			if (StringUtils.isBlank(token)) {
				token = mnrlAuthService.getMnrlAuthtoken(getMnrlAuth());
			}

			if (StringUtils.isNotBlank(token)) {
				mnrlCountRequest = mnrlCountService.setMnrlCountRequest(token);

				count = mnrlCountService.getMnrlCount(mnrlCountRequest).getCount();
				// int count=1198;
				LOGGER.info("Mrnl count [{}]", count);

				int countPerOffset = 1000;
				if (MnrlLoadData.propdetails.getAppProperty().getCountPerOffset() != countPerOffset
						&& MnrlLoadData.propdetails.getAppProperty().getCountPerOffset() > 0) {

					countPerOffset = MnrlLoadData.propdetails.getAppProperty().getCountPerOffset();
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
							MnrlDataRequest mnrlDataRequest = new MnrlDataRequest();
							MNRLFetchDataRequest mnrlFetchData_Request = new MNRLFetchDataRequest();
							MNRLDataBody body = new MNRLDataBody();
							MNRLHeaders headers = new MNRLHeaders();
							MNRLDataPayload payload = new MNRLDataPayload();
							payload.setBankId(MnrlLoadData.tokenReqDetails.getBankId());
							if (j == 1) {
								payload.setOffset(j - 1);
							} else {
								payload.setOffset(countPerOffset * (j - 1));
							}
							LOGGER.debug("Offset [{}]", payload.getOffset());
							payload.setDate(
									mnrlCountRequest.getMNRLFetchCount_Request().getBody().getPayload().getDate());

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
							mnrlDataRequest.setMNRLFetchData_Request(mnrlFetchData_Request);
							LOGGER.info("Data request [{}]", MnrlLoadData.gson.toJson(mnrlDataRequest));
							MnrlReqData_DAO mnrlReqData_DAO = saveMnrlRequestData(mnrlDataRequest);
							MnrlDataResponse mnrlresponse = new MnrlDataResponse();
							String resData = mnrlDataService.getMnrlData(mnrlDataRequest);
							if ((StringUtils.isNotBlank(resData) && resData.contains("!_!"))) {
								String errorarray[] = resData.split("!_!");
								LOGGER.info("Response Failure with status code [{}] Response [{}]", errorarray[0],
										errorarray[1]);
								if (errorarray[0].equals("403")) {
									token = mnrlAuthService.getMnrlAuthtoken(getMnrlAuth());
									if (StringUtils.isBlank(token)) {
										LOGGER.info("retry block");
										callMnrlTaskScheduler(j - 1, countPerOffset, j - 1, rateLimit, cnt,
												mnrlDataListtotal,
												mnrlCountRequest.getMNRLFetchCount_Request().getBody().getPayload()
														.getDate(),
												cnt2, false, token,
												MnrlLoadData.propdetails.getAppProperty().getRetryIntervalTime(),
												partialCnt);
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
											mnrlDataListtotal,
											mnrlCountRequest.getMNRLFetchCount_Request().getBody().getPayload()
													.getDate(),
											cnt2, true, token,
											MnrlLoadData.propdetails.getAppProperty().getRetryIntervalTime(),
											partialCnt);

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

								List<MnrlData_DAO> mnrlDataList = saveMnrlData(decryptDataObj,
										String.valueOf(mnrlReqData_DAO.getId()), payload.getDate());

								mnrlDataListtotal.addAll(mnrlDataList);

								LOGGER.info("MNRL Data Saved successfully");
								if (j == cnt) {
									LOGGER.info("total  size [{}}", mnrlDataListtotal.size());
									if (mnrlExport.equals("Y")) {
										LOGGER.info("Exporting mnrl data");
										exportFile(mnrlDataListtotal);
									}
									mnrlDataListtotal.clear();
									break;
								}
								LOGGER.info("J values [{}]", j);
								if (j == rateLimit) {
									LOGGER.info("inside schedular ");
									callMnrlTaskScheduler(j, countPerOffset, j, rateLimit, cnt, mnrlDataListtotal,
											mnrlCountRequest.getMNRLFetchCount_Request().getBody().getPayload()
													.getDate(),
											cnt2, true, token,
											MnrlLoadData.propdetails.getAppProperty().getReqIntervalTime(),
											partialCnt);

								}

							}
						}
					} catch (Exception e) {
						LOGGER.info("retry block in exception",e);
						LOGGER.info(
								"J [{}] countPerOffset [{}] rateLimit [{}] cnt[{}] rateLimit [{}] mnrlDataListtotal size [{}] cnt2[{}] fetch date[{}]",
								j, countPerOffset, rateLimit, cnt, rateLimit, mnrlDataListtotal.size(), cnt2,
								mnrlCountRequest.getMNRLFetchCount_Request().getBody().getPayload().getDate());
						callMnrlTaskScheduler(j - 1, countPerOffset, j - 1, rateLimit, cnt, mnrlDataListtotal,
								mnrlCountRequest.getMNRLFetchCount_Request().getBody().getPayload().getDate(), cnt2,
								true, token, MnrlLoadData.propdetails.getAppProperty().getRetryIntervalTime(),
								partialCnt);
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
							mnrlCountRequest.getMNRLFetchCount_Request().getBody().getPayload().getDate());
				}
			} else {
				LOGGER.info("Received Token Empty");
				callMnrlTaskScheduler(0, 0, 0, 0, 0, mnrlDataListtotal, null, 0, false, token,
						MnrlLoadData.propdetails.getAppProperty().getRetryIntervalTime(), partialCnt);
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
			callMnrlTaskScheduler(0, 0, 0, 0, 0, mnrlDataListtotal, null, 0, false, token,
					MnrlLoadData.propdetails.getAppProperty().getRetryIntervalTime(), false);
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

	public List<MnrlData_DAO> saveMnrlData(MrnlDecryptedData_VO decryptDataObj, String id, String fetchDate) {

		// StreamBuilder builder=new
		// StreamBuilder("mnrldata").format("csv").addRecord(ATRFileRequest_Vo.class);
		// StreamFactory factory = StreamFactory.newInstance();
		// load the mapping file from the working directory
		// factory.load(MnrlLoadData.propdetails.getAppProperty().getMnrlXMLPath());
		// factory.define(builder);
		// BeanWriter output = factory.createWriter("mnrldata",new
		// File(MnrlLoadData.propdetails.getAppProperty().getMnrlCsvPath()));
		// output.write("header", null);
		List<MnrlData_DAO> mnrlDataList = new ArrayList<>();

		for (int i = 0; i < decryptDataObj.getData().size(); i++) {
			MnrlData_DAO dao = new MnrlData_DAO();
			dao.setLsaId(decryptDataObj.getData().get(i).getLsa_id());
			dao.setMobileNo(decryptDataObj.getData().get(i).getMobile_no());
			if (MnrlLoadData.lsaDataMap.containsKey(decryptDataObj.getData().get(i).getLsa_id())) {
				dao.setLsaName(MnrlLoadData.lsaDataMap.get(decryptDataObj.getData().get(i).getLsa_id()));
			}

			dao.setTspId(decryptDataObj.getData().get(i).getTsp_id());
			if (MnrlLoadData.tspDataMap.containsKey(decryptDataObj.getData().get(i).getTsp_id())) {
				dao.setTspName(MnrlLoadData.tspDataMap.get(decryptDataObj.getData().get(i).getTsp_id()));
			}
			dao.setDateOfDisconnection(decryptDataObj.getData().get(i).getDate_of_disconnection());
			dao.setDisconnectingReasonId(decryptDataObj.getData().get(i).getDisconnectionreason_id());

			if (MnrlLoadData.disconnectionDataMap
					.containsKey(decryptDataObj.getData().get(i).getDisconnectionreason_id())) {
				dao.setDisconnectingReason(MnrlLoadData.disconnectionDataMap
						.get(decryptDataObj.getData().get(i).getDisconnectionreason_id()));
			}
			dao.setMnrlReqDataId(id);
			dao.setFetchDate(fetchDate);
			if (updateProcessFlag.contains(decryptDataObj.getData().get(i).getDisconnectionreason_id())) {
				dao.setProcessFlag("Y");
			} else {
				dao.setProcessFlag("N");
			}
			// dao.setMnrlReqDataId("1001");

			// mrnlDataRepo.save(dao);

			mnrlDataList.add(dao);

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

		mrnlDataRepo.saveAll(mnrlDataList);
		// output.flush();
		// output.close();

		// exportFile(mnrlDataList);
		return mnrlDataList;
	}

	public MnrlAuthRequest getMnrlAuth() {

		MnrlAuthRequest mnrlAuthRequest = new MnrlAuthRequest();
		mnrlAuthRequest.setEmail(MnrlLoadData.tokenReqDetails.getEmail());
		mnrlAuthRequest.setPassword(decodePassword(MnrlLoadData.tokenReqDetails.getSecureterm()));

		return mnrlAuthRequest;

	}

	public String getCurrentDateAsString() {
		SimpleDateFormat SDFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String curr_date = SDFormat.format(cal.getTime());
		LOGGER.info("current date [{}]", curr_date);
		return curr_date;
	}

	public String decodePassword(String encStr) {
		byte[] pvtpasspharseByte = Base64.decodeBase64(encStr);
		String pvtpasspharseStr = new String(pvtpasspharseByte);
		StandardPBEStringEncryptor decryptor = new StandardPBEStringEncryptor();
		decryptor.setPassword(MnrlLoadData.propdetails.getAppProperty().getMastersecureterm());
		String password = decryptor.decrypt(pvtpasspharseStr);
		LOGGER.trace("JKS Password [{}]", password);
		return password;
	}

	public HttpClientConnectionManager httpClientConnectionManagerBean(int pool) {
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		connectionManager.setMaxTotal(pool);
		connectionManager.setDefaultMaxPerRoute(pool);
		return connectionManager;
	}

	public RestClient getRestClient() {
		RestClient restClient;
		if (MnrlLoadData.propdetails.getAppProperty().isProxyFlag()) {

			restClient = new RestClient(
					httpClientConnectionManagerBean(MnrlLoadData.propdetails.getAppProperty().getPool()),
					MnrlLoadData.propdetails.getAppProperty().getUserName(),
					MnrlLoadData.propdetails.getAppProperty().getUserSecureterm(),
					MnrlLoadData.propdetails.getAppProperty().getRestTimeOut(),
					MnrlLoadData.propdetails.getAppProperty().getRestReadTimeOut(),
					MnrlLoadData.propdetails.getAppProperty().getProxyHost(),
					MnrlLoadData.propdetails.getAppProperty().getProxyPort(),
					MnrlLoadData.propdetails.getAppProperty().isSslVerify());
		} else {
			restClient = new RestClient(
					httpClientConnectionManagerBean(MnrlLoadData.propdetails.getAppProperty().getPool()),
					MnrlLoadData.propdetails.getAppProperty().getUserName(),
					MnrlLoadData.propdetails.getAppProperty().getUserSecureterm(),
					MnrlLoadData.propdetails.getAppProperty().getRestTimeOut(),
					MnrlLoadData.propdetails.getAppProperty().getRestReadTimeOut(),
					MnrlLoadData.propdetails.getAppProperty().isSslVerify());

		}
		return restClient;
	}

	public MnrlReqData_DAO saveMnrlRequestData(MnrlDataRequest mnrlDataRequest) {
		MnrlReqData_DAO reqDataDao = new MnrlReqData_DAO();
		// MnrlReqData_DAO reqDataDao= new MnrlReqData_DAO();
		reqDataDao.setBankId(mnrlDataRequest.getMNRLFetchData_Request().getBody().getPayload().getBankId());
		reqDataDao.setOffset(mnrlDataRequest.getMNRLFetchData_Request().getBody().getPayload().getOffset());
		reqDataDao.setRecCnt(mnrlDataRequest.getMNRLFetchData_Request().getBody().getPayload().getCount());

		mrnlReqDataRepo.save(reqDataDao);

		return reqDataDao;

	}

	public void exportFile(List<MnrlData_DAO> mnrlDataList) {

		// StreamBuilder builder=new
		// StreamBuilder("mnrldata").format("csv").addRecord(ATRFileRequest_Vo.class);
		StreamFactory factory = StreamFactory.newInstance();
		// load the mapping file from the working directory
		factory.load(MnrlLoadData.propdetails.getAppProperty().getMnrlXMLPath());
		String csvFileName = MnrlLoadData.propdetails.getAppProperty().getMnrlCsvPath() + "_"
				+ commonMethodUtils.toChangeDateFormat("ddMMyyyyHHmmssSSS", new Date()) + Constants.CSV;
		// factory.define(builder);
		BeanWriter output = factory.createWriter("mnrldata", new File(csvFileName));
		// output.write("header", null);
		// mnrlDataList = new ArrayList<>();
		LOGGER.debug("export size [{}}", mnrlDataList.size());
		for (int i = 0; i < mnrlDataList.size(); i++) {

			ATRFileRequest_Vo atr = new ATRFileRequest_Vo();
			atr.setMobile_no(mnrlDataList.get(i).getMobileNo());
			atr.setLsa_id(mnrlDataList.get(i).getLsaId());
			atr.setLsa_description(mnrlDataList.get(i).getLsaName());
			atr.setTsp_id(mnrlDataList.get(i).getTspId());
			atr.setTsp_description(mnrlDataList.get(i).getTspName());
			atr.setDisconnection_date(mnrlDataList.get(i).getDateOfDisconnection());
			atr.setMnrl_disconnection_reason(mnrlDataList.get(i).getDisconnectingReasonId());
			atr.setMnrl_disconnection_reason_decription(mnrlDataList.get(i).getDisconnectingReason());
			output.write(atr);
		}
		output.flush();
		output.close();

		LOGGER.info("Data exported success fully");

	}

	public void callMnrlTaskScheduler(int fromCount, int offLimit, int offSet, int rateLimit, int totalSize,
			List<MnrlData_DAO> totalDataList, String reqDate, int partialLimit, boolean retryData, String token,
			long timeInterval, boolean partialCnt) {
		LOGGER.info("calling task schedular");
		LOGGER.info(
				"fromCount [{}] countPerOffset [{}] offsetNumber [{}] rateLimit [{}] "
				+ "totcalcount[{}]  mnrlDataListtotal size [{}] particalcount[{}] fetch date[{}] retryData[{}] token [{}]"
				+ "timeInterval [{}] booleanpartialcnt[{}]",
				fromCount, offLimit, offSet, rateLimit, totalSize,  totalDataList.size(), partialLimit,
				reqDate,retryData,token,timeInterval,partialCnt);
		MnrlTask_Vo task_vo = new MnrlTask_Vo();

		task_vo.setFromCount(fromCount);
		task_vo.setOffLimit(offLimit);
		task_vo.setOffsetNumber(offSet);
		task_vo.setRateLimit(rateLimit);
		task_vo.setTotalSize(totalSize);
		task_vo.setMnrlDataListtotal(totalDataList);
		task_vo.setReqdate(reqDate);
		if (partialCnt) {
			task_vo.setPartialLimit(partialLimit);
		}
		if (partialLimit > 0) {
			task_vo.setPartialLimit(partialLimit);
		}
		task_vo.setRetryDataDownload(retryData);
		task_vo.setToken(token);

		MnrlTaskService service = context.getBean(MnrlTaskService.class);
		service.setMrnlTaskVo(task_vo);

		Date date = new Date(System.currentTimeMillis() + timeInterval);
		datatask.schedule(service, date);
		LOGGER.info("Task schedular scheduled at [{}]", date);
		// return service;
	}
}
