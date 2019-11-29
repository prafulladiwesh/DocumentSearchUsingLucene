package com;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.jsoup.Jsoup;

import constants.Constants;
import helper.Helper;

/**
 * Class to create index of text and html document using Apache Lucene library
 * 
 * @author Prafulla Diwesh
 */
public class LuceneCreateFileIndex {

	
	public IndexWriter iWriter;
	
	public LuceneCreateFileIndex(String indexCreationPath) throws IOException {
			Directory directory = FSDirectory.open(Paths.get(indexCreationPath));
		    IndexWriterConfig indexWriterConfiguration = new IndexWriterConfig(new PorterStemmer());
		    indexWriterConfiguration.setOpenMode(OpenMode.CREATE_OR_APPEND);
		    this.iWriter = new IndexWriter(directory, indexWriterConfiguration);
	}

	public void buildIndexedDocument(String documentPath) throws IOException {
		final Path documentDirectoryPath = Paths.get(documentPath);
		// check if the given document path is a directory
		if (Files.isDirectory(documentDirectoryPath)) {
			  // check for the root and all the sub-directories present in the root directory.
			  // the root directory and sub-directories are created as a tree and searched from root to leaf.
		      Files.walkFileTree(documentDirectoryPath, new SimpleFileVisitor<Path>() {
		    	//
		        @Override
		        public FileVisitResult visitFile(Path filePath, BasicFileAttributes fileAttributes) throws IOException {
		          try {
		        	  // create the document for the readable files at indexDirectory using IndexWriter
		        	  iWriter.updateDocument(new Term(Constants.PATH_STRING, documentPath.toString()), indexParsedDocument(filePath.toString(), fileAttributes.lastModifiedTime().toMillis()));
		          } catch (IOException exception) {
		        	System.out.println("Exception in reading the file. The file is ignored. Exception : "+exception);
		          }
		          // continue searching for more files in the directory
		          return FileVisitResult.CONTINUE;
		        }
		      });
		    } else {
		    	long lastModifiedTime = Files.getLastModifiedTime(documentDirectoryPath).toMillis();
		    	// If the file is not a directory then create the document for the readable files at indexDirectory using IndexWriter
		    	iWriter.updateDocument(new Term(Constants.PATH_STRING, documentPath.toString()), indexParsedDocument(documentPath.toString(), lastModifiedTime));
		    }
	}
	
	private Document indexParsedDocument(String documentPath, long lastModifiedTime) throws IOException {
		Document document = new Document();
        FieldType fieldType = new FieldType();
        fieldType.setStored(true);
        fieldType.setTokenized(true);
        
        if (documentPath.contains(Constants.TEXT) || Helper.checkHtmlExtension(documentPath)) {
        	document.add(new StringField(Constants.FILENAME_STRING, new File(documentPath).getName(), Field.Store.YES));
        	document.add(new StringField(Constants.PATH_STRING, documentPath, Field.Store.YES));
        	document.add(new StringField(Constants.MODIFIED_STRING, String.valueOf(lastModifiedTime), Field.Store.YES));
        	document.add(new TextField(Constants.CONTENTS_STRING, new FileReader(new File(documentPath))));
        	getHtmlTitle(documentPath, document, fieldType);
        	Helper.printCreateIndexString(documentPath, document);
        }
        return document;
	}

	private void getHtmlTitle(String documentPath, Document document, FieldType fieldType) throws IOException {
		if (Helper.checkHtmlExtension(documentPath)) {
			document.add(new Field(Constants.TITLE_STRING, Jsoup.parse(new File(documentPath),Constants.UTF_8).title(), fieldType));
			document.add(new Field(Constants.SUMMARY_STRING, Jsoup.parse(new File(documentPath),Constants.UTF_8).getElementsByTag(Constants.SUMMARY_STRING).toString(), fieldType));
		}
	}
	
	public void closeIndexWriter() throws IOException {
		iWriter.close();
	}
}
