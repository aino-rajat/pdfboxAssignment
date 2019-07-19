package pdfboxdemo;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionJavaScript;

public class AddJavascript {

	public static void main(String[] args) throws InvalidPasswordException, IOException {
		// TODO Auto-generated method stub
		File file=new File("/home/intern3/demo1new.pdf");
		PDDocument document=PDDocument.load(file);
		String javaScript = "app.alert( {cMsg: 'this is an example', nIcon: 3,"
		         + " nType: 0, cTitle: 'PDFBox Javascript example’} );";
		
		//Creating PDActionJavaScript object 
	      PDActionJavaScript PDAjavascript = new PDActionJavaScript(javaScript);
	    
	      //Embedding java script
	      document.getDocumentCatalog().setOpenAction(PDAjavascript);
	      document.save( new File("/home/intern3/new.pdf"));
	      System.out.println("Data added to the given PDF"); 
	      document.close();
	}

}
