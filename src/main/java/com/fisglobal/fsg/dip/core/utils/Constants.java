package com.fisglobal.fsg.dip.core.utils;

public class Constants {

	public static final String INST_NETWORK_TASK = "I4CNetworkTask";
	public static final String INST_CALLBACK_TASK = "I4CCallbackTask";
	public static final String IB_FILEPROCESS_TASK = "I4CIBFileTask";
	public static final String I4C_BANKCODETABLE = "I4CBankCodeTable";
	public static final String I4C_PROPTABLE = "I4CPropTable";
	public static final String I4C_KEYMSTR = "I4CKeymaster";
	public static final String DEFAULT_SENSITIVE_DE = "2";
	public static final String INST_ROUTER_INSTID = ".instid";
	public static final String ROUTER = "router";
	public static final String INST_ROUTER = "inst.router";
	public static final String INST_ROUTER_NAME = ".name";
	public static final String Milisecond_MS = "ms";
	public static final String YES = "YES";
	public static final String NO = "NO";
	public static final String API = "API";
	public static final String SELF = "SELF";
	public static final String TRUE_STR = "true";
	public static final String Y_FLAG = "Y";
	public static final String N_FLAG = "N";
	public static final String B_FLAG = "B";
	public static final String R_FLAG = "R";
	public static final String P_FLAG = "P";
	public static final String F_FLAG = "F";
	
	public static final String INST_ROUTER_PFX = "router.";

	public static final String INST_ROUTER_GROUP = "inst.router.group";
	public static final String INST_ROUTER_HADLER_COREPOOL = ".handler.corepool";
	public static final String INST_ROUTER_HADLER_MAXPOOL = ".handler.maxpool";
	public static final String INST_ROUTER_GROUP_LIST = ".list";
	public static final String INST_ROUTER_HADLER_FORMATTER = ".handler.formatter";
	public static final String INST_ROUTER_HADLER_SEPERATOR = ".handler.seperator";
	public static final String INST_ROUTER_SENSITIVE_DE = ".sensitive.de";
	public static final String INST_ROUTER_GROUP_PFX = "router.group.";

	public static final String INST_CALLBACK_HOSTNAME = ".callback.hostName";
	public static final String INST_CALLBACK_PORT = ".callback.port";
	public static final String INST_CALLBACK_CONNTIMEOUT = ".callback.connTimeOut";
	public static final String INST_CALLBACK_SOTIMEOUT = ".callback.soTimeOut";
	public static final String INST_CALLBACK_MAXCONNPERHOST = ".callback.maxConnPerHost";
	public static final String INST_CALLBACK_MAXPERROUTE = ".callback.maxPerRoute";
	public static final String INST_CALLBACK_MAXCONNCOUNT = ".callback.maxConnCount";
	public static final String INST_CALLBACK_END_URL = ".callback.endurl";
	public static final String INST_CALLBACK_SSLVERSION = ".callback.sslversion";
	public static final String INST_CALLBACK_JKSPATH = ".callback.jkspath";
	public static final String INST_CALLBACK_JKSPWD = ".callback.jksfile.password";
	public static final String INST_CALLBACK_ROUTER_POOL = ".callback.pool";
	public static final String INST_CALLBACK_ROUTER_MSG_TIME = ".callback.msg.time";
	public static final String INST_CALLBACK_ROUTER_MIDDLEWAREHEADER_ADDINF = ".callback.middleware.header.adding";
	public static final String INST_ROUTER_POOL = ".pool";

	public static final String INST_MIDDLEWARE_HOSTNAME = ".middleware.hostName";
	public static final String INST_MIDDLEWARE_PORT = ".middleware.port";
	public static final String INST_MIDDLEWARE_CONNTIMEOUT = ".middleware.connTimeOut";
	public static final String INST_MIDDLEWARE_SOTIMEOUT = ".middleware.soTimeOut";
	public static final String INST_MIDDLEWARE_MAXCONNPERHOST = ".middleware.maxConnPerHost";
	public static final String INST_MIDDLEWARE_MAXPERROUTE = ".middleware.maxPerRoute";
	public static final String INST_MIDDLEWARE_MAXCONNCOUNT = ".middleware.maxConnCount";
	public static final String INST_MIDDLEWARE_END_URL = ".middleware.endurl";
	public static final String INST_MIDDLEWARE_SSLVERSION = ".middleware.sslversion";
	public static final String INST_MIDDLEWARE_JKSPATH = ".middleware.jkspath";
	public static final String INST_MIDDLEWARE_JKSPWD = ".middleware.jksfile.password";
	public static final String INST_MIDDLEWARE_ROUTER_POOL = ".middleware.pool";
	public static final String INST_MIDDLEWARE_ROUTER_MSG_TIME = ".middleware.msg.time";

	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String COMMA_SEPARATER = ",";

	public static final String INST_ROUTER_INBOUND_TOPIC = ".inbound.topic";
	public static final String INST_ROUTER_OUTBOUND_TOPIC = ".outbound.topic";

	public static final String INST_ROUTER_INBOUND_SYNC_TOPIC = ".inbound.sync.topic";
	public static final String INST_ROUTER_OUTBOUND_SYNC_TOPIC = ".outbound.sync.topic";

	public static final String INST_ROUTER_AQRESPONSE = ".resIdentifier";
	public static final String INST_ROUTER_AQREQUEST = ".reqIdentifier";

	public static final String SUCCESS = "Success";
	public static final String SUCCESS_CODE = "00";
	public static final String FAILURE_CODE = "01";
	public static final String FAILURE = "Failure";
	public static final String I4C_FAILURE = "32";
	public static final String HOLD_REASON = "37-Cyber Alert";
	//public static final String HOLD_REASON = "01-Collateral";
	public static final String INVALID_RRN = "02";

	public static final String SYNC_CALL = "SYNC-CALL";
	public static final String ASYNC_CALL = "ASYNC-CALL";

	public static final String RECORD_TYPE_API = "API";
	public static final String RECORD_TYPE_FILE = "FILE";
	public static final String INVALID_RRN_NUMBER="Invalid RRN";
	public static final String IB ="IND";
	
	public static final String MONEY_TRANSFER_TO="Money Transfer To";

	public static final String PUT_ON_HOLD="Transaction Put on Hold";
	

	public static final String ATM_WITHDRAWAL="Withdrawal through ATM";
	
	public static final String POS_WITHDRAWAL="Withdrawal through POS";
	
	public static final String CHEQUE_WITHDRAWAL="Withdrawal through Cheque";
	
	public static final String AEPS="AEPS";
	
	
	public static final String MONEY_TRANSFER_TO_ID="3";

	public static final String PUT_ON_HOLD_ID="1";
	

	public static final String ATM_WITHDRAWAL_ID="5";
	
	public static final String POS_WITHDRAWAL_ID="11";
	
	public static final String CHEQUE_WITHDRAWAL_ID="14";
	
	public static final String AEPS__ID="6";
	
	public static final String Channel_ECOM="ECOM";
	public static final String Channel_POS="POS";
	public static final String Channel_UPI="UPI";
	public static final String Channel_NEFT="NEFT";
	public static final String Channel_RTGS="RTGS";
	public static final String Channel_ATM="ATM";
	public static final String Channel_CHEQUE="CHEQUE";
	public static final String Channel_AEPS="AEPS";
	public static final String Channel_IMPS="IMPS";
	public static final String Channel_INTRA="INTRA";
	
	public static final String yyyy_MM_dd="yyyy-MM-dd";
	public static final String yyyyMMdd="yyyyMMdd";
	public static final String ddMMyyyy="ddMMyyyy";
	public static final String HHmmss="HHmmss";
	public static final String HH_mm_ss="HH:mm:ss";
	public static final String dd_MM_yyyy = "dd-MM-yyyy";
	public static final String INVALID_DISPUTED_AMOUNT = "14";
	public static final String NO_RECORD_FOUND = "Record not found";
	public static final String DEFAULT_IFSC_CODE = "IDIB000A046";
	public static final String DEFAULT_BANK = "Indian Bank";
	
	
	public static  final String ALGORITHM = "AES/CBC/PKCS5PADDING";
	public static  final String ENC_TYPE = "AES";
	public static  final String sha256 ="PBKDF2WithHmacSHA256";
	public static  final String SINGLE_ZERO="0";
	public static final String yyyyMMddHHmmss="yyyyMMddHHmmss";
	public static final String OTHERBANK="Other Bank";
	public static final String EMAIL_TYPE_ERROR="ERROR";
	public static final String EMAIL_TYPE_BRANCH="BRANCH";
	
	public static final String YEAR = "YEAR";
	public static final String MONTH = "MONTH";
	public static final String HALF = "HALF";
	public static final String QUATER = "QUATER";
	
	public static final String AVL_BLN_LESS_THAN_DISPUTED_AMOUNT="Available Balance is less than Disputed Amount";
	public static final String NEGATIVE_AVAILABLE_BALANCE="Negative Available Balance";
	public static final String AGGREGATOR_HOLD_REMARKS="Nodal/Aggregator account";
	
//Error Code
	/*
	 * public static final String COMMON_ERROR_CODE ="99"; public static final
	 * String COMMON_ERROR_MSG
	 * ="Sorry We are not able to process your request. Kindly try after some time."
	 * ; public static final String SUCCESS_CODE ="00"; public static final String
	 * SUCCESS_MSG ="Success"; public static final String
	 * RECORD_NOT_FOUND_ERROR_CODE ="01"; public static final String
	 * RECORD_NOT_FOUND_ERROR_MSG ="Record not found"; public static final String
	 * CALLBACKSTATUS_PENDING="31"; public static final String
	 * CALLBACKSTATUS_SUCCESS="00"; public static final String
	 * CALLBACKSTATUS_FAILURE="32"; public static final String
	 * Pending_MSG="Pending"; public static final String Failure_MSG="Failure";
	 */
	
	//HEADERS
	
	public static final String HEADERACCEPTORENCODING="header.accept.encoding";
	public static final String HEADERBRANCHNUMBER="header.branch.number";
	public static final String HEADERCHANNEL="header.channel";
	public static final String HEADERCONTENTTYPE="header.content.type";
	public static final String HEADERHEALTHCHECK="header.healthCheck";
	public static final String HEALTHTYPE="header.healthType";
	public static final String HEADEROVERRIDEFLAG="header.override.flag";
	public static final String HEADERRECOVERYFLAG="header.recovery.flag";
	public static final String HEADERACCTSTMTTELLERNUMBER="header.teller.number";
	public static final String HEADERTERMINALNUMBER="header.terminal.number";
	public static final String HEADERXCLIENTCERTIFICATE="header.x.client.certificate";
	public static final String HEADERXIBCLIENTID="header.x.ib.client.id";
	public static final String HEADERXIBCLIENTSECRET="header.x.ib.client.secret";
	
	//private keystore details
	
	public static final String KEYSTOREALIAS="keystore.alias";
	public static final String KEYSTORESECURETERM="keystore.password";
	public static final String KEYSTORETYPE="keystore.type";
	public static final String KEYSTOREFILEPATH="keystore.file.path";
	
	//environment
	
	public static final String UAT="UAT";
	public static final String PREPROD="PREPROD";
	public static final String PROD="PROD";
	public static final String APP="APP";
	
	public static final String COUNTDATE="mnrl.count.api.date";
	public static final String COUNTDATEFLAG="mnrl.count.api.manualdate.flag";
	public static final String JASYPTSECURETERM="jasypt.encryptor.secureterm";
	public static final String MRNLXMLPATH="mnrl.xml.path";
	public static final String MNRLCSVPATH="mnrl.csv.path";
	public static final String MNRLAUTHAPIHEADERPOINT="mnrl.auth.api.header.point";
	public static final String MNRLAUTHURL="mnrl.auth.api.url";
	public static final String MNRLCOUNTURL="mnrl.count.api.url";
	public static final String MNRLCOUNTAPIHEADERPOINT="mnrl.count.api.header.point";
	public static final String MNRLDATAURL="mnrl.data.api.url";
	public static final String MNRLDATAAPIHEADERPOINT="mnrl.data.api.header.point";
	
	public static final String PROXYFLAG="mnrl.proxy.flag";
	public static final String PROXYHOST="mnrl.proxy.host";
	public static final String PROXYPORT="mnrl.proxy.port";
	public static final String POOL="mnrl.rest.client.pool";
	public static final String RESTTIMEOUT="mnrl.rest.client.timeout";
	public static final String RESTREADTIMEOUT="mnrl.rest.client.readtimeout";
	public static final String USERNAME="mnrl.user.name";
	public static final String USERSECURETERM="mnrl.user.secureterm";
	public static final String SSLVERIFY="mnrl.ssl.verify";
	public static final String COUNTPEROFFSET="mnrl.count.per.offset";
	public static final String MNRLREQCOUNT="mnrl.data.interval.req.count";
	public static final String MNRLREQTIME="mnrl.data.interval.req.time";
	public static final String MNRLATRINCSVPATH="mnrl.atr.in.csv.path";
	public static final String MNRLATROUTCSVPATH="mnrl.atr.out.csv.path";
	public static final String MNRLPUBKEYPATH="mnrl.pub.key.path";
	public static final String MNRLATRURL="mnrl.atr.api.url";
	public static final String CBSACCENQHEADERPOINT="cbs.acc.enq.header.point";
	public static final String CBSACCENQURL="cbs.acc.enq.api.url";
	public static final String CBSMOBILEENQHEADERPOINT="cbs.mob.enq.header.point";
	public static final String CBSMOBILEENQURL="cbs.mob.enq.api.url";
	public static final String CBSCIFENQHEADERPOINT="cbs.cif.enq.header.point";
	public static final String CBSCIFENQURL="cbs.cif.enq.api.url";
	public static final String CBSCIFBLOCKHEADERPOINT="cbs.cif.block.header.point";
	public static final String CBSCIFBLOCKURL="cbs.cif.block.api.url";
	public static final String CBSMOBILEREMOVALHEADERPOINT="cbs.mob.removal.header.point";
	public static final String CBSMOBILEREMOVALURL="cbs.mob.removal.api.url";
	public static final String FRIPUBKEYPATH="fri.public.key.path";
	//public static final String FRIAUTHHEADER="fri.auth.api.header";
	public static final String FRIAUTHURL="fri.auth.api.url";
	public static final String MNRLATRAPIHEADER="mnrl.atr.api.header.point";
	public static final String FRICOUNTDATE="fri.count.api.date";
	public static final String FRICOUNTDATEFLAG="fri.count.api.manualdate.flag";
	
	public static final String FRIAUTHAPIHEADERPOINT="fri.auth.api.header.point";
	public static final String FRICOUNTURL="fri.count.api.url";
	public static final String FRICOUNTAPIHEADERPOINT="fri.count.api.header.point";
	public static final String FRIDATAURL="fri.data.api.url";
	public static final String FRIDATAAPIHEADERPOINT="fri.data.api.header.point";

	public static final String FRIKEYSTOREALIAS="fri.keystore.alias";
	public static final String FRIKEYSTORESECURETERM="fri.keystore.password";
	public static final String FRIKEYSTORETYPE="fri.keystore.type";
	public static final String FRIKEYSTOREFILEPATH="fri.keystore.file.path";
	public static final String MNRLRETRYTIME="mnrl.data.interval.retry.time";
	
	public static final String REACTIVATECOUNTDATE="mnrl.reactivate.count.api.date";
	public static final String REACTIVATECOUNTDATEFLAG="mnrl.reactivate.count.api.manualdate.flag";
	public static final String REACTIVATECOUNTURL="mnrl.reactivate.count.api.url";
	public static final String REACTIVATECOUNTAPIHEADERPOINT="mnrl.reactivate.count.api.header.point";
	public static final String REACTIVATEDATAURL="mnrl.reactivate.data.api.url";
	public static final String REACTIVATEDATAAPIHEADERPOINT="mnrl.reactivate.data.api.header.point";

	public static final String FRIREQCOUNT="fri.data.interval.req.count";
	public static final String FRIREQTIME="fri.data.interval.req.time";
	public static final String FRIRETRYTIME="fri.data.interval.retry.time";
	
	public static final String REACTIVATEREQCOUNT="mnrl.reactivate.data.interval.req.count";
	public static final String REACTIVATEREQTIME="mnrl.reactivate.data.interval.req.time";
	public static final String REACTIVATERETRYTIME="mnrl.reactivate.data.interval.retry.time";
	
	public static final String FRICOUNTPEROFFSET="fri.count.per.offset";
	public static final String REACTIVATECOUNTPEROFFSET="mnrl.reactivate.count.per.offset";
	public static final String FRIATRURL="fri.atr.api.url";
	public static final String FRIATRAPIHEADER="fri.atr.api.header.point";
	
	public static final String FRIMOBENQPAGINGSIZE="fri.mob.enq.page.size";
	public static final String FRIMOBENQTHREADSIZE="fri.mob.enq.thread.size";
	public static final String FRIACTIONPAGINGSIZE="fri.action.page.size";
	public static final String FRIACTIONTHREADSIZE="fri.action.thread.size";
	
	
	
	public static final String HEADERENVIRONMENTS="header.environments";
	
	public static final String CSV=".csv";
	
	public static final String CBSDEBITFREEZEHEADERPOINT="cbs.debit.freeze.header.point";
	public static final String CBSDEBITFREEZEURL="cbs.debit.freeze.api.url";
	public static final String STOPREASON="cbs.debit.freeze.stop.reason";
	public static final String ACCOUNTCONDITION="cbs.debit.freeze.account.condition";
	
	public static final String MNRLMOBENQPAGINGSIZE="mnrl.mob.enq.page.size";
	public static final String MNRLMOBENQTHREADSIZE="mnrl.mob.enq.thread.size";
	public static final String MNRLACTIONPAGINGSIZE="mnrl.action.page.size";
	public static final String MNRLACTIONTHREADSIZE="mnrl.action.thread.size";
	public static final String MNRLENQUIRYPAGINGSIZE="mnrl.acccifenq.page.size";
	public static final String MNRLENQUIRYTHREADSIZE="mnrl.acccifenq.thread.size";
	public static final String COUNTRYCODE="country.code";
	
	public static final String MOBILEENQNODATAERROR="mob.enq.no.data.error";
	public static final String MNRLATRPAGINGSIZE="mnrl.atr.page.size";
	public static final String MNRLATRTHREADSIZE="mnrl.atr.thread.size";
	
	public static final String FRIATRPAGINGSIZE="fri.atr.page.size";
	public static final String FRIATRTHREADSIZE="fri.atr.thread.size";
	
	public static final String INPROGRESS="INPROGRESS";
	public static final String COMPLETED="COMPLETED";
	
	

}
