package pdfboxdemo;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class CharacterCountPdf {
	
	Logger mylogger=Logger.getLogger(this.getClass().getName());

	public Boolean checkFile(File file) {
		if (file == null || file.exists()==false) {
		      return false;
		    }

		 try {
		PDDocument document=PDDocument.load(file);
		int noOfpages=document.getNumberOfPages();		//Count the no. of pages in the doc.
		mylogger.info(" Total no. of pages: " +noOfpages);
		 Boolean iresult=countCharacter(file);
		}	
		 catch (Exception e) {
			mylogger.log(Level.INFO,"Exception occured while Character Count",e);
		}
	
		return true;
	}

	private Boolean countCharacter(File file) {
		try {
			int countCharcter = 0;
			PDFTextStripper pdfStripper = new PDFTextStripper();
			PDDocument doc = PDDocument.load(file);
			String text = pdfStripper.getText(doc);
			if(!(text.equals(""))) 
				countCharcter += text.length(); 
			mylogger.log(Level.INFO, " Total Number of Characters in Document:" + countCharcter);
			return true;
		} catch (Exception e) {
			mylogger.log(Level.INFO, " Exception occured while :",e);
		}
		return false;
	}
}
