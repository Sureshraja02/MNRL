package com.fisglobal.fsg.dip.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Table(name = "REACTIVATED_REQ_DATA")
@Entity
public class ReactivatedReqData_DAO {

	@Id
	@GeneratedValue(generator = "ID", strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name = "IDSEQREQDT", sequenceName = "IDSEQREQDT", allocationSize = 1)
	private Long id;
	
	
	@Column(name = "OFFSET")
	private int offset; 
	
	@Column(name = "REC_CNT")
	private int recCnt;
	
	//@Column(name = "ENTRY_DATE")
	//private Timestamp entryDate;
	
	@Column(name = "BANKID")
	private String bankId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getRecCnt() {
		return recCnt;
	}

	public void setRecCnt(int recCnt) {
		this.recCnt = recCnt;
	}

	/*public Timestamp getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Timestamp entryDate) {
		this.entryDate = entryDate;
	}*/
	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	} 
}
