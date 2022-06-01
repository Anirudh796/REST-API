package com.restapi.entity;

import javax.validation.constraints.NotEmpty;

import com.restapi.validation.CustomDateConstraint;
import com.restapi.validation.PhoneNoConstraint;

public class Request {
	@PhoneNoConstraint
	private String phonenumber;
	@CustomDateConstraint
	private String scheduledTime;
	@NotEmpty(message = "Message should not be empty")
	private String message;
	
	public Request() {
		
	}
	
	public Request(String phonenumber, String scheduledTime, String message) {
		super();
		this.phonenumber = phonenumber;
		this.scheduledTime = scheduledTime;
		this.message = message;
	}
	
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getScheduledTime() {
		return scheduledTime;
	}
	public void setScheduledTime(String scheduledTime) {
		this.scheduledTime = scheduledTime;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
