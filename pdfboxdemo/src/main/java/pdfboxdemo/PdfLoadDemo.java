package pdfboxdemo;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;

public class PdfLoadDemo {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File file=new File("/home/intern3/demo2.pdf"); 
		PDDocument document=new PDDocument().load(file);	//Loading the pdf file 
		System.out.println("Pdf file loaded!");
		int noOfpages=document.getNumberOfPages();		//Count the no. of pages in the doc.
		System.out.println("No. of pages in document:"+noOfpages);
		
		
		document.removePage(5); //Removing the page
		document.save("file");
		
		System.out.println("Document created with Removed page !")	;
		
		document.close();

	}

}
