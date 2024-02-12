package com.synex.domain;

import jakarta.persistence.*;

@Entity
public class PaymentData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cardNumber;
    private String expiryDate;
    private String cvv;
    private String nameOnCard;
    private double totalAmount;
    private String paymentMethod;
    private String phone;
    
    @Column(length = 1000)
   	private String receiptUrl;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address billingAddress;
    
   

    public PaymentData() {
        super();
    }

    public PaymentData(String paymentMethod, String cardNumber, String expiryDate, String cvv, String nameOnCard, double totalAmount, Address billingAddress, String phone, String receiptUrl) {
        this.paymentMethod = paymentMethod;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.nameOnCard = nameOnCard;
        this.totalAmount = totalAmount;
        this.billingAddress = billingAddress;
        this.phone = phone;
        this.receiptUrl = receiptUrl;
        
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    public String getPhone() {
    	return phone;
    }
    
    public void setPhone(String phone) {
    	this.phone = phone;
    }
    
    public String getReceiptUrl() {
    	return receiptUrl;
    }

	public void setReceiptUrl(String receiptUrl) {
		this.receiptUrl = receiptUrl;
		
	}
}