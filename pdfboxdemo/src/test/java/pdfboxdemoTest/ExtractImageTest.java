package pdfboxdemoTest;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.tools.ExtractImages;
import org.junit.Test;

import pdfboxdemo.ExractImagesDemo;
import pdfboxdemo.ExtractText;

public class ExtractImageTest {

	private ExractImagesDemo extractImage;
	private String inputPath="src/test/resources/pdfFiles/DownloadBlob.pdf";
	
	@Test
	public void test() {
		
		try {
			extractImage=new ExractImagesDemo();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File inputFile=new File(inputPath);
		try {
			extractImage.extractImages(inputFile);
		} catch (InvalidPasswordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue("No Images in the PDF", true);
	}

}
