package pdfboxdemoTest;

import org.junit.BeforeClass;
import org.junit.Test;

import pdfboxdemo.CreateDocumnet;

public class PdfDemoTestCases {

	static CreateDocumnet createDocumnet;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		createDocumnet=new CreateDocumnet();
	}

	@Test
	public void test() {
//		PDDocument result=createDocumnet.getClass(PDDocument);
//		assertTrue(result==());
		
	}

}
