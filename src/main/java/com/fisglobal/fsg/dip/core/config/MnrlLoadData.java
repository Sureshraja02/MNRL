package com.fisglobal.fsg.dip.core.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import com.fisglobal.fsg.dip.core.comm.beans.AppProperty;
import com.fisglobal.fsg.dip.core.comm.beans.HeaderProperty;
import com.fisglobal.fsg.dip.core.comm.beans.PreProdHeaderProperty;
import com.fisglobal.fsg.dip.core.comm.beans.ProdHeaderProperty;
import com.fisglobal.fsg.dip.core.comm.beans.PropertyDetails;
import com.fisglobal.fsg.dip.core.comm.beans.UATHeaderProperty;
import com.fisglobal.fsg.dip.core.service.RestClient;
import com.fisglobal.fsg.dip.core.utils.Constants;
import com.fisglobal.fsg.dip.entity.FriIndicatorDetails_DAO;
import com.fisglobal.fsg.dip.entity.MnrlActionDetail_DAO;
import com.fisglobal.fsg.dip.entity.MnrlLsaData_DAO;
import com.fisglobal.fsg.dip.entity.MnrlProperty_DAO;
import com.fisglobal.fsg.dip.entity.MnrlReactivationIdDetails_DAO;
import com.fisglobal.fsg.dip.entity.MnrlTokenCredDtls_DAO;
import com.fisglobal.fsg.dip.entity.MnrlTspData_DAO;
import com.fisglobal.fsg.dip.entity.MrnlDisconnectionReasonMapping_DAO;
import com.fisglobal.fsg.dip.entity.repo.FriIndicatorDetailsRepo;
import com.fisglobal.fsg.dip.entity.repo.MnrlActionDetailRepo;
import com.fisglobal.fsg.dip.entity.repo.MnrlDisconnectionReasonMappingRepo;
import com.fisglobal.fsg.dip.entity.repo.MnrlLsaDataRepo;
import com.fisglobal.fsg.dip.entity.repo.MnrlPropertyRepo;
import com.fisglobal.fsg.dip.entity.repo.MnrlReactivationIdDetailsRepo;
import com.fisglobal.fsg.dip.entity.repo.MnrlTokenCredDtlsRepo;
import com.fisglobal.fsg.dip.entity.repo.MnrlTspDataRepo;
import com.fisglobal.fsg.dip.request.entity.MnrlTokenRequestDetails;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

//@Order(1)
@Configuration
public class MnrlLoadData {

	private static final Logger LOGGER = LoggerFactory.getLogger(MnrlLoadData.class);

	@Inject
	MnrlPropertyRepo repo;

	@Inject
	MnrlLsaDataRepo lsaRepo;

	@Inject
	MnrlTspDataRepo tspRepo;

	@Inject
	MnrlActionDetailRepo actionDetailRepo;

	@Inject
	MnrlDisconnectionReasonMappingRepo reasonRepo;

	@Inject
	MnrlTokenCredDtlsRepo tokenRepo;

	@Inject
	MnrlReactivationIdDetailsRepo reactivationRepo;

	@Inject
	FriIndicatorDetailsRepo friIndicatorRepo;
	
	
	
	@Value("${mnrl.config.path}")
	private String mnrlConfigPath;
	
	@Value("${fri.config.path}")
	private String friConfigPath;
	
	

	UATHeaderProperty uatProp = new UATHeaderProperty();
	PreProdHeaderProperty preProdProp = new PreProdHeaderProperty();
	ProdHeaderProperty prodProp = new ProdHeaderProperty();
	HeaderProperty headerProp = new HeaderProperty();
	AppProperty appProp = new AppProperty();

	public static Map<String, String> lsaDataMap = new HashMap<String, String>();
	public static Map<String, String> tspDataMap = new HashMap<String, String>();
	public static Map<String, String> actionDetailDataMap = new HashMap<String, String>();
	public static Map<String, String> disconnectionDataMap = new HashMap<String, String>();
	public static Map<Integer, String> reactivationMap = new HashMap<Integer, String>();
	public static Map<String, String> friIndicatorMap = new HashMap<String, String>();
	public static Map<String, String> credDataMap = new HashMap<String, String>();
	public static PropertyDetails propdetails = null;
	public static MnrlTokenRequestDetails tokenReqDetails = null;
	public static MnrlTokenRequestDetails friTokenRequestDetails = null;

	public static Map<String, HeaderProperty> headerMap = new HashMap<String, HeaderProperty>();
	public static Gson gson = new GsonBuilder().disableHtmlEscaping().create();
//	public static UUID uuid = UUID.randomUUID();

	@Bean(name = "FRI_ATR_REQ")
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames(friConfigPath);
		messageSource.setUseCodeAsDefaultMessage(true);
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(0);
		return messageSource;
	}

	@Bean(name = "MNRL_ATR_REQ")
	public MessageSource mnrlMessageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames(mnrlConfigPath);
		messageSource.setUseCodeAsDefaultMessage(true);
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(0);
		return messageSource;
	}

	@PostConstruct
	public void loadConfig() {

		loadProperty();
		loadLsaData();
		loadTspData();
		loadActionDetailData();
		loadDisconnectionReasonData();
		loadTokenCredDetails();
		loadReactivationDetailsData();
		loadFriDetailsData();
	}

	public void loadProperty() {

		propdetails = new PropertyDetails();
		List<MnrlProperty_DAO> property = new ArrayList<MnrlProperty_DAO>();

		property = repo.findAll();

		for (MnrlProperty_DAO prop : property) {

			/*
			 * if (Constants.UAT.equals(prop.getEnvironment())) { //
			 * LOGGER.info(prop.toString()); uatProp = uatHeaderProperties(prop);
			 * 
			 * } else if (Constants.PREPROD.equals(prop.getEnvironment())) {
			 * LOGGER.debug("PREPROD PROPERTIES [{}]", prop.getEnvironment()); preProdProp =
			 * preProdHeaderProperties(prop);
			 * 
			 * } else if (Constants.PROD.equals(prop.getEnvironment())) { prodProp =
			 * prodHeaderProperties(prop);
			 * 
			 * } else
			 */
			if (Constants.APP.equals(prop.getEnvironment())) {

				appProp = setAppProperties(prop);

			} /*
				 * else { List<String> list =
				 * Stream.of(MnrlLoadData.propdetails.getAppProperty().getHeaderEnvironments().
				 * split(",")) .collect(Collectors.toList());
				 * 
				 * for(String s: list) { if(s.equals(prop.getEnvironment())) { headerProp
				 * =setHeaderProperties(prop); headerMap.put(s, headerProp); } } }
				 */

		}
		propdetails.setAppProperty(appProp);
		
		RestClient restClient =getRestClient();
		
		propdetails.setRestClient(restClient);

		// for (MnrlProperty_DAO prop : property) {
		List<String> list = Stream.of(MnrlLoadData.propdetails.getAppProperty().getHeaderEnvironments().split(","))
				.collect(Collectors.toList());

		for (String s : list) {
			List<MnrlProperty_DAO> p = new ArrayList<MnrlProperty_DAO>();
			p = repo.findDataById(s);
			// LOGGER.info("2");
			if (!p.isEmpty()) {
				HeaderProperty headerProp = new HeaderProperty();
				for (MnrlProperty_DAO prop : p) {
					if (Constants.HEADERACCEPTORENCODING.equals(prop.getPropertyName())) {
						headerProp.setHeaderAcceptEncoding(prop.getPropertyValue());
					}
					if (Constants.HEADERBRANCHNUMBER.equals(prop.getPropertyName())) {
						headerProp.setHeaderBranchNumber(prop.getPropertyValue());
					}
					if (Constants.HEADERCHANNEL.equals(prop.getPropertyName())) {
						headerProp.setHeaderChannel(prop.getPropertyValue());
					}
					if (Constants.HEADERCONTENTTYPE.equals(prop.getPropertyName())) {
						headerProp.setHeaderContentType(prop.getPropertyValue());
					}
					if (Constants.HEADERHEALTHCHECK.equals(prop.getPropertyName())) {
						headerProp.setHeaderHealthCheck(prop.getPropertyValue());
					}
					if (Constants.HEALTHTYPE.equals(prop.getPropertyName())) {
						headerProp.setHeaderHealthType(prop.getPropertyValue());
					}
					if (Constants.HEADEROVERRIDEFLAG.equals(prop.getPropertyName())) {
						headerProp.setHeaderOverrideFlag(prop.getPropertyValue());
					}
					if (Constants.HEADERRECOVERYFLAG.equals(prop.getPropertyName())) {
						headerProp.setHeaderRecoveryFlag(prop.getPropertyValue());
					}
					if (Constants.HEADERACCTSTMTTELLERNUMBER.equals(prop.getPropertyName())) {
						headerProp.setHeaderAcctStatementTellerNumber(prop.getPropertyValue());
					}
					if (Constants.HEADERTERMINALNUMBER.equals(prop.getPropertyName())) {
						headerProp.setHeaderTerminalNumber(prop.getPropertyValue());
					}
					if (Constants.HEADERXCLIENTCERTIFICATE.equals(prop.getPropertyName())) {
						headerProp.setHeaderXclientCertificate(prop.getPropertyValue());
					}
					if (Constants.HEADERXIBCLIENTID.equals(prop.getPropertyName())) {
						headerProp.setHeaderXIbClientId(prop.getPropertyValue());
					}
					if (Constants.HEADERXIBCLIENTSECRET.equals(prop.getPropertyName())) {
						headerProp.setHeaderXIbClientIdSecret(prop.getPropertyValue());
					}
					// LOGGER.debug("Key [{}] Value[{}]", s, headerProp);

				}
				// LOGGER.info("1");
				headerMap.put(s, headerProp);
				headerProp = null;
				// prop.clear();
			}
		}
		// }
		// propdetails.setHeaderProperty(headerProp);
		// propdetails.setPreProdProperty(preProdProp);
		// propdetails.setProdProperty(prodProp);
		// propdetails.setAppProperty(appProp);

		LOGGER.debug("Properties [{}]", propdetails.toString());
		LOGGER.debug("HeaderMap [{}]", headerMap);
	}

	public void loadLsaData() {

		List<MnrlLsaData_DAO> lsaData = new ArrayList<MnrlLsaData_DAO>();

		lsaData = lsaRepo.findAll();

		for (MnrlLsaData_DAO data : lsaData) {
			lsaDataMap.put(data.getLsaId(), data.getLsaName());
		}
		LOGGER.debug("LSA DATA [{}]", lsaDataMap);
	}

	public void loadTspData() {

		List<MnrlTspData_DAO> tspData = new ArrayList<MnrlTspData_DAO>();

		tspData = tspRepo.findAll();

		for (MnrlTspData_DAO data : tspData) {
			tspDataMap.put(data.getTspId(), data.getTspName());
		}
		LOGGER.debug("TSP DATA [{}]", tspDataMap);
	}

	public void loadActionDetailData() {

		List<MnrlActionDetail_DAO> ActionDetailsData = new ArrayList<MnrlActionDetail_DAO>();

		ActionDetailsData = actionDetailRepo.findAll();

		for (MnrlActionDetail_DAO data : ActionDetailsData) {
			actionDetailDataMap.put(data.getActionCode(), data.getActionReason());
		}
		LOGGER.debug("ACTION DETAIL DATA [{}]", actionDetailDataMap);
	}

	public void loadDisconnectionReasonData() {

		List<MrnlDisconnectionReasonMapping_DAO> disconnectionData = new ArrayList<MrnlDisconnectionReasonMapping_DAO>();

		disconnectionData = reasonRepo.findAll();

		for (MrnlDisconnectionReasonMapping_DAO data : disconnectionData) {
			disconnectionDataMap.put(data.getActionId(), data.getDisconnectionReason());
		}
		LOGGER.debug("DISCONNECTION REASON DATA [{}]", disconnectionDataMap);
	}

	public void loadReactivationDetailsData() {

		List<MnrlReactivationIdDetails_DAO> reactivationData = new ArrayList<MnrlReactivationIdDetails_DAO>();

		reactivationData = reactivationRepo.findAll();

		for (MnrlReactivationIdDetails_DAO data : reactivationData) {
			reactivationMap.put(data.getReactivationId(), data.getReactivationIdDescription());
		}
		LOGGER.debug("REACTIVATION ID DATA [{}]", reactivationMap);
	}

	public void loadFriDetailsData() {

		List<FriIndicatorDetails_DAO> friIndicatorData = new ArrayList<FriIndicatorDetails_DAO>();

		friIndicatorData = friIndicatorRepo.findAll();

		for (FriIndicatorDetails_DAO data : friIndicatorData) {
			friIndicatorMap.put(data.getFriIndicatorId(), data.getFriIndicatorIdDescription());
		}
		LOGGER.debug("FRI INDICATOR DATA [{}]", friIndicatorMap);
	}

	public void loadTokenCredDetails() {

		MnrlTokenCredDtls_DAO tokenDetails = new MnrlTokenCredDtls_DAO();
		tokenReqDetails = new MnrlTokenRequestDetails();
		tokenDetails = tokenRepo.findDataByIndicator("MNRL");
		if (StringUtils.isNotBlank(tokenDetails.getEmail())) {
			tokenReqDetails.setEmail(tokenDetails.getEmail());
		}
		if (StringUtils.isNotBlank(tokenDetails.getSecureTerm())) {
			tokenReqDetails.setSecureterm(tokenDetails.getSecureTerm());
		}
		if (StringUtils.isNotBlank(tokenDetails.getBankId())) {
			tokenReqDetails.setBankId(tokenDetails.getBankId());
		}

		LOGGER.debug("MNRL TOKEN REQUEST DETAILS [{}]", tokenReqDetails.toString());

		friTokenRequestDetails = new MnrlTokenRequestDetails();
		tokenDetails = tokenRepo.findDataByIndicator("FRI");

		if (StringUtils.isNotBlank(tokenDetails.getEmail())) {
			friTokenRequestDetails.setEmail(tokenDetails.getEmail());
		}
		if (StringUtils.isNotBlank(tokenDetails.getSecureTerm())) {
			friTokenRequestDetails.setSecureterm(tokenDetails.getSecureTerm());
		}
		if (StringUtils.isNotBlank(tokenDetails.getBankId())) {
			friTokenRequestDetails.setBankId(tokenDetails.getBankId());
		}

		LOGGER.debug("FRI TOKEN REQUEST DETAILS [{}]", friTokenRequestDetails.toString());

	}

	public AppProperty setAppProperties(MnrlProperty_DAO prop) {

		if (Constants.KEYSTOREALIAS.equals(prop.getPropertyName())) {
			appProp.setKeystoreAlias(prop.getPropertyValue());
		}
		if (Constants.KEYSTORESECURETERM.equals(prop.getPropertyName())) {
			appProp.setKeystoreSecureTerm(prop.getPropertyValue());
		}
		if (Constants.KEYSTORETYPE.equals(prop.getPropertyName())) {
			appProp.setKeystoreType(prop.getPropertyValue());
		}
		if (Constants.KEYSTOREFILEPATH.equals(prop.getPropertyName())) {
			appProp.setKeystoreFilePath(prop.getPropertyValue());
		}

		if (Constants.COUNTDATE.equals(prop.getPropertyName())) {
			appProp.setMnrlCountDate(prop.getPropertyValue());
		}

		if (Constants.COUNTDATEFLAG.equals(prop.getPropertyName())) {
			appProp.setMnrlCountDateFlag(prop.getPropertyValue());
		}

		if (Constants.JASYPTSECURETERM.equals(prop.getPropertyName())) {
			appProp.setMastersecureterm(prop.getPropertyValue());
		}

		if (Constants.MRNLXMLPATH.equals(prop.getPropertyName())) {
			appProp.setMnrlXMLPath(prop.getPropertyValue());
		}

		if (Constants.MNRLCSVPATH.equals(prop.getPropertyName())) {
			appProp.setMnrlCsvPath(prop.getPropertyValue());
		}

		if (Constants.MNRLAUTHAPIHEADERPOINT.equals(prop.getPropertyName())) {
			appProp.setMnrlAuthAPIHeader(prop.getPropertyValue());
		}

		if (Constants.MNRLAUTHURL.equals(prop.getPropertyName())) {
			appProp.setMnrlAuthURL(prop.getPropertyValue());
		}

		if (Constants.MNRLCOUNTURL.equals(prop.getPropertyName())) {
			appProp.setMnrlCountURL(prop.getPropertyValue());
		}

		if (Constants.MNRLCOUNTAPIHEADERPOINT.equals(prop.getPropertyName())) {
			appProp.setMnrlCountAPIHeader(prop.getPropertyValue());
		}

		if (Constants.MNRLDATAURL.equals(prop.getPropertyName())) {
			appProp.setMnrlDataURL(prop.getPropertyValue());
		}

		if (Constants.MNRLDATAAPIHEADERPOINT.equals(prop.getPropertyName())) {
			appProp.setMnrlDataAPIHeader(prop.getPropertyValue());
		}
		if (Constants.PROXYFLAG.equals(prop.getPropertyName())) {
			appProp.setProxyFlag(Boolean.valueOf(prop.getPropertyValue()));
		}

		if (Constants.PROXYHOST.equals(prop.getPropertyName())) {
			appProp.setProxyHost(prop.getPropertyValue());
		}

		if (Constants.PROXYPORT.equals(prop.getPropertyName())) {
			appProp.setProxyPort(Integer.valueOf(prop.getPropertyValue()));
		}

		if (Constants.POOL.equals(prop.getPropertyName())) {
			appProp.setPool(Integer.valueOf(prop.getPropertyValue()));
		}

		if (Constants.RESTTIMEOUT.equals(prop.getPropertyName())) {
			appProp.setRestTimeOut(Integer.valueOf(prop.getPropertyValue()));
		}

		if (Constants.RESTREADTIMEOUT.equals(prop.getPropertyName())) {
			appProp.setRestReadTimeOut(Integer.valueOf(prop.getPropertyValue()));
		}

		if (Constants.USERNAME.equals(prop.getPropertyName())) {
			appProp.setUserName(prop.getPropertyValue());
		}

		if (Constants.USERSECURETERM.equals(prop.getPropertyName())) {
			appProp.setUserSecureterm(prop.getPropertyValue());
		}

		if (Constants.SSLVERIFY.equals(prop.getPropertyName())) {
			appProp.setSslVerify(Boolean.valueOf(prop.getPropertyValue()));
		}

		if (Constants.COUNTPEROFFSET.equals(prop.getPropertyName())) {
			appProp.setCountPerOffset(Integer.valueOf(prop.getPropertyValue()));
		}
		if (Constants.MNRLREQCOUNT.equals(prop.getPropertyName())) {
			appProp.setReqCount(Integer.valueOf(prop.getPropertyValue()));
		}
		if (Constants.MNRLREQTIME.equals(prop.getPropertyName())) {
			appProp.setReqIntervalTime(Long.valueOf(prop.getPropertyValue()));
		}
		if (Constants.MNRLATRINCSVPATH.equals(prop.getPropertyName())) {
			appProp.setMnrlAtrInCsvPath(prop.getPropertyValue());
		}
		if (Constants.MNRLATROUTCSVPATH.equals(prop.getPropertyName())) {
			appProp.setMnrlAtrOutCsvPath(prop.getPropertyValue());
		}
		if (Constants.MNRLPUBKEYPATH.equals(prop.getPropertyName())) {
			appProp.setMnrlPubKeyPath(prop.getPropertyValue());
		}
		if (Constants.MNRLATRURL.equals(prop.getPropertyName())) {
			appProp.setMnrlAtrUrl(prop.getPropertyValue());
		}

		if (Constants.CBSACCENQHEADERPOINT.equals(prop.getPropertyName())) {
			appProp.setCbsAccEnqHeader(prop.getPropertyValue());
		}

		if (Constants.CBSACCENQURL.equals(prop.getPropertyName())) {
			appProp.setCbsAccEnqUrl(prop.getPropertyValue());
		}

		if (Constants.CBSMOBILEENQHEADERPOINT.equals(prop.getPropertyName())) {
			appProp.setCbsMobileEnqHeader(prop.getPropertyValue());
		}

		if (Constants.CBSMOBILEENQURL.equals(prop.getPropertyName())) {
			appProp.setCbsMobileEnqUrl(prop.getPropertyValue());
		}

		if (Constants.CBSCIFENQHEADERPOINT.equals(prop.getPropertyName())) {
			appProp.setCbsCifEnqHeader(prop.getPropertyValue());
		}

		if (Constants.CBSCIFENQURL.equals(prop.getPropertyName())) {
			appProp.setCbsCifEnqUrl(prop.getPropertyValue());
		}

		if (Constants.CBSCIFBLOCKHEADERPOINT.equals(prop.getPropertyName())) {
			appProp.setCbsCifBlockHeader(prop.getPropertyValue());
		}

		if (Constants.CBSCIFBLOCKURL.equals(prop.getPropertyName())) {
			appProp.setCbsCifBlockUrl(prop.getPropertyValue());
		}

		if (Constants.CBSMOBILEREMOVALHEADERPOINT.equals(prop.getPropertyName())) {
			appProp.setCbsMobileRemovalHeader(prop.getPropertyValue());
		}

		if (Constants.CBSMOBILEREMOVALURL.equals(prop.getPropertyName())) {
			appProp.setCbsMobileRemovalUrl(prop.getPropertyValue());
		}

		if (Constants.FRIPUBKEYPATH.equals(prop.getPropertyName())) {
			appProp.setFriPublicKeyPath(prop.getPropertyValue());
		}

		if (Constants.FRIAUTHAPIHEADERPOINT.equals(prop.getPropertyName())) {
			appProp.setFriauthHeader(prop.getPropertyValue());
		}

		if (Constants.FRIAUTHURL.equals(prop.getPropertyName())) {
			appProp.setFriauthURL(prop.getPropertyValue());
		}

		if (Constants.MNRLATRAPIHEADER.equals(prop.getPropertyName())) {
			appProp.setMnrlAtrApiHeader(prop.getPropertyValue());
		}

		if (Constants.FRICOUNTDATE.equals(prop.getPropertyName())) {
			appProp.setFriCountDate(prop.getPropertyValue());
		}

		if (Constants.FRICOUNTDATEFLAG.equals(prop.getPropertyName())) {
			appProp.setFriCountDateFlag(prop.getPropertyValue());
		}

		if (Constants.FRICOUNTURL.equals(prop.getPropertyName())) {
			appProp.setFriCountURL(prop.getPropertyValue());
		}

		if (Constants.FRICOUNTAPIHEADERPOINT.equals(prop.getPropertyName())) {
			appProp.setFriCountAPIHeader(prop.getPropertyValue());
		}

		if (Constants.FRIDATAURL.equals(prop.getPropertyName())) {
			appProp.setFriDataURL(prop.getPropertyValue());
		}

		if (Constants.FRIDATAAPIHEADERPOINT.equals(prop.getPropertyName())) {
			appProp.setFriDataAPIHeader(prop.getPropertyValue());
		}
		if (Constants.FRIKEYSTOREALIAS.equals(prop.getPropertyName())) {
			appProp.setFrikeystoreAlias(prop.getPropertyValue());
		}
		if (Constants.FRIKEYSTORESECURETERM.equals(prop.getPropertyName())) {
			appProp.setFrikeystoreSecureTerm(prop.getPropertyValue());
		}
		if (Constants.FRIKEYSTORETYPE.equals(prop.getPropertyName())) {
			appProp.setFriKeystoreType(prop.getPropertyValue());
		}
		if (Constants.FRIKEYSTOREFILEPATH.equals(prop.getPropertyName())) {
			appProp.setFrikeystoreFilePath(prop.getPropertyValue());
		}

		if (Constants.MNRLRETRYTIME.equals(prop.getPropertyName())) {
			appProp.setRetryIntervalTime(Long.valueOf(prop.getPropertyValue()));
		}

		if (Constants.REACTIVATECOUNTDATE.equals(prop.getPropertyName())) {
			appProp.setMnrlReactivatedCountDate(prop.getPropertyValue());
		}

		if (Constants.REACTIVATECOUNTDATEFLAG.equals(prop.getPropertyName())) {
			appProp.setMnrlReactivatedCountDateFlag(prop.getPropertyValue());
		}

		if (Constants.REACTIVATECOUNTURL.equals(prop.getPropertyName())) {
			appProp.setMnrlReactivatedCountURL(prop.getPropertyValue());
		}

		if (Constants.REACTIVATECOUNTAPIHEADERPOINT.equals(prop.getPropertyName())) {
			appProp.setMnrlReactivatedCountAPIHeader(prop.getPropertyValue());
		}

		if (Constants.REACTIVATEDATAURL.equals(prop.getPropertyName())) {
			appProp.setMnrlReactivatedDataURL(prop.getPropertyValue());
		}

		if (Constants.REACTIVATEDATAAPIHEADERPOINT.equals(prop.getPropertyName())) {
			appProp.setMnrlReactivatedDataAPIHeader(prop.getPropertyValue());
		}

		if (Constants.FRIREQCOUNT.equals(prop.getPropertyName())) {
			appProp.setFriReqCount(Integer.valueOf(prop.getPropertyValue()));
		}
		if (Constants.FRIREQTIME.equals(prop.getPropertyName())) {
			appProp.setFriReqIntervalTime(Long.valueOf(prop.getPropertyValue()));
		}

		if (Constants.FRIRETRYTIME.equals(prop.getPropertyName())) {
			appProp.setFriRetryIntervalTime(Long.valueOf(prop.getPropertyValue()));
		}

		if (Constants.REACTIVATEREQCOUNT.equals(prop.getPropertyName())) {
			appProp.setReactivatedReqCount(Integer.valueOf(prop.getPropertyValue()));
		}
		if (Constants.REACTIVATEREQTIME.equals(prop.getPropertyName())) {
			appProp.setReactivatedReqIntervalTime(Long.valueOf(prop.getPropertyValue()));
		}

		if (Constants.REACTIVATERETRYTIME.equals(prop.getPropertyName())) {
			appProp.setReactivatedRetryIntervalTime(Long.valueOf(prop.getPropertyValue()));
		}

		if (Constants.FRICOUNTPEROFFSET.equals(prop.getPropertyName())) {
			appProp.setFriCountPerOffset(Integer.valueOf(prop.getPropertyValue()));
		}

		if (Constants.REACTIVATECOUNTPEROFFSET.equals(prop.getPropertyName())) {
			appProp.setReactivatedCountPerOffset(Integer.valueOf(prop.getPropertyValue()));
		}

		if (Constants.FRIATRURL.equals(prop.getPropertyName())) {
			appProp.setFriAtrUrl(prop.getPropertyValue());
		}

		if (Constants.FRIATRAPIHEADER.equals(prop.getPropertyName())) {
			appProp.setFriAtrApiHeader(prop.getPropertyValue());
		}

		if (Constants.FRIMOBENQPAGINGSIZE.equals(prop.getPropertyName())) {
			appProp.setFriMobileEnquiryPagingSize(Integer.valueOf(prop.getPropertyValue()));
		}

		if (Constants.FRIMOBENQTHREADSIZE.equals(prop.getPropertyName())) {
			appProp.setFriMobileEnquiryThreadPoolSize(Integer.valueOf(prop.getPropertyValue()));
		}

		if (Constants.FRIACTIONPAGINGSIZE.equals(prop.getPropertyName())) {
			appProp.setFriActionPagingSize(Integer.valueOf(prop.getPropertyValue()));
		}

		if (Constants.FRIACTIONTHREADSIZE.equals(prop.getPropertyName())) {
			appProp.setFriActionThreadPoolSize(Integer.valueOf(prop.getPropertyValue()));
		}

		if (Constants.HEADERENVIRONMENTS.equals(prop.getPropertyName())) {
			appProp.setHeaderEnvironments(prop.getPropertyValue());
		}

		if (Constants.CBSDEBITFREEZEHEADERPOINT.equals(prop.getPropertyName())) {
			appProp.setDebitFreezeHeader(prop.getPropertyValue());
		}

		if (Constants.CBSDEBITFREEZEURL.equals(prop.getPropertyName())) {
			appProp.setDebitFreezeUrl(prop.getPropertyValue());
		}

		if (Constants.STOPREASON.equals(prop.getPropertyName())) {
			appProp.setStopReason(prop.getPropertyValue());
		}

		if (Constants.ACCOUNTCONDITION.equals(prop.getPropertyName())) {
			appProp.setAccountCondition(prop.getPropertyValue());
		}

		if (Constants.MNRLMOBENQPAGINGSIZE.equals(prop.getPropertyName())) {
			appProp.setMnrlMobileEnquiryPagingSize(Integer.valueOf(prop.getPropertyValue()));
		}

		if (Constants.MNRLMOBENQTHREADSIZE.equals(prop.getPropertyName())) {
			appProp.setMnrlMobileEnquiryThreadPoolSize(Integer.valueOf(prop.getPropertyValue()));
		}

		if (Constants.MNRLACTIONPAGINGSIZE.equals(prop.getPropertyName())) {
			appProp.setMnrlActionPagingSize(Integer.valueOf(prop.getPropertyValue()));
		}

		if (Constants.MNRLACTIONTHREADSIZE.equals(prop.getPropertyName())) {
			appProp.setMnrlActionThreadPoolSize(Integer.valueOf(prop.getPropertyValue()));
		}

		if (Constants.MNRLENQUIRYPAGINGSIZE.equals(prop.getPropertyName())) {
			appProp.setMnrlEnquiryPagingSize(Integer.valueOf(prop.getPropertyValue()));
		}

		if (Constants.MNRLENQUIRYTHREADSIZE.equals(prop.getPropertyName())) {
			appProp.setMnrlEnquiryPoolSize(Integer.valueOf(prop.getPropertyValue()));
		}

		if (Constants.COUNTRYCODE.equals(prop.getPropertyName())) {
			appProp.setCountryCode(prop.getPropertyValue());
		}

		if (Constants.MOBILEENQNODATAERROR.equals(prop.getPropertyName())) {
			appProp.setMobileEnqNoDataError(prop.getPropertyValue());
		}
		if (Constants.MNRLATRPAGINGSIZE.equals(prop.getPropertyName())) {
			appProp.setMnrlATRPagingSize(Integer.valueOf(prop.getPropertyValue()));
		}

		if (Constants.MNRLATRTHREADSIZE.equals(prop.getPropertyName())) {
			appProp.setMnrlATRPoolSize(Integer.valueOf(prop.getPropertyValue()));
		}
		
		if (Constants.FRIATRPAGINGSIZE.equals(prop.getPropertyName())) {
			appProp.setFriATRPagingSize(Integer.valueOf(prop.getPropertyValue()));
		}

		if (Constants.FRIATRTHREADSIZE.equals(prop.getPropertyName())) {
			appProp.setFriATRPoolSize(Integer.valueOf(prop.getPropertyValue()));
		}
		return appProp;

	}

	public UATHeaderProperty uatHeaderProperties(MnrlProperty_DAO prop) {

		if (Constants.HEADERACCEPTORENCODING.equals(prop.getPropertyName())) {
			uatProp.setHeaderAcceptEncoding(prop.getPropertyValue());
		}
		if (Constants.HEADERBRANCHNUMBER.equals(prop.getPropertyName())) {
			uatProp.setHeaderBranchNumber(prop.getPropertyValue());
		}
		if (Constants.HEADERCHANNEL.equals(prop.getPropertyName())) {
			uatProp.setHeaderChannel(prop.getPropertyValue());
		}
		if (Constants.HEADERCONTENTTYPE.equals(prop.getPropertyName())) {
			uatProp.setHeaderContentType(prop.getPropertyValue());
		}
		if (Constants.HEADERHEALTHCHECK.equals(prop.getPropertyName())) {
			uatProp.setHeaderHealthCheck(prop.getPropertyValue());
		}
		if (Constants.HEALTHTYPE.equals(prop.getPropertyName())) {
			uatProp.setHeaderHealthType(prop.getPropertyValue());
		}
		if (Constants.HEADEROVERRIDEFLAG.equals(prop.getPropertyName())) {
			uatProp.setHeaderOverrideFlag(prop.getPropertyValue());
		}
		if (Constants.HEADERRECOVERYFLAG.equals(prop.getPropertyName())) {
			uatProp.setHeaderRecoveryFlag(prop.getPropertyValue());
		}
		if (Constants.HEADERACCTSTMTTELLERNUMBER.equals(prop.getPropertyName())) {
			uatProp.setHeaderAcctStatementTellerNumber(prop.getPropertyValue());
		}
		if (Constants.HEADERTERMINALNUMBER.equals(prop.getPropertyName())) {
			uatProp.setHeaderTerminalNumber(prop.getPropertyValue());
		}
		if (Constants.HEADERXCLIENTCERTIFICATE.equals(prop.getPropertyName())) {
			uatProp.setHeaderXclientCertificate(prop.getPropertyValue());
		}
		if (Constants.HEADERXIBCLIENTID.equals(prop.getPropertyName())) {
			uatProp.setHeaderXIbClientId(prop.getPropertyValue());
		}
		if (Constants.HEADERXIBCLIENTSECRET.equals(prop.getPropertyName())) {
			uatProp.setHeaderXIbClientIdSecret(prop.getPropertyValue());
		}

		return uatProp;

	}

	public PreProdHeaderProperty preProdHeaderProperties(MnrlProperty_DAO prop) {

		if (Constants.HEADERACCEPTORENCODING.equals(prop.getPropertyName())) {
			preProdProp.setHeaderAcceptEncoding(prop.getPropertyValue());
		}
		if (Constants.HEADERBRANCHNUMBER.equals(prop.getPropertyName())) {
			preProdProp.setHeaderBranchNumber(prop.getPropertyValue());
		}
		if (Constants.HEADERCHANNEL.equals(prop.getPropertyName())) {
			preProdProp.setHeaderChannel(prop.getPropertyValue());
		}
		if (Constants.HEADERCONTENTTYPE.equals(prop.getPropertyName())) {
			preProdProp.setHeaderContentType(prop.getPropertyValue());
		}
		if (Constants.HEADERHEALTHCHECK.equals(prop.getPropertyName())) {
			preProdProp.setHeaderHealthCheck(prop.getPropertyValue());
		}
		if (Constants.HEALTHTYPE.equals(prop.getPropertyName())) {
			preProdProp.setHeaderHealthType(prop.getPropertyValue());
		}
		if (Constants.HEADEROVERRIDEFLAG.equals(prop.getPropertyName())) {
			preProdProp.setHeaderOverrideFlag(prop.getPropertyValue());
		}
		if (Constants.HEADERRECOVERYFLAG.equals(prop.getPropertyName())) {
			preProdProp.setHeaderRecoveryFlag(prop.getPropertyValue());
		}
		if (Constants.HEADERACCTSTMTTELLERNUMBER.equals(prop.getPropertyName())) {
			preProdProp.setHeaderAcctStatementTellerNumber(prop.getPropertyValue());
		}
		if (Constants.HEADERTERMINALNUMBER.equals(prop.getPropertyName())) {
			preProdProp.setHeaderTerminalNumber(prop.getPropertyValue());
		}
		if (Constants.HEADERXCLIENTCERTIFICATE.equals(prop.getPropertyName())) {
			preProdProp.setHeaderXclientCertificate(prop.getPropertyValue());
		}
		if (Constants.HEADERXIBCLIENTID.equals(prop.getPropertyName())) {
			preProdProp.setHeaderXIbClientId(prop.getPropertyValue());
		}
		if (Constants.HEADERXIBCLIENTSECRET.equals(prop.getPropertyName())) {
			preProdProp.setHeaderXIbClientIdSecret(prop.getPropertyValue());
		}

		return preProdProp;
	}

	public HeaderProperty setHeaderProperties(MnrlProperty_DAO prop) {

		if (Constants.HEADERACCEPTORENCODING.equals(prop.getPropertyName())) {
			headerProp.setHeaderAcceptEncoding(prop.getPropertyValue());
		}
		if (Constants.HEADERBRANCHNUMBER.equals(prop.getPropertyName())) {
			headerProp.setHeaderBranchNumber(prop.getPropertyValue());
		}
		if (Constants.HEADERCHANNEL.equals(prop.getPropertyName())) {
			headerProp.setHeaderChannel(prop.getPropertyValue());
		}
		if (Constants.HEADERCONTENTTYPE.equals(prop.getPropertyName())) {
			headerProp.setHeaderContentType(prop.getPropertyValue());
		}
		if (Constants.HEADERHEALTHCHECK.equals(prop.getPropertyName())) {
			headerProp.setHeaderHealthCheck(prop.getPropertyValue());
		}
		if (Constants.HEALTHTYPE.equals(prop.getPropertyName())) {
			headerProp.setHeaderHealthType(prop.getPropertyValue());
		}
		if (Constants.HEADEROVERRIDEFLAG.equals(prop.getPropertyName())) {
			headerProp.setHeaderOverrideFlag(prop.getPropertyValue());
		}
		if (Constants.HEADERRECOVERYFLAG.equals(prop.getPropertyName())) {
			headerProp.setHeaderRecoveryFlag(prop.getPropertyValue());
		}
		if (Constants.HEADERACCTSTMTTELLERNUMBER.equals(prop.getPropertyName())) {
			headerProp.setHeaderAcctStatementTellerNumber(prop.getPropertyValue());
		}
		if (Constants.HEADERTERMINALNUMBER.equals(prop.getPropertyName())) {
			headerProp.setHeaderTerminalNumber(prop.getPropertyValue());
		}
		if (Constants.HEADERXCLIENTCERTIFICATE.equals(prop.getPropertyName())) {
			headerProp.setHeaderXclientCertificate(prop.getPropertyValue());
		}
		if (Constants.HEADERXIBCLIENTID.equals(prop.getPropertyName())) {
			headerProp.setHeaderXIbClientId(prop.getPropertyValue());
		}
		if (Constants.HEADERXIBCLIENTSECRET.equals(prop.getPropertyName())) {
			headerProp.setHeaderXIbClientIdSecret(prop.getPropertyValue());
		}

		return headerProp;
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
	
	
}
