/**
 * 
 */
package com.tengfei.term.pojo;

import org.bson.Document;

import com.mongodb.ReflectionDBObject;

/**
 * @author Administrator
 *
 */
public class Patent extends ReflectionDBObject {

private String patentId;
private int field;
private String chineseAbstract;

public String getPatentId() {
	return patentId;
}
public void setPatentId(String patentId) {
	this.patentId = patentId;
}
public String getChineseAbstract() {
	return chineseAbstract;
}
public void setChineseAbstract(String chineseAbstract) {
	this.chineseAbstract = chineseAbstract;
}
public int getField() {
	return field;
}
public void setField(int field) {
	this.field = field;
}
public Patent(String patentId,String chineseAbstract ,int field) {
	super();
	this.patentId = patentId;
	this.field = field;
	this.chineseAbstract = chineseAbstract;
}
@Override
public String toString() {
	return "Patent [patentId=" + patentId + ", field=" + field + ", chineseAbstract=" + chineseAbstract + "]";
}

public Document toDocument(){
	
	Document doc = new Document();
	
	doc.put("patentId", patentId);
	doc.put("field", field);
	doc.put("chineseAbstract", chineseAbstract);

	return doc;
	
}





}
