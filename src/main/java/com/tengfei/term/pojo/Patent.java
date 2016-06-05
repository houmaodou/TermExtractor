/**
 * 
 */
package com.tengfei.term.pojo;

/**
 * @author Administrator
 *
 */
public class Patent {

private String patentId;
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
public Patent(String patentId, String chineseAbstract) {
	super();
	this.patentId = patentId;
	this.chineseAbstract = chineseAbstract;
}
@Override
public String toString() {
	return "Patent [patentId=" + patentId + ", chineseAbstract=" + chineseAbstract + "]";
}





}
