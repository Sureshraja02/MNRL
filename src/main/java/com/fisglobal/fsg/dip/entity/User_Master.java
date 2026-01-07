package com.fisglobal.fsg.dip.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Table(name = "USER_MASTER")
@Entity
public class User_Master implements Serializable, UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "userseq")
	@SequenceGenerator(name = "userseq", sequenceName = "USERSEQ", allocationSize = 1)
	@Column(name = "USERID")
	private Long userID;

	@Column(name = "USERNAME")
	private String userName;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "USERTYPE")
	private String userType;

	@Column(name = "PASSWORD")
	private String userSecureTerm;
	
	@Column(name = "CLRI4CPWD")
	private String i4cUsrSecTerm;

	@Column(name = "PASSWORDDATE", columnDefinition = "TIMESTAMP")
	private LocalDateTime passwordDate;

	@Column(name = "GROUPID")
	private String groupID;

	@Column(name = "GROUPNAME")
	private String groupName;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "INSTID")
	private String instID;

	@Column(name = "MOBILENO")
	private String mobileNo;

	@Column(name = "CREATEUSERID")
	private String createUserID;

	@Column(name = "INSERTEDDATE")
	private LocalDateTime insertDate;

	@Column(name = "MODIFIEDDATE")
	private LocalDateTime modifiedDate;

	@Column(name = "FIRSTTIMELOGIN")
	private String isFirstTimeLogin;
	
	@Column(name = "API_ENC_DEC_KEY")
	private String apiEncDecKey;
	
	@Column(name = "CHANNEL")
	private String channel;

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}

	
	
	public LocalDateTime getPasswordDate() {
		return passwordDate;
	}

	public String getUserSecureTerm() {
		return userSecureTerm;
	}

	public void setUserSecureTerm(String userSecureTerm) {
		this.userSecureTerm = userSecureTerm;
	}

	public void setPasswordDate(LocalDateTime passwordDate) {
		this.passwordDate = passwordDate;
	}

	public String getGroupID() {
		return groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getInstID() {
		return instID;
	}

	public void setInstID(String instID) {
		this.instID = instID;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getCreateUserID() {
		return createUserID;
	}

	public void setCreateUserID(String createUserID) {
		this.createUserID = createUserID;
	}

	public LocalDateTime getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(LocalDateTime insertDate) {
		this.insertDate = insertDate;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getIsFirstTimeLogin() {
		return isFirstTimeLogin;
	}

	public void setIsFirstTimeLogin(String isFirstTimeLogin) {
		this.isFirstTimeLogin = isFirstTimeLogin;
	}
	
	public String getApiEncDecKey() {
		return apiEncDecKey;
	}

	public void setApiEncDecKey(String apiEncDecKey) {
		this.apiEncDecKey = apiEncDecKey;
	}
	
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getI4cUsrSecTerm() {
		return i4cUsrSecTerm;
	}

	public void setI4cUsrSecTerm(String i4cUsrSecTerm) {
		this.i4cUsrSecTerm = i4cUsrSecTerm;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getPassword() {
		if(this.userSecureTerm!=null) {
			return userSecureTerm;
		} else {
			return getUserSecureTerm();
		}
		
	}
	

}
