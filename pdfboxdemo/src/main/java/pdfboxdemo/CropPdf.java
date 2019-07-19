package pdfboxdemo;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

public class CropPdf {
	Logger mylogger=Logger.getLogger(this.getClass().getName());
	
	//This function accepts the pdf file as input parameter and crops the pdf and return the file.
	public Boolean cropPdf(File file,String destinationPath) throws IOException {
		try {
			PDDocument doc = PDDocument.load(file);
			PDPage page = doc.getPage(0);
			page.setCropBox(new PDRectangle(20, 20, 200, 400));
			doc.save(destinationPath);
			doc.close();
			return true;
		}
		catch (Exception e) {
			mylogger.log(Level.INFO,"exception occured while cropping:",e);
		}
		return false;
	}

}
	
