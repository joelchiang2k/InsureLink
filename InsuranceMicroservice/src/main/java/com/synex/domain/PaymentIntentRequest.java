package com.synex.domain;

import com.stripe.param.PaymentIntentCreateParams;

public class PaymentIntentRequest {
	private String paymentMethodId;
    private String driverId;
    private Long amount;
    
	public String getPaymentMethodId() {
		return paymentMethodId;
	}
	public void setPaymentMethodId(String paymentMethodId) {
		this.paymentMethodId = paymentMethodId;
	}
	public String getDriverId() {
		return driverId;
	}
	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}
	
	public PaymentIntentCreateParams toPaymentIntentParams() {
        PaymentIntentCreateParams.Builder builder = PaymentIntentCreateParams.builder()
                .setPaymentMethod(paymentMethodId);
               
              
        return builder.build();
    }
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
}
