package com.synex.domain;

import jakarta.persistence.*;

@Entity
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private byte[] driverLicense;

    private String fileName;

    // Constructors, getters, and setters

    public Document() {
    }

    public Document(byte[] driverLicense, String fileName) {
        this.driverLicense = driverLicense;
        this.fileName = fileName;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getDriverLicense() {
        return driverLicense;
    }

    public void setDriverLicense(byte[] driverLicense) {
        this.driverLicense = driverLicense;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}