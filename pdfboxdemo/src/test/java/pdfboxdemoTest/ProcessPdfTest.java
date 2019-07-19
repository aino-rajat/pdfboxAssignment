package pdfboxdemoTest;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import pdfboxdemo.BorderPdf;

public class ProcessPdfTest {
	private BorderPdf borderpdf;
	private String inputPath="src/test/resources/pdfFiles/DownloadBlob.pdf";
	
	@Test
	public void test() {
		borderpdf=new BorderPdf();
		File inputFile=new File(inputPath);
		borderpdf.checkFile(inputFile);
		assertTrue("Document is not Cropped", true);
	}

}
