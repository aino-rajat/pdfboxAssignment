package pdfboxdemo;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;

public class Encrypt {

	public static void main(String[] args) throws InvalidPasswordException, IOException {
		// TODO Auto-generated method stub
		File file=new File("/home/intern3/demo2.pdf");
		PDDocument doc=PDDocument.load(file);
		AccessPermission accessPermission=new AccessPermission();
		StandardProtectionPolicy sppObject=new StandardProtectionPolicy("1234","1234",accessPermission);
		sppObject.setEncryptionKeyLength(128);	//setkeyLength	
		sppObject.setPermissions(accessPermission);//setting Permission
		doc.protect(sppObject);
		doc.save(file);
		doc.close();
		
		
	}

}
