package com.fisglobal.fsg.dip.core.utils;

public class DataProcessingException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Base_VO sourceObject;
	private String search;
	private String code;
	private String message;

	/**
	 * @param sourceObject
	 * @param code
	 * @param message
	 */
	public DataProcessingException(Base_VO sourceObject, String code, String message) {
		super();
		this.sourceObject = sourceObject;
		this.code = code;
		this.message = message;
	}
	public DataProcessingException(String search, String code, String message) {
		super();
		this.search = search;
		this.code = code;
		this.message = message;
	}
	
	public DataProcessingException(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	/**
	 * @return the sourceObject
	 */
	public Base_VO getSourceObject() {
		return sourceObject;
	}
	/**
	 * @param sourceObject the sourceObject to set
	 */
	public void setSourceObject(Base_VO sourceObject) {
		this.sourceObject = sourceObject;
	}
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the search
	 */
	public String getSearch() {
		return search;
	}
	/**
	 * @param search the search to set
	 */
	public void setSearch(String search) {
		this.search = search;
	}
}
