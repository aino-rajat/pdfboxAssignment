package pdfboxdemo;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.contentstream.PDFStreamEngine;
import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class ExractImagesDemo extends PDFStreamEngine{
	
	public ExractImagesDemo()throws IOException {
		
	}
	 public int imageNumber = 1;
	public Boolean extractImages(File fileName) throws InvalidPasswordException, IOException {
		PDDocument document = null;
		if (fileName == null || fileName.exists()==false) {
			return false;
		}
				
			try {
				document = PDDocument.load(fileName);
				ExractImagesDemo printer = new ExractImagesDemo();
				int pageNum = 0;
				for (PDPage page : document.getPages()) {
					pageNum++;
					System.out.println("Processing page: " + pageNum);
					printer.processPage(page);
					return true;
				} 
			} finally {
				 if( document != null )
		            {
		                document.close();
		            }
				 
			}
				return false;
		 
	}
	

	
	/**
     * @param operator The operation to perform.
     * @param operands The list of arguments.
     *
     * @throws IOException If there is an error processing the operation.
     */
    @Override
    protected void processOperator( Operator operator, List<COSBase> operands) throws IOException
    {
        String operation = operator.getName();
        if( "Do".equals(operation) )
        {
            COSName objectName = (COSName) operands.get( 0 );
            PDXObject xobject = getResources().getXObject( objectName );
            if( xobject instanceof PDImageXObject)
            {
                PDImageXObject image = (PDImageXObject)xobject;
                int imageWidth = image.getWidth();
                int imageHeight = image.getHeight();
 
                // same image to local
                BufferedImage bImage = new BufferedImage(imageWidth,imageHeight,BufferedImage.TYPE_INT_ARGB);
                bImage = image.getImage();
                ImageIO.write(bImage,"PNG",new File("image_"+imageNumber+".png"));
                System.out.println("Image saved.");
                imageNumber++;
                
            }
            else if(xobject instanceof PDFormXObject)
            {
                PDFormXObject form = (PDFormXObject)xobject;
                showForm(form);
            }
        }
        else
        {
            super.processOperator( operator, operands);
        }
    }
 
}

	
