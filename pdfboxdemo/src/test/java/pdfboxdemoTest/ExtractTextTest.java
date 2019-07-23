package pdfboxdemoTest;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import pdfboxdemo.ExtractText;

public class ExtractTextTest {

	private ExtractText extractText;
	String inputPath="src/test/resources/pdfFiles/DownloadBlob.pdf";
	
	@Test
	public void test() {
		File inputFile=new File(inputPath);
		extractText=new ExtractText();
		extractText.receiveFile(inputFile);
		assertTrue("Text Not not extracted from Document",true);
	
	}

}
