package pdfboxdemoTest;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import pdfboxdemo.ParagraphExtract;

public class ParagraphCountTest {
	private ParagraphExtract paragraphExtract;
	private String inputPath="src/test/resources/pdfFiles/DownloadBlob.pdf";
	
	@Test
	public void test() {
	paragraphExtract=new ParagraphExtract();
	File inputFile=new File(inputPath);
	paragraphExtract.checkFile(inputFile);
	assertTrue("Documnet does contain Paragraph",true);
	}

	
}
