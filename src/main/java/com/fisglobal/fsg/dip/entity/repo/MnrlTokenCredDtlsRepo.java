package com.fisglobal.fsg.dip.entity.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fisglobal.fsg.dip.entity.MnrlTokenCredDtls_DAO;

public interface MnrlTokenCredDtlsRepo extends JpaRepository<MnrlTokenCredDtls_DAO, Long>{
	
	@Query(value = "select a.* from MNRL_TOKEN_CRED_DTLS a where a.ID=?", nativeQuery = true)
	MnrlTokenCredDtls_DAO findDataById(String id);
	
	@Query(value = "select a.* from MNRL_TOKEN_CRED_DTLS a where a.INDICATOR=?", nativeQuery = true)
	MnrlTokenCredDtls_DAO findDataByIndicator(String indicator);

}
