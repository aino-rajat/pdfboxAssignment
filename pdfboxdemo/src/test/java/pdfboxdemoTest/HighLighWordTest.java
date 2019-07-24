package pdfboxdemoTest;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import pdfboxdemo.HighLightWords;

public class HighLighWordTest {

	private HighLightWords highLight;
	private String inputPath="src/test/resources/pdfFiles/DownloadBlob.pdf";
			// "demoAddingText.pdf";
	
	@Test
	public void test() throws IOException {
		HighLightWords highLight=new HighLightWords();
		File inputFile=new File(inputPath);
		highLight.HighLightWordsCordinates(inputFile);
	}

}
