package com.fisglobal.fsg.dip.entity.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fisglobal.fsg.dip.entity.MnrlCountDetails_DAO;

public interface MnrlCountDetails_Repo extends JpaRepository<MnrlCountDetails_DAO, Long>{
	
	@Query(value = "select * from mnrl_count_details where id =(select max(id) from mnrl_count_details where fetch_date =(select max(fetch_date) from mnrl_count_details ))", 
			countQuery = "select count(*) from mnrl_count_details where id =(select max(id) from mnrl_count_details where fetch_date =(select max(fetch_date) from mnrl_count_details ))", nativeQuery = true)
	MnrlCountDetails_DAO getMaxFetchDate();

}
