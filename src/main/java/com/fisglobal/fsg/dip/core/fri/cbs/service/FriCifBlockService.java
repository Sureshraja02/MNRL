package com.fisglobal.fsg.dip.core.fri.cbs.service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fisglobal.fsg.dip.core.cbs.entity.CIFBlockResponseVo;
import com.fisglobal.fsg.dip.core.cbs.service.CIFBlockService;
import com.fisglobal.fsg.dip.core.service.CommonMethodUtils;
import com.fisglobal.fsg.dip.entity.FriCbsData_DAO;
import com.fisglobal.fsg.dip.entity.repo.FriCbsDataRepo;

@Service
public class FriCifBlockService {

	private static final Logger LOGGER = LoggerFactory.getLogger(FriCifBlockService.class);

	@Inject
	private CIFBlockService cifBlockService;

	@Inject
	private CommonMethodUtils commonMethodUtils;
	
	@Value("${error.invalid.header:Invalid Header}")
	private String invalidHeader;
	
	@Inject
	private FriCbsDataRepo cbsDataRepo;

	public FriCbsData_DAO processCifBlock(FriCbsData_DAO friCbsEntity) throws KeyManagementException,
			ClientProtocolException, NoSuchAlgorithmException, KeyStoreException, JSONException, IOException {

		CIFBlockResponseVo cifBlockResp = new CIFBlockResponseVo();
		String mobile = commonMethodUtils.getMobileNumberWithoutCC(friCbsEntity.getMobileNo());
		friCbsEntity.setCifBlockDateTime(LocalDateTime.now());
		cbsDataRepo.save(friCbsEntity);
		
		cifBlockResp = cifBlockService.processCifBlock(mobile, "FRI");
		
		friCbsEntity.setCifBlockDateTime(LocalDateTime.now());
		Optional.ofNullable(cifBlockResp.getCIFBlockingResponse()).ifPresent(m -> {
			Optional.ofNullable(m.getBody()).ifPresent(n -> {
				Optional.ofNullable(n.getPayload()).ifPresent(o -> {
					Optional.ofNullable(o.getStatus()).ifPresent(p -> {
						if (m.getBody().getPayload().getStatus().equals("O.K.")) {
							friCbsEntity.setCifBlockFlag("C");
							friCbsEntity.setCifBlockResponse(m.getBody().getPayload().getStatus());
							LOGGER.info("cif block status [{}]", m.getBody().getPayload().getStatus());
						}
					});

				});
			});
		});

		Optional.ofNullable(cifBlockResp.getErrorResponse()).ifPresent(a -> {
			Optional.ofNullable(a.getAdditionalinfo()).ifPresent(b -> {
				if (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepText())
						&& a.getAdditionalinfo().getExcepText().equals(invalidHeader)) {
					LOGGER.info("Error [{}]",a.getAdditionalinfo().getExcepText());
					friCbsEntity.setCifBlockFlag("EX");
					friCbsEntity.setCifBlockResponse(a.getAdditionalinfo().getExcepText());

				}else {
				if (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepMetaData())) {
					friCbsEntity.setCifBlockFlag("E");
					friCbsEntity.setCifBlockResponse(a.getAdditionalinfo().getExcepMetaData());
					LOGGER.info("Cif block EMetadata [{}]", a.getAdditionalinfo().getExcepMetaData());
				} else if (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepText())) {
					friCbsEntity.setCifBlockFlag("E");
					friCbsEntity.setCifBlockResponse(a.getAdditionalinfo().getExcepText());
					LOGGER.info("Cif block[{}]", a.getAdditionalinfo().getExcepText());
				} else if (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepCode())) {
					friCbsEntity.setCifBlockFlag("E");
					friCbsEntity.setCifBlockResponse(a.getAdditionalinfo().getExcepCode());
					LOGGER.info("Cif block Ecode [{}]", a.getAdditionalinfo().getExcepCode());
				}
			}
			});
		});

		if (!(Optional.ofNullable(cifBlockResp.getCIFBlockingResponse()).isPresent()
				|| Optional.ofNullable(cifBlockResp.getErrorResponse()).isPresent())) {
			friCbsEntity.setCifBlockFlag("EX");
		}

		/*
		 * if(cifBlockResp!=null) {
		 * if(cifBlockResp.getCIFBlockingResponse().getBody().getPayload().getStatus().
		 * equals("O.K.")) { friCbsEntity.setCifBlockFlag("C"); }else {
		 * friCbsEntity.setCifBlockFlag("F"); friCbsEntity.setActionFlag("2"); }
		 * friCbsEntity.setCifBlockFlag("C"); }else { friCbsEntity.setCifBlockFlag("F");
		 * friCbsEntity.setActionFlag("4"); }
		 */

		LOGGER.info("cifblockflag after response [{}]", friCbsEntity.getCifBlockFlag());

		return friCbsEntity;
	}

}
