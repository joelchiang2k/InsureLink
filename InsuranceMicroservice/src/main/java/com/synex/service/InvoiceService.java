package com.synex.service;

import java.io.IOException;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.synex.domain.Address;
import com.synex.domain.Driver;
import com.synex.domain.InsurancePlan;
import com.synex.domain.Vehicle;

@Service
public class InvoiceService {
	public byte[] generateInvoice(Driver driver) {
		Document document = new Document();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		try {
			PdfWriter.getInstance(document, outputStream);
			document.open();
			addContent(document, driver);
			document.close();
			
			return outputStream.toByteArray();
		} catch (DocumentException e) {
			e.printStackTrace();
		} finally {
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	private void addContent(Document document, Driver driver) throws DocumentException {
		Address address = driver.getAddress();
		Vehicle vehicle = driver.getVehicle();
		InsurancePlan insurancePlan = driver.getInsurancePlan();
		Font headerFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
		Font regularFont = new Font(Font.FontFamily.TIMES_ROMAN, 12);
		
		Paragraph header = new Paragraph("Invoice", headerFont);
		header.setAlignment(Element.ALIGN_CENTER);
		document.add(header);
		
		document.add(new Paragraph("\n"));
		
		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(100);
		table.setWidths(new float[] {1,2});
		
		PdfPCell cell;
		
		cell = new PdfPCell(new Phrase("Invoice Number:", regularFont));
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(String.valueOf(driver.getId()), regularFont));
		table.addCell(cell);
		
//		cell = new PdfPCell(new Phrase("Invoice Date:", regularFont));
//		table.addCell(cell);
//		cell = new PdfPCell(new Phrase(driver.getUserName(), regularFont));
//		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Customer Name:", regularFont));
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(driver.getName(), regularFont));
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Customer Email:", regularFont));
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(address.getEmail(), regularFont));
		table.addCell(cell);
		
//		cell = new PdfPCell(new Phrase("Customer Mobile:", regularFont));
//		table.addCell(cell);
//		cell = new PdfPCell(new Phrase(booking.getCustomerMobile(), regularFont));
//		table.addCell(cell);
		
//		cell = new PdfPCell(new Phrase("Booking ID:", regularFont));
//		table.addCell(cell);
//		cell = new PdfPCell(new Phrase(String.valueOf(booking.getBookingId()), regularFont));
//		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Vehicle Make:", regularFont));
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(vehicle.getMake(), regularFont));
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Vehicle Model:", regularFont));
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(vehicle.getModel(), regularFont));
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Date:", regularFont));
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(getCurrentDate(), regularFont));
		table.addCell(cell);
//		
//		cell = new PdfPCell(new Phrase("Check-Out Date:", regularFont));
//		table.addCell(cell);
//		cell = new PdfPCell(new Phrase(booking.getCheckOutDate(), regularFont));
//		table.addCell(cell);
//		
//		cell = new PdfPCell(new Phrase("No. of Rooms:", regularFont));
//		table.addCell(cell);
//		cell = new PdfPCell(new Phrase(String.valueOf(booking.getNoRooms()), regularFont));
//		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Plan Name:", regularFont));
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(insurancePlan.getPlanName(), regularFont));
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Company:", regularFont));
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(String.valueOf(insurancePlan.getCompany()), regularFont));
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Price:", regularFont));
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("$" + insurancePlan.getPremium(), regularFont));
		table.addCell(cell);
		
		
		
		
		
		table.addCell(cell);
		
		document.add(table);
		
		document.add(new Paragraph("\n"));
		
		Paragraph totalSavings = new Paragraph("Total Amount: $" + insurancePlan.getPremium(), headerFont);
		totalSavings.setAlignment(Element.ALIGN_RIGHT);
		document.add(totalSavings);
	}
	
	private String getCurrentDate() {
		java.util.Date today = new java.util.Date();
		return today.toString();
	}
}
