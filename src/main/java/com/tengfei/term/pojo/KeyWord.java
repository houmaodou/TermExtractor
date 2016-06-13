/**
 * 
 */
package com.tengfei.term.pojo;

import org.bson.Document;


/**
 * @author Administrator
 *
 */
public class KeyWord {
	
private String content;
private String patentId;
private int field;

public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}

public String getPatentId() {
	return patentId;
}
public void setPatentId(String patentId) {
	this.patentId = patentId;
}
@Override
public String toString() {
	return "ParsedPhrase [content=" + content + ",  patentId=" + patentId + "]";
}
public int getField() {
	return field;
}
public void setField(int field) {
	this.field = field;
}
public KeyWord(String content, String patentId, int field) {
	super();
	this.content = content;
	this.patentId = patentId;
	this.field = field;
}

public Document toDocument(){
	
	Document doc = new Document();
	
	doc.put("content", content);
	doc.put("patentId", patentId);
	doc.put("field", field);

	return doc;
	
}






}
