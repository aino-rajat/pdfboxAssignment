package pdfboxdemo;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.apache.fontbox.util.BoundingBox;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.font.PDCIDFontType2;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDSimpleFont;
import org.apache.pdfbox.pdmodel.font.PDTrueTypeFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType3CharProc;
import org.apache.pdfbox.pdmodel.font.PDType3Font;
import org.apache.pdfbox.pdmodel.font.PDVectorFont;
import org.apache.pdfbox.pdmodel.interactive.pagenavigation.PDThreadBead;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
import org.apache.pdfbox.util.Matrix;
import org.apache.pdfbox.util.Vector;

public class GetWordsAndPositions extends PDFTextStripper{

	static Logger mylogger=Logger.getLogger(GetWordsAndPositions.class.getName());
	   private BufferedImage image;
	private AffineTransform flipAT;
	 private AffineTransform rotateAT;
	  private AffineTransform transAT;
	 private final String filename;
	    static final int SCALE = 4;
	 private Graphics2D g2d;
	
	static List<String> words = new ArrayList<String>();
	
	public GetWordsAndPositions(PDDocument document, String inputPath) throws IOException {
	this.document=document;
	this.filename=inputPath;
	}
	


	public Boolean getFile(File inputFile,String inputPath) throws IOException {
		if(inputFile==null || inputFile.exists()==false) {
			return false;
		}
			try {
				document = PDDocument.load(inputFile);
			} catch (InvalidPasswordException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			GetWordsAndPositions stripper = new GetWordsAndPositions(document,inputPath);
			stripper.setSortByPosition( true );
			 for (int page = 0; page < document.getNumberOfPages(); ++page)
             {
                 stripper.stripPage(page);
             }
//			stripper.setStartPage( 0 );
//			stripper.setEndPage( document.getNumberOfPages() );
//			Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream());
//			stripper.writeText(document, dummy);
//
//			// print words
//			for(String word:words){
//				mylogger.info(word); 
//			}
			return true;
			
		}
	
    @Override
    protected void showGlyph(Matrix textRenderingMatrix, PDFont font, int code, String unicode, Vector displacement) throws IOException
    {
        super.showGlyph(textRenderingMatrix, font, code, unicode, displacement);

        // in cyan:
        // show actual glyph bounds. This must be done here and not in writeString(),
        // because writeString processes only the glyphs with unicode, 
        // see e.g. the file in PDFBOX-3274
        Shape cyanShape = calculateGlyphBounds(textRenderingMatrix, font, code);

        if (cyanShape != null)
        {
            cyanShape = flipAT.createTransformedShape(cyanShape);
            cyanShape = rotateAT.createTransformedShape(cyanShape);
            cyanShape = transAT.createTransformedShape(cyanShape);

            g2d.setColor(Color.CYAN);
            g2d.draw(cyanShape);
        }
    }

    // this calculates the real (except for type 3 fonts) individual glyph bounds
    private Shape calculateGlyphBounds(Matrix textRenderingMatrix, PDFont font, int code) throws IOException
    {
        GeneralPath path = null;
        AffineTransform at = textRenderingMatrix.createAffineTransform();
        at.concatenate(font.getFontMatrix().createAffineTransform());
        if (font instanceof PDType3Font)
        {
            // It is difficult to calculate the real individual glyph bounds for type 3 fonts
            // because these are not vector fonts, the content stream could contain almost anything
            // that is found in page content streams.
            PDType3Font t3Font = (PDType3Font) font;
            PDType3CharProc charProc = t3Font.getCharProc(code);
            if (charProc != null)
            {
                BoundingBox fontBBox = t3Font.getBoundingBox();
                PDRectangle glyphBBox = charProc.getGlyphBBox();
                if (glyphBBox != null)
                {
                    // PDFBOX-3850: glyph bbox could be larger than the font bbox
                    glyphBBox.setLowerLeftX(Math.max(fontBBox.getLowerLeftX(), glyphBBox.getLowerLeftX()));
                    glyphBBox.setLowerLeftY(Math.max(fontBBox.getLowerLeftY(), glyphBBox.getLowerLeftY()));
                    glyphBBox.setUpperRightX(Math.min(fontBBox.getUpperRightX(), glyphBBox.getUpperRightX()));
                    glyphBBox.setUpperRightY(Math.min(fontBBox.getUpperRightY(), glyphBBox.getUpperRightY()));
                    path = glyphBBox.toGeneralPath();
                }
            }
        }
        else if (font instanceof PDVectorFont)
        {
            PDVectorFont vectorFont = (PDVectorFont) font;
            path = vectorFont.getPath(code);

            if (font instanceof PDTrueTypeFont)
            {
                PDTrueTypeFont ttFont = (PDTrueTypeFont) font;
                int unitsPerEm = ttFont.getTrueTypeFont().getHeader().getUnitsPerEm();
                at.scale(1000d / unitsPerEm, 1000d / unitsPerEm);
            }
            if (font instanceof PDType0Font)
            {
                PDType0Font t0font = (PDType0Font) font;
                if (t0font.getDescendantFont() instanceof PDCIDFontType2)
                {
                    int unitsPerEm = ((PDCIDFontType2) t0font.getDescendantFont()).getTrueTypeFont().getHeader().getUnitsPerEm();
                    at.scale(1000d / unitsPerEm, 1000d / unitsPerEm);
                }
            }
        }
        else if (font instanceof PDSimpleFont)
        {
            PDSimpleFont simpleFont = (PDSimpleFont) font;

            // these two lines do not always work, e.g. for the TT fonts in file 032431.pdf
            // which is why PDVectorFont is tried first.
            String name = simpleFont.getEncoding().getName(code);
            path = simpleFont.getPath(name);
        }
        else
        {
            // shouldn't happen, please open issue in JIRA
            System.out.println("Unknown font class: " + font.getClass());
        }
        if (path == null)
        {
            return null;
        }
        return at.createTransformedShape(path.getBounds2D());
    }
	
	private void stripPage(int page) throws IOException{
		PDFRenderer pdfRenderer = new PDFRenderer(document);
        image = pdfRenderer.renderImage(page, SCALE);
        
        PDPage pdPage = document.getPage(page);
        PDRectangle cropBox = pdPage.getCropBox();

        // flip y-axis
        flipAT = new AffineTransform();
        flipAT.translate(0, pdPage.getBBox().getHeight());
        flipAT.scale(1, -1);

        // page may be rotated
        rotateAT = new AffineTransform();
        int rotation = pdPage.getRotation();
        if (rotation != 0)
        {
            PDRectangle mediaBox = pdPage.getMediaBox();
            switch (rotation)
            {
                case 90:
                    rotateAT.translate(mediaBox.getHeight(), 0);
                    break;
                case 270:
                    rotateAT.translate(0, mediaBox.getWidth());
                    break;
                case 180:
                    rotateAT.translate(mediaBox.getWidth(), mediaBox.getHeight());
                    break;
                default:
                    break;
            }
            rotateAT.rotate(Math.toRadians(rotation));
        }

        // cropbox
        transAT = AffineTransform.getTranslateInstance(-cropBox.getLowerLeftX(), cropBox.getLowerLeftY());

        g2d = image.createGraphics();
        g2d.setStroke(new BasicStroke(0.1f));
        g2d.scale(SCALE, SCALE);

        setStartPage(page + 1);
        setEndPage(page + 1);

        Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream());
        writeText(document, dummy);
        
        // beads in green
        g2d.setStroke(new BasicStroke(0.4f));
        List<PDThreadBead> pageArticles = pdPage.getThreadBeads();
        for (PDThreadBead bead : pageArticles)
        {
            if (bead == null)
            {
                continue;
            }
            PDRectangle r = bead.getRectangle();
            Shape s = r.toGeneralPath().createTransformedShape(transAT);
            s = flipAT.createTransformedShape(s);
            s = rotateAT.createTransformedShape(s);
            g2d.setColor(Color.green);
            g2d.draw(s);
        }

        g2d.dispose();

        String imageFilename = filename;
        int pt = imageFilename.lastIndexOf('.');
        imageFilename = imageFilename.substring(0, pt) + "-marked-" + (page + 1) + ".png";
        ImageIO.write(image, "png", new File(imageFilename));
		
	}
	
	/**
	 * Override the default functionality of PDFTextStripper.writeString()
	 */
	@Override
	protected void writeString(String str, List<TextPosition> textPositions) throws IOException {
		//        String[] wordsInStream = str.split(getWordSeparator());
		//        if(wordsInStream!=null){
		//            for(String word :wordsInStream){
		//
		//                words.add(word);
		//            }
		//        }
		String wordSeparator = getWordSeparator();
		List<TextPosition> word = new ArrayList<>();
		for (TextPosition text : textPositions) {
			String thisChar = text.getUnicode();
			if (thisChar != null) {
				if (thisChar.length() >= 1) {
					if (!thisChar.equals(wordSeparator)) {
						word.add(text);
					} else if (!word.isEmpty()) {
						printWord(word);
						word.clear();
					}
				}
			}
		}
		if (!word.isEmpty()) {
			printWord(word);
			word.clear();
		}
	}

	void printWord(List<TextPosition> word) throws IOException {
//		File file=new File("src/test/resources/pdfFiles/DownloadBlob.pdf");
//		
//    	PDDocument document=PDDocument.load(file);    	
//    	PDPage page = new PDPage();
//          page=document.getPage(0);
		Rectangle2D boundingBox = null;
		StringBuilder builder = new StringBuilder();
		for (TextPosition text : word) {
			Rectangle2D box = new Rectangle2D.Float(text.getXDirAdj(), text.getYDirAdj(), text.getWidthDirAdj(), text.getHeightDir());
			if (boundingBox == null)
				boundingBox = box;
			else
				boundingBox.add(box);
			builder.append(text.getUnicode());
			// glyph space -> user space
            // note: text.getTextMatrix() is *not* the Text Matrix, it's the Text Rendering Matrix
            AffineTransform at = text.getTextMatrix().createAffineTransform();

            // in red:
            // show rectangles with the "height" (not a real height, but used for text extraction 
            // heuristics, it is 1/2 of the bounding box height and starts at y=0)
            Rectangle2D.Float rect = new Rectangle2D.Float((float) boundingBox.getX(),(float) boundingBox.getY(),
            		(float)boundingBox.getHeight(),(float)boundingBox.getWidth()
            		);
           
//    		Rectangle2D.Float rect = new Rectangle2D.Float(0, 0, 
//                    text.getWidthDirAdj() / text.getTextMatrix().getScalingFactorX(),
//                    text.getHeightDir() / text.getTextMatrix().getScalingFactorY());
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
		
		mylogger.info(builder.toString() + " [(X=" + boundingBox.getX() + ",Y=" + boundingBox.getY()
		+ ") height=" + boundingBox.getHeight() + " width=" + boundingBox.getWidth() + "]");
	
		//document.close(); 
	}
	
	



}


