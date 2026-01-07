package com.fisglobal.fsg.dip.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Table(name = "REACTIVATED_COUNT_DETAILS")
@Entity
public class ReactivatedCountDetails_DAO {

	@Id
	@GeneratedValue(generator = "ID", strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name = "IDSEQREQDT", sequenceName = "IDSEQREQDT", allocationSize = 1)
	private Long id;

	@Column(name = "FETCH_DATE")
	private String fetchDate;

	@Column(name = "COUNT")
	private int count;

	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "ENTRY_DATE",columnDefinition = "TIMESTAMP",insertable=false,updatable=false)
	private LocalDateTime entryDate;
		

	public Long getId() {
		return id;
	}

	public LocalDateTime getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(LocalDateTime entryDate) {
		this.entryDate = entryDate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFetchDate() {
		return fetchDate;
	}

	public void setFetchDate(String fetchDate) {
		this.fetchDate = fetchDate;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
