package pdfboxdemo;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;

public class BorderPdf {
	
	Logger mylogger=Logger.getLogger(this.getClass().getName());
	
	public Boolean checkFile(File file) {
		if(file==null || file.exists()==false) {
			return false;
		}
		try {
			PDDocument document=PDDocument.load(file);
			int noOfpages=document.getNumberOfPages();
			mylogger.info("Number of pages in Document:" +noOfpages);
			Boolean iresult=boderDocumentImage(file);
				borderParagraph(file);
			return true;
		} catch (InvalidPasswordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	private Boolean boderDocumentImage(File file) {
		
		try {
			PDDocument document=PDDocument.load(file);
			PDPage page = new PDPage();
			//int noOfpages=document.getNumberOfPages();
			page=document.getPage(0);

			
			PDPageContentStream contentStream = new PDPageContentStream(document,page,AppendMode.APPEND, false);
			contentStream.addRect(100,910,720,300);
			contentStream.setLineWidth(1);
			contentStream.setStrokingColor(Color.BLACK);
			contentStream.stroke();
			contentStream.close();
			document.save("src/test/resources/pdfFiles/BorderedPDF.pdf");
			document.close();
			return true;
			
		} catch (InvalidPasswordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	
	private Boolean borderParagraph(File file) {
		try {
			
			PDFTextStripper pdfStripper = new PDFTextStripper();
			PDDocument document=PDDocument.load(file);
			PDPage page = new PDPage();
			pdfStripper.setParagraphStart("/t");
			pdfStripper.setSortByPosition(true);
			for (String paragraph: pdfStripper.getText(document).split(pdfStripper.getParagraphStart()))
            {
			//int noOfpages=document.getNumberOfPages();
			page=document.getPage(0);

            }
			
			PDPageContentStream contentStream = new PDPageContentStream(document,page,AppendMode.APPEND, false);
			contentStream.addRect(250,250,720,300);
			contentStream.setLineWidth(1);
			contentStream.setStrokingColor(Color.BLACK);
			contentStream.stroke();
			contentStream.close();
			document.save("src/test/resources/pdfFiles/BorderedPDF.pdf");
			document.close();
			return true;
			
		} catch (InvalidPasswordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}
	

}
