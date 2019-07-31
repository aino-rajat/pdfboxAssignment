package pdfboxdemo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

public class ParagraphExtract extends PDFTextStripper {
	public ParagraphExtract() throws IOException {
		super();
		// TODO Auto-generated constructor stub
	}

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
	private Boolean paragraphCount(File file) throws FileNotFoundException, IOException {
		extractTextPosition(file);
		try {
			int paragraphCount = 0; 
			 StringBuilder sb = new StringBuilder();
			
			PDDocument doc = PDDocument.load(file);
			PDFTextStripper pdfStripper = new PDFTextStripper();
			String text = pdfStripper.getText(doc);
			
		
			pdfStripper.setParagraphStart("/t");
			pdfStripper.setSortByPosition(true);
			for (String line: pdfStripper.getText(doc).split(pdfStripper.getParagraphStart()))
			{
				 

				//line.length();
				mylogger.log(Level.INFO,"Paragraph length:" +line.length());
				
				mylogger.log(Level.INFO, "Paragraph in Document:" +line);
               
            }

			return true;
		} catch (Exception e) {
			mylogger.log(Level.INFO, " Exception occured while :",e);
		}
		return false;
	}
	
	 private void extractTextPosition(File file) throws FileNotFoundException, IOException {
		 PDDocument doc = PDDocument.load(file);
		    PDFParser parser = new PDFParser((RandomAccessRead) file);
		    parser.parse();
		    StringWriter outString = new StringWriter();
		    PDFTextStripper stripper = new ParagraphExtract();
		    stripper.writeText(parser.getPDDocument(), outString);
		    List<List<TextPosition>> vectorlistoftps = stripper.getArticleStart();
		    for (int i = 0; i < vectorlistoftps.size(); i++) {
		        List<TextPosition> tplist = vectorlistoftps.get(i);
		        for (int j = 0; j < tplist.size(); j++) {
		            TextPosition text = tplist.get(j);
		            System.out.println(" String "
		          + "[x: " + text.getXDirAdj() + ", y: "
		          + text.getY() + ", height:" + text.getHeightDir()
		          + ", space: " + text.getWidthOfSpace() + ", width: "
		          + text.getWidthDirAdj() + ", yScale: " + text.getYScale() + "]");
		          
		        }       
		    }
//		    String[] lines = stripper.getText(doc).split(stripper.getParagraphStart());
//		    for (int i = 0; i<lines.length; i++) {
//		    	 Integer tplist =  lines.length;
//		        for (int j = 0; j < tplist; j++) {
//		            TextPosition text = tplist.toString();
//		            System.out.println(" String "
//		          + "[x: " + text.getXDirAdj() + ", y: "
//		          + text.getY() + ", height:" + text.getHeightDir()
//		          + ", space: " + text.getWidthOfSpace() + ", width: "
//		          + text.getWidthDirAdj() + ", yScale: " + text.getYScale() + "]"
//		          + text.getCharacter());
//		        }       
//		    }	
	 }
	

}		


