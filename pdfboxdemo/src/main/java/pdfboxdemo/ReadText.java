package pdfboxdemo;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class ReadText {

	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		File file=new File("/home/intern3/demo1new.pdf");
		PDDocument document=PDDocument.load(file);
		
		  //Instantiate PDFTextStripper class
	      PDFTextStripper pdfStripper = new PDFTextStripper();	//class extracts all the text from the given PDF document.
	      String text=pdfStripper.getText(document);
	      System.out.println(text);
	      document.save(file);
		document.close();
	}

}
