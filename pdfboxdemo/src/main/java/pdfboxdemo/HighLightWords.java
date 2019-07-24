package pdfboxdemo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.logging.Logger;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationTextMarkup;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

public class HighLightWords extends PDFTextStripper{

	Logger mylogger=Logger.getLogger(this.getClass().getName());
	
	public HighLightWords() throws IOException {
		super();
		
	}


		public void HighLightWordsCordinates(File fileName) throws InvalidPasswordException, IOException {
		PDDocument document=null;
				
	  			document = PDDocument.load(fileName);
	            PDFTextStripper stripper = new HighLightWords();
	            stripper.setSortByPosition( true );

	            stripper.setStartPage( 0 );
	            stripper.setEndPage( document.getNumberOfPages() );

	            Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream());
	            stripper.writeText(document, dummy);

	            File file1 = new File("src/test/resources/pdfFiles/HighLightWord.pdf");
	            document.save(file1);
	            document.close();
	            
	        
	    }

	    /**
	     * Override the default functionality of PDFTextStripper.writeString()
	     */

	    @Override
	    protected void writeString(String string, List<TextPosition> textPositions) throws IOException {
	        boolean isFound = false;
	        float posXInit  = 0, 
	              posXEnd   = 0, 
	              posYInit  = 0,
	              posYEnd   = 0,
	              width     = 0, 
	              height    = 0, 
	              fontHeight = 0;	   
	        String[] criteria = {" "};

	        for (int i = 0; i < criteria.length; i++) {
	            if (string.contains(criteria[i])) {
	                isFound = true;
	            } 
	        }
	        if (isFound) {
	            posXInit = textPositions.get(0).getXDirAdj();
	            posXEnd  = textPositions.get(textPositions.size() - 1).getXDirAdj() + textPositions.get(textPositions.size() - 1).getWidth();
	            posYInit = textPositions.get(0).getPageHeight() - textPositions.get(0).getYDirAdj();
	            posYEnd  = textPositions.get(0).getPageHeight() - textPositions.get(textPositions.size() - 1).getYDirAdj();
	            width    = textPositions.get(0).getWidthDirAdj();
	            height   = textPositions.get(0).getHeightDir();

	            mylogger.info(string + "X-Init = " + posXInit + "; Y-Init = " + posYInit + "; X-End = " + posXEnd + "; Y-End = " + posYEnd + "; Font-Height = " + fontHeight);

	            /* numeration is index-based. Starts from 0 */

	            float quadPoints[] = {posXInit, posYEnd + height + 2, posXEnd, posYEnd + height + 2, posXInit, posYInit - 2, posXEnd, posYEnd - 2};

	            List<PDAnnotation> annotations = document.getPage(this.getCurrentPageNo() - 1).getAnnotations();
	            PDAnnotationTextMarkup highlight = new PDAnnotationTextMarkup(PDAnnotationTextMarkup.SUB_TYPE_HIGHLIGHT);

	            PDRectangle position = new PDRectangle();
	            position.setLowerLeftX(posXInit);
	            position.setLowerLeftY(posYEnd);
	            position.setUpperRightX(posXEnd);
	            position.setUpperRightY(posYEnd + height);

	            highlight.setRectangle(position);

	            // quadPoints is array of x,y coordinates in Z-like order (top-left, top-right, bottom-left,bottom-right) 
	            // of the area to be highlighted

	            highlight.setQuadPoints(quadPoints);

	            PDColor yellow = new PDColor(new float[]{1, 1, 1 / 255F}, PDDeviceRGB.INSTANCE);
	            highlight.setColor(yellow);
	            annotations.add(highlight);
	        }
	    }

	}