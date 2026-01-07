package com.fisglobal.fsg.dip.entity.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.fisglobal.fsg.dip.entity.MnrlData_DAO;

public interface MnrlDataRepo extends PagingAndSortingRepository<MnrlData_DAO, String> {

	@Query(value = "select * from MNRL_DATA where PROCESS_FLAG='N' and rownum<= :row", 
			countQuery = "select count(*) from MNRL_DATA where PROCESS_FLAG='N'  and rownum<= :row", nativeQuery = true)
	Page<MnrlData_DAO> getMNRLData(Pageable pageable, @Param("row") int row);

}
