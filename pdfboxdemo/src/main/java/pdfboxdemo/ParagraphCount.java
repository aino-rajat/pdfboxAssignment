package pdfboxdemo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ParagraphCount {

	public static void main(String[] args) throws IOException {

	
			
			        try {
			
			            File file1 = new File("src\\test\\resources\\pdfFiles\\fileTest.txt");
			
			            File file2 = new File("src\\test\\resources\\pdfFiles\\data1.txt");
			
			             
			
			            Scanner sc = new Scanner(new FileReader(file1));
			
			 
			
			            PrintWriter output = new PrintWriter(new FileOutputStream(file2));
			
			            
			
			             
			
			 
			
			            int lineNum = 0;
			
			            int wordCount = 0;
			
			            int charCount = 0;
			
			            int paraCount = 0;
			
			 
			
			            while (sc.hasNextLine()) {
			
			                String line;
			
			                line = sc.nextLine();
			
			                lineNum++;
			
			
			                charCount += line.length();
			
			                paraCount = getPara(line);
			
			 
			
			                if (!file2.exists()) {
			
			                    try {
			
			                        file2.createNewFile();
			
			                        output.print(line);
			
			                        output.flush();
			
			                    } catch (IOException e) {
			
			                        // TODO Auto-generated catch block
			
			                        e.printStackTrace();
			
			                    }
			
			                     
			
			                }
			
			            }
			
			             
			
			            System.out.println(lineNum + " lines");
			
			 
			
			            System.out.println(wordCount + " word(s)");
			
			 
			
			            System.out.println(charCount + " characters");
			
			 
			
			            System.out.println(paraCount);
			
			 
			
			            sc.close();
			
			            output.close();
			
			 
			
			            System.out.println("File Written");
						        } catch (FileNotFoundException e) {
			
			            System.out.println("There was an error opening one of the files.");
			
			        }
			
			    }
			
			 
			
			    private static int getPara(String line) {
			
			        String[] str = line.split("\r\n");
			
			        int count = 0;
			
			        if(str.length > 1){
			
			            count++;
			
			             
			
			        }
			
			        return count;
			
			    }
			
			

	
}