package com.fisglobal.fsg.dip.core.cbs.service;

import java.io.IOException;
import java.math.BigInteger;
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
import com.fisglobal.fsg.dip.core.service.CommonMethodUtils;
import com.fisglobal.fsg.dip.entity.MnrlCbsData_DAO;
import com.fisglobal.fsg.dip.entity.repo.MnrlCbsDataRepo;

@Service
public class MnrlCifBlockService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MnrlCifBlockService.class);
	
	@Inject
	private CIFBlockService cifService;
	
	@Inject
	private CommonMethodUtils commonMethodUtils;
	
	@Value("${error.invalid.header:Invalid Header}")
	private String invalidHeader;
	
	@Inject
	private MnrlCbsDataRepo cbsDataRepo;

	public MnrlCbsData_DAO processCifBlock(MnrlCbsData_DAO mnrlCbsEntity) throws KeyManagementException,
			ClientProtocolException, NoSuchAlgorithmException, KeyStoreException, JSONException, IOException {

		CIFBlockResponseVo cifBlockresponse = new CIFBlockResponseVo();

		//BigInteger bigInt = new BigInteger(mnrlCbsEntity.getAccountNo());
		
		String mobile=commonMethodUtils.getMobileNumberWithoutCC(mnrlCbsEntity.getMobileNo());
		mnrlCbsEntity.setCifBlockDateTime(LocalDateTime.now());
		cbsDataRepo.save(mnrlCbsEntity);
		
		cifBlockresponse = cifService.processCifBlock(mobile,"MNRL");
		
		mnrlCbsEntity.setCifBlockDateTime(LocalDateTime.now());
		if (!(Optional.ofNullable(cifBlockresponse.getCIFBlockingResponse()).isPresent()
				|| Optional.ofNullable(cifBlockresponse.getErrorResponse()).isPresent())) {
			mnrlCbsEntity.setCifBlockFlag("EX");
		}
		
		Optional.ofNullable(cifBlockresponse.getCIFBlockingResponse()).ifPresent(m -> {
			Optional.ofNullable(m.getBody()).ifPresent(n -> {
				Optional.ofNullable(n.getPayload()).ifPresent(o -> {
					Optional.ofNullable(o.getStatus()).ifPresent(p -> {
						if (m.getBody().getPayload().getStatus().equals("O.K.")) {
							mnrlCbsEntity.setCifBlockFlag("C");
							mnrlCbsEntity.setCifBlockResponse(m.getBody().getPayload().getStatus());
							LOGGER.info("cif block status [{}]", m.getBody().getPayload().getStatus());
						}
					});

				});
			});
		});
		
		Optional.ofNullable(cifBlockresponse.getErrorResponse()).ifPresent(a -> {
			Optional.ofNullable(a.getAdditionalinfo()).ifPresent(b -> {
				if (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepText())
						&& a.getAdditionalinfo().getExcepText().equals(invalidHeader)) {
					LOGGER.info("Error [{}]",a.getAdditionalinfo().getExcepText());
					mnrlCbsEntity.setCifBlockFlag("EX");
					mnrlCbsEntity.setCifBlockResponse(a.getAdditionalinfo().getExcepText());

				}else {
				if (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepMetaData())) {
					mnrlCbsEntity.setCifBlockFlag("E");
					mnrlCbsEntity.setCifBlockResponse(a.getAdditionalinfo().getExcepMetaData());
					LOGGER.info("Cif block EMetadata [{}]", a.getAdditionalinfo().getExcepMetaData());
				} else if (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepText())) {
					mnrlCbsEntity.setCifBlockFlag("E");
					mnrlCbsEntity.setCifBlockResponse(a.getAdditionalinfo().getExcepText());
					LOGGER.info("Cif block[{}]", a.getAdditionalinfo().getExcepText());
				}else if (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepCode())) {
					mnrlCbsEntity.setCifBlockFlag("E");
					mnrlCbsEntity.setCifBlockResponse(a.getAdditionalinfo().getExcepCode());
					LOGGER.info("Cif block Ecode [{}]", a.getAdditionalinfo().getExcepCode());
				}
			}
			});
		});

		

		/*if (cifBlockresponse != null) {
			/*if (cifBlockresponse.getCIFBlockingResponse().getBody().getPayload().getStatus().equals("O.K.")) {
				mnrlCbsEntity.setCifBlockFlag("C");
			}else {
				mnrlCbsEntity.setCifBlockFlag("F");
				mnrlCbsEntity.setActionFlag("4");
			}
			
			mnrlCbsEntity.setCifBlockFlag("C");
		}else {
			mnrlCbsEntity.setCifBlockFlag("F");
			mnrlCbsEntity.setActionFlag("4");
		}*/
		
		LOGGER.info("cifblockflag after response [{}]",mnrlCbsEntity.getCifBlockFlag());
		return mnrlCbsEntity;
	}

}
