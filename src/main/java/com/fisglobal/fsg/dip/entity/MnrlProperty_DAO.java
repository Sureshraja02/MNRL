package com.fisglobal.fsg.dip.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@IdClass(MnrlPropertyPK.class)
@Entity
@Table(name = "MNRL_PROPERTY")
public class MnrlProperty_DAO {
	
	private static final long serialVersionUID = 5918182539654111121L;

	
	@Column(name = "ID")
	private Long Id;
	@Id
	@Column(name="PROPERTY_NAME")
	private String propertyName;
	
	@Column(name="PROPERTY_VALUE")
	private String propertyValue;
	@Id
	@Column(name="ENVIRONMENT")
	private String environment;
	
	@Column(name="INSTITUTION_ID")
	private String institutionId;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getInstitutionId() {
		return institutionId;
	}

	public void setInstitutionId(String institutionId) {
		this.institutionId = institutionId;
	}
}
