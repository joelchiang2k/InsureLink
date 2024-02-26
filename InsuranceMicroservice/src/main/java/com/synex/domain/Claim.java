package com.synex.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class Claim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long amount;
    private Long policyNumber;
    private String reason;
    private String claimStatus;

    @Lob
    private byte[] mishapImage; // Changed to single byte array

    public Claim() {
        // No need to initialize mishapImage here
    }

    public Claim(byte[] mishapImage) {
        this.mishapImage = mishapImage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(Long policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getClaimStatus() {
        return claimStatus;
    }

    public void setClaimStatus(String claimStatus) {
        this.claimStatus = claimStatus;
    }

    public byte[] getMishapImage() {
        return mishapImage;
    }

    public void setMishapImage(byte[] mishapImage) {
        this.mishapImage = mishapImage;
    }
}
