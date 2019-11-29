package helper;

import java.util.Scanner;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.ScoreDoc;

import constants.Constants;

/**
 * Helper class containing all the helper methods
 * 
 * @author Prafulla Diwesh
 */
public class Helper {

	public static String getInputFromScanner() {
		Scanner inputScanner = new Scanner(System.in);
	    System.out.print("\n\nENTER THE QUERY STRING : ");
	    String searchQuery = inputScanner.next(); 
        inputScanner.close();
		return searchQuery;
	}
	
	public static void printSearchString(int documentRank, ScoreDoc scoreDocument, Document document) {
		System.out.println("FILE NAME : "+document.get(Constants.FILENAME_STRING)+", RANK : " + documentRank + ", PATH : " + document.get(Constants.PATH_STRING) + ", LAST MODIFICATION TIME : " + document.get(Constants.MODIFIED_STRING) + (document.getField(Constants.TITLE_STRING)!=null  ? ", TITLE : " + document.get(Constants.TITLE_STRING) : "") + (document.getField(Constants.SUMMARY_STRING)!=null  ? ", SUMMARY : " + document.get(Constants.SUMMARY_STRING) : "") +", SCORE : " + scoreDocument.score);
	}
	
	public static void printCreateIndexString(String documentPath, Document document) {
		System.out.println("FILE NAME : "+document.get(Constants.FILENAME_STRING) + "DOCUMENT PATH : " + documentPath + ", DOCUMENT : "+ document);
	}
	
	public static boolean checkHtmlExtension(String documentPath) {
		return (documentPath.endsWith(Constants.HTML) || documentPath.endsWith(Constants.HTM));
	}
}
