package pdfboxdemoTest;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import pdfboxdemo.CropPdf;

public class CropTest  {
	private CropPdf cropPdfObject;
	private File inputFile=new File("src/test/resources/pdfFiles/DownloadBlob.pdf");
	private String outputPath="src/test/resources/pdfFiles/CropedDownloadBlob.pdf";
	
	@Test
	public void cropPdfTest() throws IOException {
	cropPdfObject=new CropPdf();
	Boolean isCropped=cropPdfObject.cropPdf(inputFile,outputPath);
	assertTrue("PDF not cropped", isCropped);
	
}
}