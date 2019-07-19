package pdfboxdemo;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class InsertImage {

	public static void main(String[] args) throws InvalidPasswordException, IOException {
		// TODO Auto-generated method stub
		File file=new File("/home/intern3/demo1new.pdf");
		PDDocument document=PDDocument.load(file);
		PDPage page=document.getPage(1);
		PDImageXObject pdImage = PDImageXObject.createFromFile("/home/intern3/logo.png", document);
		PDPageContentStream contentStream=new PDPageContentStream(document,page);
		contentStream.drawImage(pdImage, 70, 250);
		contentStream.close();
		document.save(file);
		document.close();
	}

}
