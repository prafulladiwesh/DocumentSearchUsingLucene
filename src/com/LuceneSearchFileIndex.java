package com;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import constants.Constants;
import helper.Helper;

/**
 * Class to search for a query text from the documents
 * 
 * @author Prafulla Diwesh
 * 		   Venkatesh
 * 	       Sidra
 * 	       Deeksha
 * 		   Vinayak
 *
 */
public class LuceneSearchFileIndex {

	public IndexSearcher indexSearcher;
	public LuceneSearchFileIndex(String indexDocumentPath) throws IOException {
        	Directory directory = FSDirectory.open(Paths.get(indexDocumentPath));
			indexSearcher = new IndexSearcher(DirectoryReader.open(directory));
			// using Vector Space Ranking Model for similarity and computing rank
			indexSearcher.setSimilarity(new ClassicSimilarity());
	}
	
	public void searchIndexDocument(String searchQuery, int topSearchDoc) {
		try {
			int documentRank = 0;
			// porter stemmer is used for stemming and passed to QueryParser for creation of query
			QueryParser queryParser = new QueryParser(Constants.CONTENTS_STRING, new PorterStemmer());
			// search all the related documents and stored in high to low order based on score
			TopDocs topDocuments = indexSearcher.search(queryParser.parse(searchQuery), topSearchDoc);
			getIndexedDocument(documentRank, topDocuments);
		} catch (IOException ioexception) {
			System.out.println("IO Exception : "+ioexception);
		} catch (ParseException parseException) {
			System.out.println("IO Exception : "+parseException);
		}
	}

	private void getIndexedDocument(int documentRank, TopDocs topDocuments) throws IOException {
		int count = 0;
		for (ScoreDoc scoreDocument : topDocuments.scoreDocs) {
			count++;
			// get the actual document based on score
			Document document = getDocument(scoreDocument);
			Helper.printSearchString(++documentRank, scoreDocument, document);
		}
		if(count == 0) {
			System.out.println("No Documents Contain the Searched Item.");
		}
	}

	private Document getDocument(ScoreDoc scoreDocument) throws IOException {
		// search in the indexed document present at index directory
		return indexSearcher.doc(scoreDocument.doc);
	}
	
	public void findTopDocuments() {
		
	}
}
