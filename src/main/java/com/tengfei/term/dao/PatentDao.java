package com.tengfei.term.dao;

import java.util.List;

import org.bson.Document;

public interface PatentDao {
	
public void insertMany(List<Document> patents);
public int getWordFrequency(String keyword, int field);

/** 获取某一个候选词汇的嵌套关键词
 * @param keyword
 * @param field
 * @return
 */
public List<Document> getNestingCandidate(String keyword,int field);

/** 获取所有关键词（候选词汇）
 * @param field
 * @return
 */
public List<String> getAllKeywords(int field);

public List<Document> getTopNSCValue(int topN);

}
