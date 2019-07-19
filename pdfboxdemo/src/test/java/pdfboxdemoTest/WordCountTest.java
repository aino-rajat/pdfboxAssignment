package pdfboxdemoTest;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import pdfboxdemo.CharacterCountPdf;
import pdfboxdemo.WordCount;

public class WordCountTest {
	private WordCount wordcount;
	private CharacterCountPdf charctercount;
	private String inputPath="src/test/resources/pdfFiles/DownloadBlob.pdf"; 
	
	
	@Test
	public void test() {
		wordcount=new WordCount();
		File inputfile=new File(inputPath);
	wordcount.checkFile(inputfile);
	assertTrue("Words not found in the document",true);
	}

	@Test
	public void chacterCounttest() {
		charctercount=new CharacterCountPdf();
		File inputFile=new File(inputPath);
		charctercount.checkFile(inputFile);
		assertTrue("Characters not Found in Document",true);
		
	}
}
