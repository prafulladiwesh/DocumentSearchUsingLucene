## Introduction:
This implementation is part of an academic course project during Information Retrieval Lecture. In this project we need to create a Information Retrieval system using *Apache Lucene* library. This project is motivated by *Apache Lucene* and uses some part of the *Lucene* library implementation for document indexing. 

## Implementation:
To create an simple Information Retrieval system using *Apache Lucene* libraray for indexing, searching, and ranking documents. Some part of *Lucene* implementation code is borrowed for indexing the documents.

  Implementation Steps:
    1. Parsing and indexing Text and HTML documents in a given folder and it's subfolders.
    2. Listing and saving all the indexed files.
    3. Performing data preprocessing steps and apply Porter Stemmer.
    4. For a given search query, printing ranked list of relevant documents.
    5. For HTML documents, search is done on documents text along with it's title and date
    
  Library versions used:
      
      Apache Lucene | 7.7.2
      Java          | 8

## Input data:
HTML and Text documents present in a folder and it's subfolder. For sample docs folder is present in this repository which contains text and html docs.

## Output:
The output contains following values:
  * Most Relevant Documents
  * Their Rank
  * Their Path
  * Last Modification Time
  * It's Relevance Score
  * In case of HTML document
    * HTML Document Title
    * HTML Document Summary

## How to Run the program?
Using the following command from terminal:

    java -jar documentsearch.jar Document_Folder
