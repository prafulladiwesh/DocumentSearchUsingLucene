package com;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;

/**
 * Class to implement the PorterStemmer algorithm
 * 
 * @author Prafulla Diwesh
 * 		   Venkatesh
 * 	       Sidra
 * 	       Deeksha
 * 		   Vinayak
 *
 */
public class PorterStemmer extends Analyzer {

	@Override
	protected TokenStreamComponents createComponents(String sourceField) {
		StandardTokenizer standardTokenizer = new StandardTokenizer();
		// convert input string to lowecase and input it to porterstemmer for stemming.
		return new TokenStreamComponents(standardTokenizer, new PorterStemFilter(new LowerCaseFilter(standardTokenizer)));
	}	
}
