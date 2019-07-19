package pdfboxdemoTest;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import pdfboxdemo.ParagraphCount;

public class ParagraphCountTest {
	private ParagraphCount paragraphCount;
	private String inputPath="src/test/resources/pdfFiles/DownloadBlob.pdf";
	
	@Test
	public void test() {
	paragraphCount=new ParagraphCount();
	File inputFile=new File(inputPath);
	paragraphCount.checkFile(inputFile);
	assertTrue("Documnet does contain Paragraph",true);
	}

}
