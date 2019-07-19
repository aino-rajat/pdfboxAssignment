package pdfboxdemo;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.logging.Logger;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

public class WordCordinates extends PDFTextStripper {
	Logger mylogger=Logger.getLogger(this.getClass().getName());

	public WordCordinates() throws IOException {
		super();
		
	}
	 
		public void CharacterCordinates(File fileName) {
		PDDocument document=null;
      try {
           
			document = PDDocument.load(fileName);
		} catch (InvalidPasswordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
            PDFTextStripper stripper;
			try {
				stripper = new WordCordinates();
				  stripper.setSortByPosition( true );
		            stripper.setStartPage( 0 );
		            stripper.setEndPage( document.getNumberOfPages() );
		            Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream());
		            stripper.writeText(document, dummy);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
          
      }
		
//        finally {
//        	
//            if( document != null ) {
//                try {
//					document.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//            }
//        }
    
    /**
     * Override the default functionality of PDFTextStripper.writeString()
     */
		
    protected void writeString(String string, List<TextPosition> textPositions) throws IOException {
    	File file=new File("src/test/resources/pdfFiles/demo.pdf");
		PDDocument document=PDDocument.load(file);    	
    	PDPage page = new PDPage();
          page=document.getPage(0);
          
    	for (TextPosition text : textPositions) {
            mylogger.info(text.getUnicode()+ " [(X=" + text.getXDirAdj() + ",Y=" +
                    text.getYDirAdj() + ") height=" + text.getHeightDir() + " width=" +
                    text.getWidthDirAdj() + "]");
    	

            PDPageContentStream contentStream = new PDPageContentStream(document,page,AppendMode.APPEND, false);
			contentStream.addRect(text.getXDirAdj(),text.getYDirAdj(),text.getHeightDir(),text.getWidthDirAdj());
			contentStream.setLineWidth(1);
			contentStream.setStrokingColor(Color.BLACK);
			contentStream.stroke();
			contentStream.close();
			document.save("src/test/resources/pdfFiles/CordinateD.pdf");
			document.close();
    	}
    }	
}
