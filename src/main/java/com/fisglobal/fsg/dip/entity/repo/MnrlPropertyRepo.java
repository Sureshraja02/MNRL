package com.fisglobal.fsg.dip.entity.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fisglobal.fsg.dip.entity.MnrlProperty_DAO;
import com.fisglobal.fsg.dip.entity.MnrlTokenCredDtls_DAO;

public interface MnrlPropertyRepo extends JpaRepository<MnrlProperty_DAO, String>{
	
	@Query(value = "select a.* from MNRL_PROPERTY a where a.environment=?", nativeQuery = true)
	List<MnrlProperty_DAO> findDataById(String id);	
	

}
