package com.tinyurl.entity;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


public class UrlToBeShortenedBean {
	
	@JsonProperty("fullurl")
	String fullUrl;
	
	@JsonProperty("timestamp")
	@JsonIgnore
	LocalDateTime timeStamp;

	public String getFullUrl() {
		return fullUrl;
	}

	public void setFullUrl(String fullUrl) {
		this.fullUrl = fullUrl;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}

	@Override
	public String toString() {
		return "URLEntity [fullUrl=" + fullUrl + ", timeStamp=" + timeStamp + "]";
	}

	public UrlToBeShortenedBean(String fullUrl, LocalDateTime timeStamp) {
		super();
		this.fullUrl = fullUrl;
		this.timeStamp = timeStamp;
	}
	public UrlToBeShortenedBean() {
		
	}
	

}
