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
	
	@Value("${reapply.url}")
    private String reapplyUrl;

	@Async
	public void sendDriverConfirmation(Driver driver) {
		Address address = driver.getAddress();
        System.out.println(driver.getId());
        System.out.println("email" + driver.getAddress().getEmail());
        
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            
       
            helper.setTo(address.getEmail());
            helper.setSubject("Upload documents for verification.");
            helper.setText(getEmailBodyPending(driver), true); 
            
     
            javaMailSender.send(message);
        } catch (Exception e) {
            System.err.println("Email Sending Exception: " + e.getMessage());
        }
    }
	
	@Async
	public void sendDriverApproval(Driver driver) {
		Address address = driver.getAddress();
		System.out.println(driver.getId());
		System.out.println("email" + driver.getAddress().getEmail());
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			
			byte[] invoiceBytes = invoiceService.generateInvoice(driver);
			MimeBodyPart attachmentPart = new MimeBodyPart();
			attachmentPart.setContent(invoiceBytes, "application/pdf");
			attachmentPart.setFileName("invoice.pdf");
			
			MimeBodyPart textPart = new MimeBodyPart();
			textPart.setContent(this.getEmailBodyApproved(driver), "text/html;charset=UTF-8");
			
			
			helper.setTo(address.getEmail());
			helper.setSubject("Congratulations, you have been approved!");
			
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(attachmentPart); //pdf
			multipart.addBodyPart(textPart); //text
			
			message.setContent(multipart);
			javaMailSender.send(message);
		} catch (Exception e) {
			System.err.println("Email Sending Exception: " + e.getMessage());
		}
	}
	
	@Async
	public void sendEmailBodyClaimApproval(Driver driver, Long coverageAmount) {
		if(coverageAmount instanceof Long) {
			System.out.println("coverageAmount is Long");
		}
		Address address = driver.getAddress();
        System.out.println(driver.getId());
        System.out.println("email" + driver.getAddress().getEmail());
        
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            
          
            helper.setTo(address.getEmail());
            helper.setSubject("Congratulaions! Your insurance claim has been approved.");
            System.out.println("First");
            helper.setText(getEmailBodyClaimApproval(driver, coverageAmount), true); 
            System.out.println("Last");
           
            javaMailSender.send(message);
        } catch (Exception e) {
            System.err.println("Email Sending Exception: " + e.getMessage());
        }
	}
	
	@Async
	public void sendEmailBodyClaimRejection(Driver driver, Long coverageAmount) {
		Address address = driver.getAddress();
        System.out.println(driver.getId());
        System.out.println("email" + driver.getAddress().getEmail());
        
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            
          
            helper.setTo(address.getEmail());
            helper.setSubject("Unforunately! Your insurance claim has been rejected.");
            helper.setText(getEmailBodyClaimRejection(driver, coverageAmount), true); 
            
           
            javaMailSender.send(message);
        } catch (Exception e) {
            System.err.println("Email Sending Exception: " + e.getMessage());
        }
	}
	
	@Async
	public void sendDriverRejection(Driver driver) {
		Address address = driver.getAddress();
        System.out.println(driver.getId());
        System.out.println("email" + driver.getAddress().getEmail());
        
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            
          
            helper.setTo(address.getEmail());
            helper.setSubject("Unforunately, your application has been rejected.");
            helper.setText(getEmailBodyRejected(driver), true); 
            
           
            javaMailSender.send(message);
        } catch (Exception e) {
            System.err.println("Email Sending Exception: " + e.getMessage());
        }
	}
	
	public String getEmailBodyPending(Driver driver) {
	    //InsurancePlan insurance = driver.getInsurancePlan();
	    String uploadUrlWithId = uploadUrl + "?driverId=" + driver.getId();
	    
	    String htmlTemplate = """
	    	    <!DOCTYPE html>
	    	    <html>
	    	    <head>
	    	        <title>Upload Documents</title>
	    	    </head>
	    	    <body>
	    	        <h1>Upload Your Documents</h1>
	    	        <p>To upload your documents, please visit: <a href="%s">Upload Documents</a></p>
	    	    </body>
	    	    </html>
	    	    """.formatted(uploadUrlWithId);

	    	return htmlTemplate;

	}
	
	public String getEmailBodyApproved(Driver driver) {
	    InsurancePlan insurance = driver.getInsurancePlan();
	    String htmlTemplate = """
	            <!DOCTYPE html>
	            <html>
	            <head>
	                <title>Your insurance has been approved!</title>
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
	                insurance.getUninsuredMotoristDeductible()
	            );

	    return htmlTemplate;
	}
	
	public String getEmailBodyRejected(Driver driver) {
	    String htmlTemplate = """
	    	    <!DOCTYPE html>
	    	    <html>
	    	    <head>
	    	        <title>Your insurance application has been rejected.</title>
	    	    </head>
	    	    <body>
	    	        <h1>Your Insurance Application Status</h1>
	    	        <p>We regret to inform you that your insurance application has been rejected.</p>
	    	        <p>To reapply for insurance, please visit: <a href="%s">Reapply for Insurance</a></p>
	    	    </body>
	    	    </html>
	    	    """.formatted(reapplyUrl);

	    	return htmlTemplate;
	}
	
	public String getEmailBodyClaimApproval(Driver driver, Long coverageAmount) {
	    String htmlTemplate = """
	        <!DOCTYPE html>
	        <html>
	        <head>
	            <title>Your insurance claim has been approved!</title>
	        </head>
	        <body>
	            <h1>Your Insurance Claim Approval</h1>
	            <p>We are pleased to inform you that your insurance claim has been approved.</p>
	            <p>The insurance company is willing to cover an amount of $%.2f.</p>
	            <p>Thank you for choosing our insurance services.</p>
	        </body>
	        </html>
	        """.formatted((double) coverageAmount);
	    System.out.println("htmlTemplate" + htmlTemplate);
	    return htmlTemplate;
	}

	
	public String getEmailBodyClaimRejection(Driver driver, Long coverageAmount) {
	    String htmlTemplate = """
	        <!DOCTYPE html>
	        <html>
	        <head>
	            <title>Your insurance claim has been rejected!</title>
	        </head>
	        <body>
	            <h1>Your Insurance Claim Rejection</h1>
	            <p>We regret to inform you that your insurance claim has been rejected.</p>
	            <p>The company is not willing to cover $%.2f.</p>
	            <p>Please review the policy terms and conditions for further details.</p>
	        </body>
	        </html>
	        """.formatted((double) coverageAmount);

	    return htmlTemplate;
	}


	}

