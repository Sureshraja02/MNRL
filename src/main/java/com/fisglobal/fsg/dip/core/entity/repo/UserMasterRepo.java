package com.fisglobal.fsg.dip.core.entity.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.fisglobal.fsg.dip.core.entity.pk.User_Master_PK;
import com.fisglobal.fsg.dip.entity.User_Master;

public interface UserMasterRepo
		extends JpaRepository<User_Master, User_Master_PK>, JpaSpecificationExecutor<User_Master> {
	
	@Query(value = "SELECT * FROM USER_MASTER WHERE EMAIL = ?1 and PASSWORD =?2", nativeQuery = true)		
	User_Master getLoginData(String userId,String password);
}
