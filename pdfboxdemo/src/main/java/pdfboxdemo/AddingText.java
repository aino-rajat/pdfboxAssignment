package pdfboxdemo;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class AddingText {

	public static void main(String[] args)throws IOException {
		// TODO Auto-generated method stub

		File file=new File("src/test/resources/pdfFiles/demo.pdf");
		PDDocument document=PDDocument.load(file);
		
		PDPage mypage=document.getPage(0);
		PDPageContentStream contentstream=new PDPageContentStream(document, mypage);
		
		contentstream.beginText();
		contentstream.setFont(PDType1Font.TIMES_ROMAN, 16);
		contentstream.setLeading(14.5f);
		contentstream.newLineAtOffset(25,725);
		String text="Hello World";
		// String text2 = "as we want like this using the ShowText()  method of the";
		
		 contentstream.showText(text);
		 contentstream.newLine();
		// contentstream.showText(text2);
		contentstream.endText();
		System.out.println("Data added in document");
		contentstream.close();
		document.save("src/test/resources/pdfFiles/demoAddingText.pdf");
		document.close();
		
	}

}
