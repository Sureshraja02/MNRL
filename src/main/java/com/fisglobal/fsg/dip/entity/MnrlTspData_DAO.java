package com.fisglobal.fsg.dip.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Table(name = "MNRL_TSP_DATA")
@Entity
public class MnrlTspData_DAO {

	@Id
	@GeneratedValue(generator = "ID", strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name = "IDSEQTSP", sequenceName = "IDSEQTSP", allocationSize = 1)
	private Long id;

	@Column(name = "TSPID")
	private String tspId;

	@Column(name = "TSPNAME")
	private String tspName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTspId() {
		return tspId;
	}

	public void setTspId(String tspId) {
		this.tspId = tspId;
	}

	public String getTspName() {
		return tspName;
	}

	public void setTspName(String tspName) {
		this.tspName = tspName;
	}

}
