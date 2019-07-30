package pdfboxdemo;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceCharacteristicsDictionary;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

public final class AddborderToField {

	  private void AddBorderToField()
	  	    {
	  	    }
	  	    
	  	    public static void main(String[] args) throws IOException
	  	    {
	 	        
	  	        try (PDDocument document = PDDocument.load(new File("src/test/resources/pdfFiles/FormDemo.pdf")))
	  	        {
	  	            PDAcroForm acroForm = document.getDocumentCatalog().getAcroForm();
	  	            
	  	    
	  	            PDField field = acroForm.getField("SampleField");
	  	            PDAnnotationWidget widget = field.getWidgets().get(0);
	  	            
	         
	  	            PDAppearanceCharacteristicsDictionary fieldAppearance =
	  	                    new PDAppearanceCharacteristicsDictionary(new COSDictionary());
	  	            PDColor green = new PDColor(new float[] { 0, 1, 0 }, PDDeviceRGB.INSTANCE);
	  	            fieldAppearance.setBorderColour(green);
	  	            
	  	           
	  	            widget.setAppearanceCharacteristics(fieldAppearance);
	  	            
	  	            document.save("src/test/resources/pdfFiles/AddBorderToField.pdf");
	  	        }
	  	    }
		

		}
	
