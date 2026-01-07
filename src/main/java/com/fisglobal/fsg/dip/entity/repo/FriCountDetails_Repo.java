package com.fisglobal.fsg.dip.entity.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fisglobal.fsg.dip.entity.FriCountDetails_DAO;
import com.fisglobal.fsg.dip.entity.MnrlCountDetails_DAO;

public interface FriCountDetails_Repo extends JpaRepository<FriCountDetails_DAO, Long>{
	
	@Query(value = "select * from fri_count_details where id =(select max(id) from fri_count_details where fetch_date =(select max(fetch_date) from fri_count_details ))", 
			countQuery = "select count(*) from fri_count_details where id =(select max(id) from fri_count_details where fetch_date =(select max(fetch_date) from fri_count_details)", nativeQuery = true)
	FriCountDetails_DAO getMaxFetchDate();

}
