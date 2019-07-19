package pdfboxdemo;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class ParagraphCount {
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

}		


