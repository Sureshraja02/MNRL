package com.fisglobal.fsg.dip.entity.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fisglobal.fsg.dip.entity.FriCbsDataPK;
import com.fisglobal.fsg.dip.entity.FriCbsData_DAO;
import com.fisglobal.fsg.dip.entity.MnrlCbsData_DAO;

public interface FriCbsDataRepo extends JpaRepository<FriCbsData_DAO, FriCbsDataPK> {
	
	@Query(value="select * from FRI_CBS_DATA where ACTION_FLAG='0' and rownum<= :row", 
			countQuery="select count(*) from FRI_CBS_DATA where ACTION_FLAG='0' and rownum<= :row",
			nativeQuery = true)	
	Page<FriCbsData_DAO> getFriActionCbsData(Pageable pageable,@Param("row") int row);
	
	@Query(value="select * from FRI_CBS_DATA where  ATR_UPLOAD_FLAG='R' and action_flag='77' and rownum<= :row ", 
			countQuery="select count(*) from FRI_CBS_DATA where  ATR_UPLOAD_FLAG='R' and action_flag='77' and rownum<= :row",
			nativeQuery = true)	
	Page<FriCbsData_DAO> getAtrCbsData(Pageable pageable,@Param("row") int row);
	
	@Query(value="select * from FRI_CBS_DATA where atr_upload_flag='R'  and MOBILE_NO = :mobileNo and uuid=:uuid  ", 
			countQuery="select count(*) from FRI_CBS_DATA where atr_upload_flag='R'  and MOBILE_NO = :mobileNo and uuid=:uuid ",
			nativeQuery = true)	
	FriCbsData_DAO getAtrUploadData(@Param("mobileNo") String mobileNo,@Param("uuid") String uuid);

}
