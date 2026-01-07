package com.fisglobal.fsg.dip.core.fri.cbs.service;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fisglobal.fsg.dip.entity.FriCbsData_DAO;
import com.fisglobal.fsg.dip.fri.entity.FRIAtrResponse;
import com.fisglobal.fsg.dip.fri.service.FriAtrService;
import com.fisglobal.fsg.dip.request.entity.MnrlAtrData_VO;

@Service
public class FriAtrUploadService {

	private static final Logger LOGGER = LoggerFactory.getLogger(FriAtrUploadService.class);

	@Inject
	private FriAtrService atrService;

	@Value("${error.invalid.header:Invalid Header}")
	private String invalidHeader;

	public void processFriAtr(List<MnrlAtrData_VO> atrdatalist,List<FriCbsData_DAO> cbsdatalist) throws Exception {

		 atrService.processAtrData(atrdatalist,cbsdatalist);

		/*if (!(Optional.ofNullable(atrResponse.getErrorResponse()).isPresent())) {
			friCbsEntity.setAtrUploadFlag("Y");
			friCbsEntity.setActionFlag("0");
		}

		Optional.ofNullable(atrResponse.getErrorResponse()).ifPresent(a -> {
			Optional.ofNullable(a.getAdditionalinfo()).ifPresent(b -> {
				if (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepText())
						&& a.getAdditionalinfo().getExcepText().equals(invalidHeader)) {
					LOGGER.info("Error [{}]", a.getAdditionalinfo().getExcepText());
					friCbsEntity.setAtrUploadFlag("Y");
					friCbsEntity.setActionFlag("0");
				} else {
					if (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepMetaData())) {
						friCbsEntity.setAtrUploadFlag("E");
						friCbsEntity.setFriAtrResponse(a.getAdditionalinfo().getExcepMetaData());
						friCbsEntity.setActionFlag("12");
						LOGGER.info("ATR EMetadata [{}]", a.getAdditionalinfo().getExcepMetaData());
					} else if (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepText())) {
						friCbsEntity.setAtrUploadFlag("E");
						friCbsEntity.setFriAtrResponse(a.getAdditionalinfo().getExcepText());
						friCbsEntity.setActionFlag("12");
						LOGGER.info("ATR EText [{}]", a.getAdditionalinfo().getExcepText());
					} else if (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepCode())) {
						friCbsEntity.setAtrUploadFlag("E");
						friCbsEntity.setFriAtrResponse(a.getAdditionalinfo().getExcepCode());
						friCbsEntity.setActionFlag("12");
						LOGGER.info("ATR Ecode [{}]", a.getAdditionalinfo().getExcepCode());
					}
				}
			});
		});

		Optional.ofNullable(atrResponse.getResponseCode()).ifPresent(a -> {
			if (atrResponse.getResponseCode().equals("200")) {
				friCbsEntity.setAtrUploadFlag("C");
				friCbsEntity.setActionFlag("1");
				friCbsEntity.setFriAtrResponse(atrResponse.getMessage());
			} else {
				friCbsEntity.setAtrUploadFlag("E");
				friCbsEntity.setActionFlag("2");
				friCbsEntity.setFriAtrResponse(atrResponse.getMessage());
			}

		});

		/*
		 * if(atrResponse!=null) { if(atrResponse.getResponseCode().equals("200") &&
		 * atrResponse.getMessage().equals("success")) {
		 * friCbsEntity.setAtrUploadFlag("C"); friCbsEntity.setActionFlag("1"); }else {
		 * friCbsEntity.setAtrUploadFlag("F"); friCbsEntity.setActionFlag("2"); } }else
		 * { friCbsEntity.setAtrUploadFlag("F"); friCbsEntity.setActionFlag("2"); }
		 */

		/*
		 * LOGGER.info("atrflag after response [{}]", friCbsEntity.getAtrUploadFlag());
		 * 
		 * return friCbsEntity;
		 */

	}

}
