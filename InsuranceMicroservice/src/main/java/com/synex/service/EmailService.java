package com.synex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.synex.domain.Address;
import com.synex.domain.Driver;
import com.synex.domain.InsurancePlan;

import jakarta.mail.Multipart;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

@Service
@EnableAutoConfiguration
public class EmailService {
	@Autowired private JavaMailSender javaMailSender;
	@Autowired private InvoiceService invoiceService;
	@Autowired InsurancePlanService insurancePlanService;
	@Value("${upload.url}")
    private String uploadUrl;

	@Async
	public void sendDriverConfirmation(Driver driver) {
		Address address = driver.getAddress();
		System.out.println(driver.getId());
		System.out.println("email" + driver.getAddress().getEmail());
		//System.out.println("booking.getUser" + booking.getUserEmail());
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			
			byte[] invoiceBytes = invoiceService.generateInvoice(driver);
			MimeBodyPart attachmentPart = new MimeBodyPart();
			attachmentPart.setContent(invoiceBytes, "application/pdf");
			attachmentPart.setFileName("invoice.pdf");
			
			MimeBodyPart textPart = new MimeBodyPart();
			textPart.setContent(this.getEmailBody(driver), "text/html;charset=UTF-8");
			
			
			helper.setTo(address.getEmail());
			helper.setSubject("Thanks for booking!");
			
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(attachmentPart); //pdf
			multipart.addBodyPart(textPart); //text
			
			message.setContent(multipart);
			javaMailSender.send(message);
		} catch (Exception e) {
			System.err.println("Email Sending Exception: " + e.getMessage());
		}
	}
	
	public String getEmailBody(Driver driver) {
	    InsurancePlan insurance = driver.getInsurancePlan();
	    String uploadUrlWithId = uploadUrl + "?driverId=" + driver.getId();
	    String htmlTemplate = """
	            <!DOCTYPE html>
	            <html>
	            <head>
	                <title>Driver Confirmation</title>
	            </head>
	            <body>
	                <h1>Your Driver and Insurance Details</h1>
	                <p>Name: %s</p>
	                <p>Vehicle Value: %s</p>
	                <p>Driving Record: %s</p>
	                <p>Insurance Details:</p>
	                <ul>
	                    <li>Description: %s</li>
	                    <li>Company: %s</li>
	                    <li>Type: %s</li>
	                    <li>Plan Name: %s</li>
	                    <li>Premium: %s</li>
	                    <li>Collision Deductible: %s</li>
	                    <li>Uninsured Motorist Deductible: %s</li>
	                    <p>To upload your documents, please visit: <a href="%s">Upload Documents</a></p>
	                </ul>
	            </body>
	            </html>
	            """.formatted(
	                driver.getName(),
	                driver.getVehicleValue(),
	                driver.getDrivingRecord(),
	                insurance.getDescription(),
	                insurance.getCompany(),
	                insurance.getInsuranceType(),
	                insurance.getPlanName(),
	                insurance.getPremium(),
	                insurance.getCollisionDeductible(),
	                insurance.getUninsuredMotoristDeductible(),
	                uploadUrlWithId
	            );

	    return htmlTemplate;
	}


	}

