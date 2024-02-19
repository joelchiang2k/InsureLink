package com.synex.domain;

public class PaymentResponse {
    private String clientSecret;
    private Driver driver;
	public String getClientSecret() {
		return clientSecret;
	}
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	public Driver getDriver() {
		return driver;
	}
	public void setDriver(Driver driver) {
		this.driver = driver;
	}

    // Constructor, getters, and setters
}
