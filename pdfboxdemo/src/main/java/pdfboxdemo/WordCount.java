package pdfboxdemo;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;

public class WordCount {
	Logger mylogger=Logger.getLogger(this.getClass().getName());
	
	
	public boolean checkFile(File file)  {
			PDDocument document = null;
			try {
				document = PDDocument.load(file);
			} catch (InvalidPasswordException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (file == null || file.exists()==false) {
			      return false;
			    }

			 try {
			
			int noOfpages=document.getNumberOfPages();		//Count the no. of pages in the doc.
			mylogger.info(" Total no. of pages: " +noOfpages);
			 Boolean iresult=countwords(file);
			

			}	
		
		catch (Exception e) {
			mylogger.log(Level.INFO,"Exception occured while counting words:",e);
		}
		return true;
		
	}			
	

	Boolean countwords(File file)  {
		try {
			int countWord = 0;
			PDFTextStripper pdfStripper = new PDFTextStripper();
			PDDocument doc = PDDocument.load(file);
			String text = pdfStripper.getText(doc); //class extracts all the text from the given PDF document.
			String[] wordList = text.split("\\s+");
			countWord += wordList.length;
			mylogger.log(Level.INFO, " Total Number of Words in Document:" + countWord);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return true;

		}	
	}


	
				
