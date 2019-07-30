package pdfboxdemo;

import java.io.IOException;

import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceCharacteristicsDictionary;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;

public class CreateSimpleForm {
	
	
	    private CreateSimpleForm()
	    {
	    }
	    
	    public static void main(String[] args) throws IOException
	    {
	        
	        PDDocument document = new PDDocument();
	        PDPage page = new PDPage(PDRectangle.A4);
	        document.addPage(page);
	        
	        
	       
	        PDFont font = PDType1Font.HELVETICA;
	        PDResources resources = new PDResources();
	        resources.put(COSName.getPDFName("Helv"), font);
	        
	      
	        PDAcroForm acroForm = new PDAcroForm(document);
	        document.getDocumentCatalog().setAcroForm(acroForm);
	        
	      
	        acroForm.setDefaultResources(resources);
	        
	       
	        String defaultAppearanceString = "/Helv 0 Tf 0 g";
	        acroForm.setDefaultAppearance(defaultAppearanceString);
	        
	        PDTextField textBox = new PDTextField(acroForm);
	        textBox.setPartialName("SampleField");
	       
	        defaultAppearanceString = "/Helv 12 Tf 0 0 1 rg";
	        textBox.setDefaultAppearance(defaultAppearanceString);

	
	        acroForm.getFields().add(textBox);

	      PDAnnotationWidget widget = textBox.getWidgets().get(0);
	        PDRectangle rect = new PDRectangle(50, 750, 200, 50);
	        widget.setRectangle(rect);
	        widget.setPage(page);

	       
	        PDAppearanceCharacteristicsDictionary fieldAppearance
	                = new PDAppearanceCharacteristicsDictionary(new COSDictionary());
	        fieldAppearance.setBorderColour(new PDColor(new float[]{0,1,0}, PDDeviceRGB.INSTANCE));
	        fieldAppearance.setBackground(new PDColor(new float[]{1,1,0}, PDDeviceRGB.INSTANCE));
	        widget.setAppearanceCharacteristics(fieldAppearance);

	       
	        widget.setPrinted(true);
	        
	      
	        page.getAnnotations().add(widget);
	        
	    
	        textBox.setValue("Sample field");

	        document.save("src/test/resources/pdfFiles/FormDemo.pdf");
	        document.close();
	    
	}
}
