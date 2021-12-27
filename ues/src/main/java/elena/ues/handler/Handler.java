package elena.ues.handler;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.text.PDFTextStripper;

import elena.ues.model.Article;

public class Handler {
	
	public Article getArticle(File file) {
		Article article = new Article(); 
		
		try {
			PDFParser parser = new PDFParser(new RandomAccessFile(file, "r"));
			parser.parse();
			String text = getText(parser); 
			//article.setText(text);
			article.setDescription(text);
			
			PDDocument pdf = parser.getPDDocument();
			PDDocumentInformation info = pdf.getDocumentInformation();
			
			String name = "" + info.getTitle();
			article.setName(name);
			
			String description = "" + info.getTrapped();
			article.setDescription(description);
			
			//set price 
			
			pdf.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return article;
	}
	
	 public String getText(File file) {
	        try {
	            PDFParser parser = new PDFParser(new RandomAccessFile(file, "r"));
	            parser.parse();
	            PDFTextStripper textStripper = new PDFTextStripper();
	            String text = textStripper.getText(parser.getPDDocument());
	            return text;
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return null;
	 }
	
	private String getText(PDFParser parser) {
		try {
			PDFTextStripper textStripper = new PDFTextStripper(); 
			String text = textStripper.getText(parser.getPDDocument());
			return text;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
