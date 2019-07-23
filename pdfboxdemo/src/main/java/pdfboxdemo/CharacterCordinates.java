package pdfboxdemo;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.logging.Logger;

import org.apache.fontbox.util.BoundingBox;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType3Font;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

public class CharacterCordinates extends PDFTextStripper {
	Logger mylogger=Logger.getLogger(this.getClass().getName());
	
	private AffineTransform flipAT;
	 private AffineTransform rotateAT;
	 private Graphics2D g2d;
	public CharacterCordinates() throws IOException {
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
				stripper = new CharacterCordinates();
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
		
 
    /**
     * Override the default functionality of PDFTextStripper.writeString()
     * @return 
     */

//writeString method receives a line of text as the first argument. writeString method is        	//	called   
 // for each line of text in the PDF document.		
    protected void writeString(String string, List<TextPosition> textPositions) throws IOException {
    	
    	File file=new File("src/test/resources/pdfFiles/demoAddingText.pdf");
		
    	PDDocument document=PDDocument.load(file);    	
    	PDPage page = new PDPage();
          page=document.getPage(0);
          
    	for (TextPosition text : textPositions) {
            mylogger.info(text.getUnicode()+ " [(X=" + text.getXDirAdj() + ",Y=" +
                    text.getYDirAdj() + ") height=" + text.getHeightDir() + " width=" +
                    text.getWidthDirAdj() +  " FontSize=" + text.getFontSize() + " xscale="
                    + text.getXScale() + " space="
    	                    + text.getWidthOfSpace() +
                    "]");
         
            AffineTransform at = text.getTextMatrix().createAffineTransform();
           
            Rectangle2D.Float rect = new Rectangle2D.Float(text.getXDirAdj(),  text.getYDirAdj(), 
                    text.getWidthDirAdj() / text.getTextMatrix().getScalingFactorX(),
                    text.getHeightDir() / text.getTextMatrix().getScalingFactorY());
           
            Shape s = at.createTransformedShape(rect);
            s = flipAT.createTransformedShape(s);
           
			s = rotateAT.createTransformedShape(s);
            g2d.setColor(Color.red);
            g2d.draw(s);

            // in blue:
            // show rectangle with the real vertical bounds, based on the font bounding box y values
            // usually, the height is identical to what you see when marking text in Adobe Reader
            PDFont font = text.getFont();
            BoundingBox bbox = font.getBoundingBox();

            // advance width, bbox height (glyph space)
            float xadvance = font.getWidth(text.getCharacterCodes()[0]); // todo: should iterate all chars
            rect = new Rectangle2D.Float(0, bbox.getLowerLeftY(), xadvance, bbox.getHeight());
            
            if (font instanceof PDType3Font)
            {
                // bbox and font matrix are unscaled
                at.concatenate(font.getFontMatrix().createAffineTransform());
            }
            else
            {
                // bbox and font matrix are already scaled to 1000
                at.scale(1/1000f, 1/1000f);
            }
            s = at.createTransformedShape(rect);
            s = flipAT.createTransformedShape(s);
            s = rotateAT.createTransformedShape(s);

            g2d.setColor(Color.blue);
            g2d.draw(s);
           

           
    	}
    	document.close();
    }	
}
