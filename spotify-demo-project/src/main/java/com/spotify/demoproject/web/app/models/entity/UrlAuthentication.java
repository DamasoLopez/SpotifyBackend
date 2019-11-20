package com.spotify.demoproject.web.app.models.entity;

import org.springframework.stereotype.Component;

@Component
public class UrlAuthentication {
	String clientId ="afb8bc11aa234e918958092cefec78a6";
	String redirectUri="http://localhost:4200/";
	String responseType="code";
	String code;
	String secretId="fa2a3abc11be42e4a8b19284af6ac300";
	String grant_type="authorization_code";
	
	public String getGrant_type() {
		return grant_type;
	}
	public void setGrant_type(String grant_type) {
		this.grant_type = grant_type;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getRedirectUri() {
		return redirectUri;
	}
	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}
	public String getResponseType() {
		return responseType;
	}
	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getSecretId() {
		return secretId;
	}
	public void setSecretId(String secretId) {
		this.secretId = secretId;
	}
	
	
	
}
