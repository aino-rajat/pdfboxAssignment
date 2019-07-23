package pdfboxdemo;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

public class ExtractText {
	Logger mylogger=Logger.getLogger(this.getClass().getName());

	public Boolean receiveFile(File inputFile) {

		if(inputFile==null || (inputFile.exists()==false)) {
			return false;
		}

		try {
			PDDocument document=PDDocument.load(inputFile);
			 PDFTextStripperByArea stripper = new PDFTextStripperByArea();
             stripper.setSortByPosition( true );
             Rectangle2D rect = new Rectangle( 10, 280, 275, 60 );
             stripper.addRegion( "class1", rect );
             PDPage firstPage = document.getPage(0);
             stripper.extractRegions( firstPage );
             System.out.println( "Text in the area:" + rect );
             System.out.println( stripper.getTextForRegion("class1" ) );
			document.save("src/test/resources/pdfFiles/markerdText.pdf");
			document.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;



	}



}
