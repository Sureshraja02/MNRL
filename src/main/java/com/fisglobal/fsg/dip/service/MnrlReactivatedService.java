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
import com.fisglobal.fsg.dip.entity.ReactivatedData_DAO;
import com.fisglobal.fsg.dip.entity.ReactivatedReqData_DAO;
import com.fisglobal.fsg.dip.entity.repo.ReactivatedDataRepo;
import com.fisglobal.fsg.dip.entity.repo.ReactivatedReqDataRepo;
import com.fisglobal.fsg.dip.request.entity.ATRFileRequest_Vo;
import com.fisglobal.fsg.dip.request.entity.MNRLDataBody;
import com.fisglobal.fsg.dip.request.entity.MNRLDataPayload;
import com.fisglobal.fsg.dip.request.entity.MNRLFetchDataRequest;
import com.fisglobal.fsg.dip.request.entity.MNRLHeaders;
import com.fisglobal.fsg.dip.request.entity.MnrlAuthRequest;
import com.fisglobal.fsg.dip.request.entity.MnrlReactivatedCountRequest;
import com.fisglobal.fsg.dip.request.entity.MnrlReactivatedDataRequest;
import com.fisglobal.fsg.dip.request.entity.MnrlTask_Vo;
import com.fisglobal.fsg.dip.response.entity.MnrlDataResponse;
import com.fisglobal.fsg.dip.response.entity.MrnlDecryptedData_VO;

@Service
public class MnrlReactivatedService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MnrlReactivatedService.class);

	
	List<ReactivatedData_DAO> reactivatedDataListtotal = new ArrayList<>();

	@Inject
	private MnrlReactivatedDataService mnrlReactivatedDataService;

	@Inject
	private MNRLDecryptionUtils mrnlDecryptionUtils;

	@Inject
	private ReactivatedDataRepo reactivatedDataRepo;

	@Inject
	private ReactivatedReqDataRepo reactivatedReqDataRepo;

	@Inject
	private MnrlAuthService mnrlAuthService;

	@Inject
	private MnrlReactivatedCountService mnrlReactivatedCountService;

	@Inject
	private CommonMethodUtils commonMethodUtils;

	@Inject
	private TaskScheduler datatask;

	// private long timeOut = 30000;

	@Inject
	private ApplicationContext context;

	// @Scheduled(initialDelay = 20000, fixedRateString = "300000")
	//@Scheduled(cron = "${reactivated.cron:0 56 11 1/1 * ?}")
	public void trigger() {

		int rateLimit = MnrlLoadData.propdetails.getAppProperty().getReactivatedReqCount();
		try {
			LOGGER.info("trigger");
			// boolean exportfile=false;
			boolean partialCnt = false;

			MnrlReactivatedCountRequest mnrlReactivatedCountRequest = new MnrlReactivatedCountRequest();

			String token = mnrlAuthService.getMnrlAuthtoken(getMnrlAuth());

			if (token != null) {
				mnrlReactivatedCountRequest = mnrlReactivatedCountService.setMnrlReactivatedCountRequest(token);

				int count = mnrlReactivatedCountService.getMnrlReactivatedCount(mnrlReactivatedCountRequest).getCount();
				// int count=1198;
				LOGGER.info("Mrnl Reactiaved count [{}]", count);

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

					for (int j = 1; j <= rateCount; j++) {
						MnrlReactivatedDataRequest mnrlReactivatedDataRequest = new MnrlReactivatedDataRequest();
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
						payload.setDate(mnrlReactivatedCountRequest.getMNRLReactiveNum_Request().getBody().getPayload()
								.getDate());

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
						mnrlReactivatedDataRequest.setMNRLReactiveData_Request(mnrlFetchData_Request);
						LOGGER.info("Reactivated Data request [{}]", MnrlLoadData.gson.toJson(mnrlFetchData_Request));
						ReactivatedReqData_DAO reactivatedReqData_DAO = saveReactivatedRequestData(
								mnrlReactivatedDataRequest);
						MnrlDataResponse mnrlresponse = new MnrlDataResponse();
						String resData = mnrlReactivatedDataService.getMnrlReactivatedData(mnrlReactivatedDataRequest);
						if ((StringUtils.isNotBlank(resData) && resData.contains("!_!"))) {
							String errorarray[] = resData.split("!_!");
							LOGGER.info("Response Failure with status code [{}] Response [{}]", errorarray[0],
									errorarray[1]);
							if (errorarray[0].equals("403")) {
								token = mnrlAuthService.getMnrlAuthtoken(getMnrlAuth());
								j = j - 1;
							} else {
								LOGGER.info("retry block");
								MnrlTask_Vo task_vo = new MnrlTask_Vo();
								task_vo.setFromCount(j - 1);
								task_vo.setOffLimit(countPerOffset);
								task_vo.setOffsetNumber(j - 1);
								task_vo.setRateLimit(rateLimit);
								task_vo.setTotalSize(cnt);
								task_vo.setReactivatedDataListtotal(reactivatedDataListtotal);
								task_vo.setReqdate(mnrlReactivatedCountRequest.getMNRLReactiveNum_Request().getBody()
										.getPayload().getDate());
								if (partialCnt) {
									task_vo.setPartialLimit(cnt2);
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

							LOGGER.debug("Decrypted Key [{}] Decrypted Data[{}], ", AESKey, decryptData);

							MrnlDecryptedData_VO decryptDataObj = MnrlLoadData.gson.fromJson(decryptData,
									MrnlDecryptedData_VO.class);

							LOGGER.info("Decrypt Data [{}]", decryptDataObj.getData().size());

							List<ReactivatedData_DAO> mnrlDataList = saveReactivatedData(decryptDataObj,
									String.valueOf(reactivatedReqData_DAO.getId()), payload.getDate());

							reactivatedDataListtotal.addAll(mnrlDataList);

							LOGGER.info("Reactiaved Data Saved successfully");
							if (j == cnt) {
								LOGGER.info("total export size [{}}", reactivatedDataListtotal.size());
								// exportFile(mnrlDataListtotal);
								reactivatedDataListtotal.clear();
								break;
							}
							LOGGER.info("J values [{}]", j);
							if (j == rateLimit) {
								LOGGER.info("inside schedular ");
								MnrlTask_Vo task_vo = new MnrlTask_Vo();
								task_vo.setFromCount(j);
								task_vo.setOffLimit(countPerOffset);
								task_vo.setOffsetNumber(j);
								task_vo.setRateLimit(rateLimit);
								task_vo.setTotalSize(cnt);
								task_vo.setReactivatedDataListtotal(reactivatedDataListtotal);
								task_vo.setReqdate(mnrlReactivatedCountRequest.getMNRLReactiveNum_Request().getBody()
										.getPayload().getDate());
								if (partialCnt) {
									task_vo.setPartialLimit(cnt2);
								}
								Date date = new Date(System.currentTimeMillis()
										+ MnrlLoadData.propdetails.getAppProperty().getReactivatedReqIntervalTime());
								MnrlReactivatedTaskService service = context.getBean(MnrlReactivatedTaskService.class);
								service.setMrnlTaskVo(task_vo);
								datatask.schedule(service, date);

							}

						}
					}

				} else {
					LOGGER.info("No Data for the date [{}]",
							mnrlReactivatedCountRequest.getMNRLReactiveNum_Request().getBody().getPayload().getDate());
				}
			} else {
				LOGGER.info("Received Token Empty");
			}

		} catch (Exception e) {
			LOGGER.error("Exception ", e);
			e.printStackTrace();
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

	public ReactivatedReqData_DAO saveReactivatedRequestData(MnrlReactivatedDataRequest mnrlReactivedDataRequest) {
		ReactivatedReqData_DAO reqDataDao = new ReactivatedReqData_DAO();
		// MnrlReqData_DAO reqDataDao= new MnrlReqData_DAO();
		reqDataDao.setBankId(mnrlReactivedDataRequest.getMNRLReactiveData_Request().getBody().getPayload().getBankId());
		reqDataDao.setOffset(mnrlReactivedDataRequest.getMNRLReactiveData_Request().getBody().getPayload().getOffset());
		reqDataDao.setRecCnt(mnrlReactivedDataRequest.getMNRLReactiveData_Request().getBody().getPayload().getCount());

		reactivatedReqDataRepo.save(reqDataDao);

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

}
