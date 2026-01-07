package com.fisglobal.fsg.dip.entity.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fisglobal.fsg.dip.entity.MnrlCountDetails_DAO;
import com.fisglobal.fsg.dip.entity.ReactivatedCountDetails_DAO;

public interface ReactivatedCountDetails_Repo extends JpaRepository<ReactivatedCountDetails_DAO, Long>{
	
	@Query(value = "select * from reactivated_count_details where id =(select max(id) from reactivated_count_details where fetch_date =(select max(fetch_date) from reactivated_count_details ))", 
			countQuery = "select count(*) from reactivated_count_details where id =(select max(id) from reactivated_count_details where fetch_date =(select max(fetch_date) from reactivated_count_details)", nativeQuery = true)
	ReactivatedCountDetails_DAO getMaxFetchDate();

}
