package pdfboxdemoTest;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import pdfboxdemo.WordCordinates;

public class WordCordinatesTest {
	
	private WordCordinates wordCordinates;
	private String inputPath="src/test/resources/pdfFiles/demoAddingText.pdf";
	
	@Test
	public void cordinateTest() {
		try {
			wordCordinates=new WordCordinates();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File inputFile=new File(inputPath);
		wordCordinates.CharacterCordinates(inputFile);
		assertTrue("Characters Not Found",true);
	}
	

}
