package com;

import java.io.IOException;

import constants.Constants;
import helper.Helper;

public class ExecuteDocumentSearch {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String documentPath = args[0];
//		String documentPath = "/Users/prafulla/eclipse-workspace/IRProject1/src/docs/";
		String indexedDocumnentPath = documentPath+"/indexeddocs";
		
		try {
			System.out.println("::::Starting Index Creation for Documents Present at : "+documentPath+".\nIndexed document path : "+indexedDocumnentPath);
			LuceneCreateFileIndex createFileIndex = new LuceneCreateFileIndex(indexedDocumnentPath);
			createFileIndex.buildIndexedDocument(documentPath);
			createFileIndex.closeIndexWriter();
			System.out.println("::::Ending Index Creation::::");
			
			System.out.println("Enter Query to be Searched in the Documents");
			String searchQuery = Helper.getInputFromScanner();
			System.out.println("::::Searching for top matching documents at indexed path : "+indexedDocumnentPath);
//			String searchQuery = "Prafulla";
			LuceneSearchFileIndex searchFile = new LuceneSearchFileIndex(indexedDocumnentPath);
			searchFile.searchIndexDocument(searchQuery, Constants.TOP_SEARCH_DOC);
			System.out.println("::::Ending Document Search::::");
			
			
		} catch (IOException ioException) {
			System.out.println("IO Exception in main class : "+ioException);
		} catch (Exception exception) {
			System.out.println("Exception in main class : "+exception);
		}
	}

	

}
