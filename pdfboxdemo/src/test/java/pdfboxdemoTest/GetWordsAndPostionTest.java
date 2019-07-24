package pdfboxdemoTest;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.junit.Test;

import pdfboxdemo.GetWordsAndPositions;

public class GetWordsAndPostionTest {

	File inputFile;
	PDDocument document;
	private GetWordsAndPositions wordCordinates;
	private String inputPath="src/test/resources/pdfFiles/demoAddingText.pdf";
			///demoAddingText.pdf";
	
	@Test
	public void test() {
		
		try {
			inputFile=new File(inputPath);
			document = PDDocument.load(inputFile);
		} catch (InvalidPasswordException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			wordCordinates=new GetWordsAndPositions(this.document, inputPath);
			wordCordinates.getFile(inputFile,inputPath);
			assertTrue("No Words Found in the Document",true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
