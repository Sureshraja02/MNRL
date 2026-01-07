/*
 * package com.fisglobal.fsg.dip.core.service;
 * 
 * import org.slf4j.Logger; import org.slf4j.LoggerFactory; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.beans.factory.annotation.Value; import
 * org.springframework.stereotype.Component;
 * 
 * import com.fisglobal.fsg.dip.core.comm.beans.Router_VO; import
 * com.fisglobal.fsg.dip.core.utils.Connection_VO;
 * 
 * @Component public class MiddlewareConnectionService { private final Logger
 * log = LoggerFactory.getLogger(MiddlewareConnectionService.class);
 * 
 * @Autowired private CommonMethodUtils commonMethodUtils;
 * 
 * @Value("${inst.instcode}") private String instCode;
 * 
 * public Connection_VO getMiddlewareConnectionObj() { Router_VO router = null;
 * Connection_VO cortexHttpBeanObj = null; try { router =
 * commonMethodUtils.getBankRouters(instCode); cortexHttpBeanObj = new
 * Connection_VO();
 * cortexHttpBeanObj.setConnTimeOut(router.getMiddlewareConnTimeOut());
 * cortexHttpBeanObj.setHost(router.getMiddlewarehost());
 * cortexHttpBeanObj.setMaxConnCount(router.getMiddlewareMaxConnCount());
 * cortexHttpBeanObj.setMaxConnPerHost(router.getMiddlewareMaxConnPerHost());
 * cortexHttpBeanObj.setMaxPerRoute(router.getMiddlewareMaxPerRoute());
 * cortexHttpBeanObj.setPort(router.getMiddlewarePort());
 * cortexHttpBeanObj.setSoTimeOut(router.getMiddlewareSoTimeOut());
 * cortexHttpBeanObj.setEndUrl(router.getMiddlewareEndUrl());
 * cortexHttpBeanObj.setJkspath(router.getMiddlewarejkspath());
 * cortexHttpBeanObj.setSslVersion(router.getMiddlewaresslversion());
 * cortexHttpBeanObj.setJksfileSecureTerm(router.getMiddlewarejksSecureTerm());
 * } catch (Exception e) { cortexHttpBeanObj = null;
 * log.error("Exception found in fetching Middleware : [{}]", e); } finally {
 * router = null; } return cortexHttpBeanObj;
 * 
 * } }
 */
