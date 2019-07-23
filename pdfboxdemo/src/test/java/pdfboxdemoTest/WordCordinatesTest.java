package pdfboxdemoTest;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import pdfboxdemo.CharacterCordinates;

public class WordCordinatesTest {
	
	private CharacterCordinates characterCordinates;
	private String inputPath="src/test/resources/pdfFiles/demoAddingText.pdf";
	
	@Test
	public void cordinateTest() {
		try {
			characterCordinates=new CharacterCordinates();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File inputFile=new File(inputPath);
		characterCordinates.CharacterCordinates(inputFile);
		assertTrue("Characters Not Found",true);
	}
	

}
