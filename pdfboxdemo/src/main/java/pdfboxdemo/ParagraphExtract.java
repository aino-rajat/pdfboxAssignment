package pdfboxdemo;

import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

public class ParagraphExtract {
	Logger mylogger=Logger.getLogger(this.getClass().getName());
	public Boolean checkFile(File file) {
		if (file == null || file.exists()==false) {
			return false;
		}

		try {
			PDDocument document=PDDocument.load(file);
			int noOfpages=document.getNumberOfPages();		//Count the no. of pages in the doc.
			mylogger.info(" Total no. of pages: " +noOfpages);
			paragraphCount(file);
			getPdfAsTextFromArea(file);
			return true;
		}	
		catch (Exception e) {
			mylogger.log(Level.INFO,"Exception occured while Character Count",e);
		}

		return false;
	}
	private Boolean paragraphCount(File file) {
		try {
			int paragraphCount = 0; 
			PDFTextStripper pdfStripper = new PDFTextStripper();
			PDDocument doc = PDDocument.load(file);
			String text = pdfStripper.getText(doc);
			pdfStripper.setParagraphStart("/t");
			pdfStripper.setSortByPosition(true);
			for (String line: pdfStripper.getText(doc).split(pdfStripper.getParagraphStart()))
            {
				mylogger.log(Level.INFO, "Paragraph in Document:" +line);
               
            }

			return true;
		} catch (Exception e) {
			mylogger.log(Level.INFO, " Exception occured while :",e);
		}
		return false;
	}
	public static String getPdfAsTextFromArea(File file) {
		String text="";
		try {
			PDDocument doc = PDDocument.load(file);
			PDFTextStripperByArea textStripper= new PDFTextStripperByArea();
			textStripper.addRegion("region1",new Rectangle2D.Double(215,315,190,121));
			textStripper.extractRegions(doc.getPage(0));
			text=textStripper.getTextForRegion("region1");
			doc.save("src/test/resources/pdfFiles/paragaroughCount.pdf");
			doc.close();
		} catch (InvalidPasswordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return text;
	
	}

}		


