package com.fisglobal.fsg.dip.entity.repo;

import java.time.LocalDateTime;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fisglobal.fsg.dip.entity.MnrlCbsDataPK;
import com.fisglobal.fsg.dip.entity.MnrlCbsData_DAO;

public interface MnrlCbsDataRepo extends JpaRepository<MnrlCbsData_DAO, MnrlCbsDataPK> {
	
	@Query(value="select * from MNRL_CBS_DATA where PROCESS_FLAG='0' and rownum<= :row", 
			countQuery="select count(*) from MNRL_CBS_DATA where PROCESS_FLAG='0' and rownum<= :row",
			nativeQuery = true)	
	Page<MnrlCbsData_DAO> getCbsData(Pageable pageable,@Param("row") int row);
	
	@Query(value="select * from MNRL_CBS_DATA where  ACTION_FLAG='0' and PROCESS_FLAG != '0' and rownum<= :row", 
			countQuery="select count(*) from MNRL_CBS_DATA where ACTION_FLAG='0' and PROCESS_FLAG != '0' and rownum<= :row",
			nativeQuery = true)	
	Page<MnrlCbsData_DAO> getActionCbsData(Pageable pageable,@Param("row") int row);
	
	@Query(value="select * from MNRL_CBS_DATA where  ATR_UPLOAD_FLAG='R' and action_flag='77' and rownum<= :row ", 
			countQuery="select count(*) from MNRL_CBS_DATA where  ATR_UPLOAD_FLAG='R' and action_flag='77' and rownum<= :row",
			nativeQuery = true)	
	Page<MnrlCbsData_DAO> getAtrCbsData(Pageable pageable,@Param("row") int row);
	
	@Query(value="select count(*) from MNRL_CBS_DATA where (DEBIT_FREEZE_FLAG='E' or CIF_BLOCK_FLAG='E' or MOBILE_REMOVAL_FLAG='E') and MOBILE_NO=:mobileNo ", 
			countQuery="select count(*) from MNRL_CBS_DATA where (DEBIT_FREEZE_FLAG='E' or CIF_BLOCK_FLAG='E' or MOBILE_REMOVAL_FLAG='E') and MOBILE_NO=:mobileNo ",
			nativeQuery = true)	
	int checkAtrUpload(@Param("mobileNo") String mobileNo);
	
	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "UPDATE MNRL_CBS_DATA u SET u.CIF = :updatedCif, u.ACC_ENQUIRY_FLAG='Y', u.PROCESS_FLAG= :processFlag,u.ACCENQ_DATETIME=:accountEnquiryDateTime WHERE U.MOBILE_NO = :mobileNo AND U.CIF = :cif and U.ACC_NO = :accountNo and U.UUID = :uuid" )
	int updateCifCbsData(@Param("accountNo") String accountNo,@Param("cif") String cif,@Param("mobileNo") String mobileNo,@Param("updatedCif") String updateCif,@Param("processFlag") String processFlag,@Param("uuid") String uuid,@Param("accountEnquiryDateTime") LocalDateTime accountEnquiryDateTime);

	
	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "UPDATE MNRL_CBS_DATA u SET u.ACC_NO = :updatedAccNo, u.CIF_ENQUIRY_FLAG='Y', u.PROCESS_FLAG= :processFlag ,u.PRIMARY_ACC_FLAG = :primaryAccountFlag, u.customer_name= :customerName , u.acct_type= :acctType, u.CIFENQ_DATETIME=:cifEnquiryDateTime WHERE U.MOBILE_NO = :mobileNo AND U.CIF = :cif and U.ACC_NO = :accountNo and U.UUID = :uuid")
	int updateAccCbsData(@Param("accountNo") String accountNo,@Param("cif") String cif,@Param("mobileNo") String mobileNo,@Param("updatedAccNo") String updateAccNo,@Param("processFlag") String processFlag,
			@Param("primaryAccountFlag") String primaryAccountFlag,@Param("customerName") String customerName,@Param("acctType") String acctType,@Param("uuid") String uuid,@Param("cifEnquiryDateTime") LocalDateTime cifEnquiryDateTime);
	
	
	@Query(value="select * from MNRL_CBS_DATA where atr_upload_flag='R'  and MOBILE_NO = :mobileNo and uuid=:uuid ", 
			countQuery="select count(*) from MNRL_CBS_DATA where atr_upload_flag='R'  and MOBILE_NO = :mobileNo and uuid=:uuid",
			nativeQuery = true)	
	MnrlCbsData_DAO getAtrUploadData(@Param("mobileNo") String mobileNo,@Param("uuid") String uuid);

}
