package com.fisglobal.fsg.dip.core.comm.beans;

import com.fisglobal.fsg.dip.core.service.RestClient;
import com.fisglobal.fsg.dip.core.utils.Base_VO;

public class PropertyDetails extends Base_VO{
	
	private HeaderProperty headerProperty;
	
	//private PreProdHeaderProperty preProdProperty;
	
	//private ProdHeaderProperty prodProperty;
	
	private AppProperty appProperty;
	
	private RestClient restClient;

	public AppProperty getAppProperty() {
		return appProperty;
	}

	public void setAppProperty(AppProperty appProperty) {
		this.appProperty = appProperty;
	}

	public HeaderProperty getHeaderProperty() {
		return headerProperty;
	}

	public void setHeaderProperty(HeaderProperty headerProperty) {
		this.headerProperty = headerProperty;
	}

	public RestClient getRestClient() {
		return restClient;
	}

	public void setRestClient(RestClient restClient) {
		this.restClient = restClient;
	}

}
