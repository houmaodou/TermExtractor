/**
 * 
 */
package com.tengfei.term.pojo;

/**
 * @author Administrator
 *
 */
public class ParsedPhrase {
	
private String content;
private String category;
private String patentId;
private int field;

public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}
public String getCategory() {
	return category;
}
public void setCategory(String category) {
	this.category = category;
}
public String getPatentId() {
	return patentId;
}
public void setPatentId(String patentId) {
	this.patentId = patentId;
}
@Override
public String toString() {
	return "ParsedPhrase [content=" + content + ", category=" + category + ", patentId=" + patentId + "]";
}
public ParsedPhrase(String content, String category, String patentId) {
	super();
	this.content = content;
	this.category = category;
	this.patentId = patentId;
}
public int getField() {
	return field;
}
public void setField(int field) {
	this.field = field;
}










}
