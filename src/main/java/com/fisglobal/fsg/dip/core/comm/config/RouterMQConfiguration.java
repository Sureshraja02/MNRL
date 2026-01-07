/*
 * package com.fisglobal.fsg.dip.core.comm.config;
 * 
 * import java.io.IOException; import java.util.ArrayList; import
 * java.util.Arrays; import java.util.HashMap; import java.util.List; import
 * java.util.Map; import java.util.Map.Entry; import
 * java.util.concurrent.Executor; import java.util.stream.Collectors; import
 * java.util.stream.Stream;
 * 
 * import javax.inject.Inject;
 * 
 * import org.apache.commons.lang3.StringUtils; import org.slf4j.Logger; import
 * org.slf4j.LoggerFactory; import
 * org.springframework.beans.factory.annotation.Value; import
 * org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.context.annotation.PropertySource; import
 * org.springframework.core.Ordered; import
 * org.springframework.core.annotation.Order; import
 * org.springframework.core.env.Environment; import
 * org.springframework.scheduling.TaskScheduler; import
 * org.springframework.scheduling.annotation.EnableAsync; import
 * org.springframework.scheduling.annotation.EnableScheduling; import
 * org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor; import
 * org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler; import
 * org.springframework.web.client.ResourceAccessException;
 * 
 * import com.fisglobal.fsg.dip.core.comm.beans.RouterGroupMap; import
 * com.fisglobal.fsg.dip.core.comm.beans.RouterGroup_VO; import
 * com.fisglobal.fsg.dip.core.comm.beans.RouterList; import
 * com.fisglobal.fsg.dip.core.comm.beans.Router_VO; import
 * com.fisglobal.fsg.dip.core.utils.Constants; import
 * com.fisglobal.fsg.dip.entity.MnrlTokenCredDtls_DAO; import
 * com.fisglobal.fsg.dip.entity.impl.MnrlTokenCredDtlsImpl;
 * 
 * @Configuration
 * 
 * @EnableScheduling
 * 
 * @EnableAsync //@PropertySource("classpath:routerconfig.properties")
 * //@PropertySource("${router.propertypath}routerconfig.properties")
 * 
 * @Order(Ordered.LOWEST_PRECEDENCE) public class RouterMQConfiguration {
 * private final static Logger log =
 * LoggerFactory.getLogger(RouterMQConfiguration.class);
 * 
 * @Inject Environment env;
 * 
 * @Inject MnrlTokenCredDtlsImpl mnrlTokenCredDtlsImpl;
 * 
 * static Properties prop = new Properties(); static { String value =
 * System.getProperty("propertyName"); log.info("FIle Name : {}", value);
 * FileReader reader; try { if (value != null) { reader = new FileReader(value);
 * prop.load(reader); } } catch (Exception e) { e.printStackTrace(); } }
 * 
 * 
 * @Value("${imps.time.zone:GMT}") private String timeZone;
 * 
 * @Bean public RouterList startRouter() throws IOException { String routerNames
 * = env.getProperty("inst.router"); if (StringUtils.isNotBlank(routerNames) &&
 * !routerNames.contains(",")) { log.info("Propery Test : {}",
 * env.getProperty("router." + routerNames + ".callback.hostName")); }
 * RouterList routerList = loadRouter(); loadRouterGroup(routerList); return
 * routerList; }
 * 
 * public RouterList loadRouter() { String[] routerList =
 * getEnvValue(Constants.INST_ROUTER, String[].class); Map<String, Router_VO>
 * routerMap = Arrays.stream(routerList).map(m -> { Router_VO router = new
 * Router_VO(); router.setTimeZone(timeZone);
 * router.setInstId(getEnvValue(Constants.INST_ROUTER_PFX + m +
 * Constants.INST_ROUTER_INSTID)); router.setCallbackConnTimeOut(
 * getEnvValue(Constants.INST_ROUTER_PFX + m +
 * Constants.INST_CALLBACK_CONNTIMEOUT, int.class));
 * router.setCallbackhost(getEnvValue(Constants.INST_ROUTER_PFX + m +
 * Constants.INST_CALLBACK_HOSTNAME)); router.setCallbackMaxConnCount(
 * getEnvValue(Constants.INST_ROUTER_PFX + m +
 * Constants.INST_CALLBACK_MAXCONNCOUNT, int.class));
 * router.setCallbackMaxConnPerHost( getEnvValue(Constants.INST_ROUTER_PFX + m +
 * Constants.INST_CALLBACK_MAXCONNPERHOST, int.class));
 * router.setCallbackMaxPerRoute( getEnvValue(Constants.INST_ROUTER_PFX + m +
 * Constants.INST_CALLBACK_MAXPERROUTE, int.class)); router.setCallbackPort(
 * getEnvValue(Constants.INST_ROUTER_PFX + m + Constants.INST_CALLBACK_PORT,
 * int.class)); router.setCallbackSoTimeOut(
 * getEnvValue(Constants.INST_ROUTER_PFX + m +
 * Constants.INST_CALLBACK_SOTIMEOUT, int.class));
 * 
 * router.setEndUrl(getEnvValue(Constants.INST_ROUTER_PFX + m +
 * Constants.INST_CALLBACK_END_URL));
 * 
 * router.setCallbackjkspath(getEnvValue(Constants.INST_ROUTER_PFX + m +
 * Constants.INST_CALLBACK_JKSPATH)); router.setCallbacksslversion(
 * getEnvValue(Constants.INST_ROUTER_PFX + m +
 * Constants.INST_CALLBACK_SSLVERSION));
 * router.setCallbackjksSecureTerm(getEnvValue(Constants.INST_ROUTER_PFX + m +
 * Constants.INST_CALLBACK_JKSPWD)); router.setMsgTimeOut(
 * getEnvValue(Constants.INST_ROUTER_PFX + m +
 * Constants.INST_CALLBACK_ROUTER_MSG_TIME, long.class));
 * router.setI4cCallbackURLMidlewareHeadAdding(
 * getEnvValue(Constants.INST_ROUTER_PFX + m +
 * Constants.INST_CALLBACK_ROUTER_MIDDLEWAREHEADER_ADDINF, boolean.class));
 * 
 * 
 * router.setInBoundTopic( getEnvValue(Constants.INST_ROUTER_PFX + m +
 * Constants.INST_ROUTER_INBOUND_TOPIC)); router.setOutBoundTopic(
 * getEnvValue(Constants.INST_ROUTER_PFX + m +
 * Constants.INST_ROUTER_OUTBOUND_TOPIC));
 * 
 * router.setSyncInBoundTopic( getEnvValue(Constants.INST_ROUTER_PFX + m +
 * Constants.INST_ROUTER_INBOUND_SYNC_TOPIC)); router.setSyncOutBoundTopic(
 * getEnvValue(Constants.INST_ROUTER_PFX + m +
 * Constants.INST_ROUTER_OUTBOUND_SYNC_TOPIC)); router.setReqAQIdentifier(
 * getEnvValue(Constants.INST_ROUTER_PFX + m +
 * Constants.INST_ROUTER_AQREQUEST)); router.setResAQIdentifier(
 * getEnvValue(Constants.INST_ROUTER_PFX + m +
 * Constants.INST_ROUTER_AQRESPONSE));
 * 
 * 
 * 
 * //Middleware router.setMiddlewareConnTimeOut(
 * getEnvValue(Constants.INST_ROUTER_PFX + m +
 * Constants.INST_MIDDLEWARE_CONNTIMEOUT, int.class));
 * router.setMiddlewarehost(getEnvValue(Constants.INST_ROUTER_PFX + m +
 * Constants.INST_MIDDLEWARE_HOSTNAME));
 * router.setMiddlewarePort(getEnvValue(Constants.INST_ROUTER_PFX + m +
 * Constants.INST_MIDDLEWARE_PORT, int.class));
 * router.setMiddlewareMaxConnCount( getEnvValue(Constants.INST_ROUTER_PFX + m +
 * Constants.INST_MIDDLEWARE_MAXCONNCOUNT, int.class));
 * router.setMiddlewareMaxConnPerHost( getEnvValue(Constants.INST_ROUTER_PFX + m
 * + Constants.INST_MIDDLEWARE_MAXCONNPERHOST, int.class));
 * router.setMiddlewareMaxPerRoute( getEnvValue(Constants.INST_ROUTER_PFX + m +
 * Constants.INST_MIDDLEWARE_MAXPERROUTE, int.class));
 * 
 * router.setMiddlewareSoTimeOut( getEnvValue(Constants.INST_ROUTER_PFX + m +
 * Constants.INST_MIDDLEWARE_SOTIMEOUT, int.class));
 * 
 * router.setMiddlewareEndUrl(getEnvValue(Constants.INST_ROUTER_PFX + m +
 * Constants.INST_MIDDLEWARE_END_URL));
 * 
 * router.setMiddlewarejkspath(getEnvValue(Constants.INST_ROUTER_PFX + m +
 * Constants.INST_MIDDLEWARE_JKSPATH)); router.setMiddlewaresslversion(
 * getEnvValue(Constants.INST_ROUTER_PFX + m +
 * Constants.INST_MIDDLEWARE_SSLVERSION));
 * router.setMiddlewarejksSecureTerm(getEnvValue(Constants.INST_ROUTER_PFX + m +
 * Constants.INST_MIDDLEWARE_JKSPWD)); router.setMiddlewareMsgTimeOut(
 * getEnvValue(Constants.INST_ROUTER_PFX + m +
 * Constants.INST_MIDDLEWARE_ROUTER_MSG_TIME, long.class));
 * 
 * ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
 * pool.setCorePoolSize(getEnvValue(Constants.INST_ROUTER_PFX + m +
 * Constants.INST_ROUTER_POOL, int.class));
 * pool.setMaxPoolSize(getEnvValue(Constants.INST_ROUTER_PFX + m +
 * Constants.INST_ROUTER_POOL, int.class)); pool.setThreadNamePrefix(m +
 * "-INST-NCCRP-I4C-Process-"); pool.setWaitForTasksToCompleteOnShutdown(true);
 * pool.initialize(); router.setPool(pool);
 * 
 * ThreadPoolTaskExecutor callbackpool = new ThreadPoolTaskExecutor();
 * callbackpool.setCorePoolSize(getEnvValue(Constants.INST_ROUTER_PFX + m +
 * Constants.INST_ROUTER_POOL, int.class));
 * callbackpool.setMaxPoolSize(getEnvValue(Constants.INST_ROUTER_PFX + m +
 * Constants.INST_ROUTER_POOL, int.class)); callbackpool.setThreadNamePrefix(m +
 * "-INST-NCCRP-I4CCallback-Process-");
 * callbackpool.setWaitForTasksToCompleteOnShutdown(true);
 * callbackpool.initialize(); router.setCallbackpool(callbackpool);
 * 
 * ThreadPoolTaskExecutor fileprocesspool = new ThreadPoolTaskExecutor();
 * fileprocesspool.setCorePoolSize(getEnvValue(Constants.INST_ROUTER_PFX + m +
 * Constants.INST_ROUTER_POOL, int.class));
 * fileprocesspool.setMaxPoolSize(getEnvValue(Constants.INST_ROUTER_PFX + m +
 * Constants.INST_ROUTER_POOL, int.class));
 * fileprocesspool.setThreadNamePrefix(m + "-INST-NCCRP-I4C-FileProcess-");
 * fileprocesspool.setWaitForTasksToCompleteOnShutdown(true);
 * fileprocesspool.initialize(); router.setFileProcesspool(fileprocesspool);
 * 
 * return router; }).collect(Collectors.toMap(Router_VO::getName, v -> v));
 * 
 * RouterList list = new RouterList(); list.setRouterMap(routerMap); return
 * list; }
 * 
 * public RouterGroupMap loadRouterGroup(RouterList routerList) throws
 * IOException { String[] routerGroup = getEnvValue(Constants.INST_ROUTER_GROUP,
 * String[].class); Map<String, RouterGroup_VO> routMap = new HashMap<>(); for
 * (String m : routerGroup) { RouterGroup_VO group = new RouterGroup_VO();
 * group.setName(m); String[] tmz = getEnvValue(Constants.INST_ROUTER_GROUP_PFX
 * + m + Constants.INST_ROUTER_GROUP_LIST, String[].class); List<Router_VO>
 * routList = Arrays.stream(tmz).map(f -> { return routerList.getRouter(f);
 * }).collect(Collectors.toList());
 * 
 * ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
 * pool.setCorePoolSize(getEnvValue( Constants.INST_ROUTER_GROUP_PFX + m +
 * Constants.INST_ROUTER_HADLER_COREPOOL, Integer.class));
 * pool.setMaxPoolSize(getEnvValue(Constants.INST_ROUTER_GROUP_PFX + m +
 * Constants.INST_ROUTER_HADLER_MAXPOOL, Integer.class));
 * pool.setThreadNamePrefix(m + "-handler-");
 * pool.setWaitForTasksToCompleteOnShutdown(true); pool.initialize();
 * 
 * group.setPool(pool);
 * 
 * group.setRouterList(routList);
 * 
 * routMap.put(group.getName(), group); } RouterGroupMap.setRoutGrpMap(routMap);
 * return new RouterGroupMap(); }
 * 
 * @Bean(name = Constants.INST_NETWORK_TASK) public Executor
 * getTaskExecutor(RouterList routerList) { ThreadPoolTaskExecutor
 * poolTaskExecutor = null; for (Entry<String, Router_VO> set :
 * routerList.getRouterMap().entrySet()) { Router_VO v = set.getValue();
 * poolTaskExecutor = v.getPool(); } return poolTaskExecutor; }
 * 
 * @Bean(name = Constants.INST_CALLBACK_TASK) public Executor
 * getCalBackExecutor(RouterList routerList) { ThreadPoolTaskExecutor
 * poolTaskExecutor = null; for (Entry<String, Router_VO> set :
 * routerList.getRouterMap().entrySet()) { Router_VO v = set.getValue();
 * poolTaskExecutor = v.getCallbackpool(); } return poolTaskExecutor; }
 * 
 * @Bean(name = Constants.IB_FILEPROCESS_TASK) public Executor
 * getFileProcessExecutor(RouterList routerList) { ThreadPoolTaskExecutor
 * poolTaskExecutor = null; for (Entry<String, Router_VO> set :
 * routerList.getRouterMap().entrySet()) { Router_VO v = set.getValue();
 * poolTaskExecutor = v.getFileProcesspool(); } return poolTaskExecutor; }
 * 
 * 
 * public TaskScheduler scheduledTask(RouterList routerList) {
 * ThreadPoolTaskScheduler poolTaskScheduler = new ThreadPoolTaskScheduler();
 * poolTaskScheduler.setPoolSize(routerList.getRouterMap().size() * 2);
 * poolTaskScheduler.setWaitForTasksToCompleteOnShutdown(true);
 * poolTaskScheduler.setThreadNamePrefix("isoNetwork-");
 * poolTaskScheduler.initialize(); TaskScheduler scheduler = poolTaskScheduler;
 * return scheduler; }
 * 
 * 
 * @Bean(name = Constants.I4C_BANKCODETABLE) public void loadBankCodeTable() {
 * 
 * 
 * 
 * //Long lng =new Long(1); //MnrlTokenCredDtls_DAO tt =
 * mnrlTokenCredDtlsImpl.getAuthDetails(lng);
 * //System.out.println("DIP - User Email : "+tt.getEmail());
 * //System.out.println("DIP - User Secure Term : "+tt.getSecureTerm());
 * //String banCode = getBankCode("City Union Bank");
 * //log.info("Bank Code From IFSC Partial 4 : "+
 * toGetBankCodeUse4N6Digits("UTIB0000131", "","","BANKNAME")); //String
 * beneRemiIfsc, String jobid, String ackNo, String bankORCode
 * //log.info("4 - Digit match: ",cCSSBankCodesImpl.getBankCodeFromUsingSubstr(
 * "YESB",4));
 * //log.info("6 - Digit match: ",cCSSBankCodesImpl.getBankCodeFromUsingSubstr(
 * "YESB0D",6));
 * //log.info("8 - Digit match: ",cCSSBankCodesImpl.getBankCodeFromUsingSubstr(
 * "YESB0DDS",8)); //log.info("Bank Code From IFSC Partial 6 : "+
 * cCSSBankCodesImpl.getBankCodeFrom("DBSS").getBankCode()); }
 * 
 * public <T> T getEnvValue(String key, Class<T> type) { T t =
 * env.getProperty(key, type); if (t == null) throw new
 * ResourceAccessException(key); return t; }
 * 
 * public String getEnvValue(String key) { String value = env.getProperty(key);
 * if (value == null) throw new ResourceAccessException(key); return value; }
 * 
 * public String getEnvSep(String key) { String value = env.getProperty(key); if
 * (value == null) value = "~"; return value; }
 * 
 * public String getEnvValueNotOptional(String key) { String value =
 * env.getProperty(key); if (value == null) {
 * log.info("key Not Confiured in receiveractivemq.proeprties. KEY is : [{}]",
 * key); value = " "; } return value; }
 * 
 * public List<Integer> getsensitiveDE(String key) { String value =
 * env.getProperty(key); if (value == null) { value =
 * Constants.DEFAULT_SENSITIVE_DE; } List<Integer> sensitiveDE =
 * Stream.of(value.split("\\,")).map(Integer::parseInt).collect(Collectors.
 * toList()); // BY default DE 2 will added. if
 * (!sensitiveDE.contains(Integer.valueOf(Constants.DEFAULT_SENSITIVE_DE))) {
 * sensitiveDE.add(Integer.valueOf(Constants.DEFAULT_SENSITIVE_DE)); } return
 * sensitiveDE; }
 * 
 * public static List<String> findMatches(List<String> options, String query) {
 * List<String> matches = new ArrayList<>(); for (String option : options) { if
 * (StringUtils.containsIgnoreCase(option, query)) { matches.add(option); } }
 * return matches; }
 * 
 * }
 */
