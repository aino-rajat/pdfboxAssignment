/**
 * 
 */
package pdfboxdemo;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

/**
 * @author intern3
 *
 */
public class CreateDocumnet  {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException{
		PDDocument document=new PDDocument(); 	//Creates the doc.
		
		
		PDPage mypage=new PDPage();				//creates the page in doc.
		document.addPage(mypage);				//Adding the page in the doc.
		document.save("src/test/resources/pdfFiles/demo.pdf");
		
		document.close();
		System.out.println("Pdf created !");
	}

}
