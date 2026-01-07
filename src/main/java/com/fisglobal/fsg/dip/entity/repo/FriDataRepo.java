package com.fisglobal.fsg.dip.entity.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.fisglobal.fsg.dip.entity.FriData_DAO;

public interface FriDataRepo extends PagingAndSortingRepository<FriData_DAO, String>{
	
	@Query(value="select * from FRI_DATA where PROCESS_FLAG='N' and rownum<= :row", 
			countQuery="select count(*) from FRI_DATA where PROCESS_FLAG='N' and rownum<= :row",
			nativeQuery = true)	
	Page<FriData_DAO> getFRIData(Pageable pageable,@Param("row") int row);

}
