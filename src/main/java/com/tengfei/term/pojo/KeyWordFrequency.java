/**
 * 
 */
package com.tengfei.term.pojo;

import org.bson.Document;


/**
 * @author Administrator
 *
 */
public class KeyWordFrequency {
	
private String keyword;
private int count;
private int field;

public String getContent() {
	return keyword;
}
public void setContent(String content) {
	this.keyword = content;
}

@Override
public String toString() {
	return "ParsedPhrase [keyword=" + keyword + ",  count=" + count + "]";
}
public int getField() {
	return field;
}
public void setField(int field) {
	this.field = field;
}
public KeyWordFrequency(String content, int count, int field) {
	super();
	this.keyword = content;
	this.count = count;
	this.field = field;
}

public Document toDocument(){
	
	Document doc = new Document();	
	doc.put("keyword", keyword);
	doc.put("count", count);
	doc.put("field", field);
	return doc;	
}






}
