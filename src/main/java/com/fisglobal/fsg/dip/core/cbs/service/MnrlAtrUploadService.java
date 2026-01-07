package com.fisglobal.fsg.dip.core.cbs.service;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fisglobal.fsg.dip.entity.MnrlCbsData_DAO;
import com.fisglobal.fsg.dip.req.res.entity.V2.MnrlAtrResponseV2;
import com.fisglobal.fsg.dip.request.entity.MnrlAtrData_VO;
import com.fisglobal.fsg.dip.service.V2.MnrlAtrServiceV2;

@Service
public class MnrlAtrUploadService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MnrlAtrUploadService.class);

	@Inject
	private MnrlAtrServiceV2 atrService;


	public void processMnrlAtr(List<MnrlAtrData_VO> atrdatalist,List<MnrlCbsData_DAO> cbslist) throws Exception {

		  atrService.processAtrData(atrdatalist,cbslist);


	}

}
