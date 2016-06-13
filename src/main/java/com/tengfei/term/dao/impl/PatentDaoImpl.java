package com.tengfei.term.dao.impl;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.regex.Pattern;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.BasicDBObject;
import com.mongodb.client.model.Filters;
import com.tengfei.term.dao.PatentDao;
import com.tengfei.term.utils.PatentConstants;

public class PatentDaoImpl implements PatentDao {

	public final static String DB_NAME="local";
	public final static String COL_KEYWORD_FREQ="keyword_freq";
	public final static String COL_KEYWORD_SC_VALUE="keyword_scv";

	public void insertMany(List<Document> patents) {
		// TODO Auto-generated method stub
		MongoDBDaoImpl.getMongoDBDaoImplInstance().insertMany(DB_NAME, "raw_patent", patents);
	}
	
	public int getWordFrequency(String keyword, int field) {
		// TODO Auto-generated method stub
		System.out.println("正在获取【"+keyword+"】的词频是...");
		BasicDBObject filter = new BasicDBObject();
		filter.put("keyword", keyword);
		filter.put("field", field);
		int count = 0;
		List<Document> list = MongoDBDaoImpl.getMongoDBDaoImplInstance().find(DB_NAME,COL_KEYWORD_FREQ, filter);
		if(list!=null&&list.size()!=0){
			Document doc = list.get(0);
			count=  (Integer)doc.get("count");
			System.out.println("获取到【"+keyword+"】的词频是："+count);

		}
		
		return count;
	}

	
	public List<Document> getNestingCandidate(String keyword,int field) {
		
		
		BasicDBObject filter=  new BasicDBObject();
		filter.put("keyword", Pattern.compile(keyword));
		filter.put("field", field);
	
		List<Document> list = MongoDBDaoImpl.getMongoDBDaoImplInstance().find(DB_NAME, COL_KEYWORD_FREQ, filter);
		List<Document> retlist = new ArrayList<Document>();
		if(list!=null&&list.size()!=0)
		for (Document document : list) {
			
			if(!document.getString("keyword").equalsIgnoreCase(keyword)){
				retlist.add(document); // 去掉keyword 本身
			}
			
		}
		
		return retlist;
	}

	public List<String> getAllKeywords(int field) {
		
		List<String> keywords = new ArrayList<String>();
		BasicDBObject filter = new BasicDBObject();
		filter.put("field",field);
		LinkedHashSet<String> set = new LinkedHashSet<String>();
		set.add("keyword");
		List<Document>  list =  MongoDBDaoImpl.getMongoDBDaoImplInstance().find(DB_NAME, COL_KEYWORD_FREQ,filter,set);

		for (Document document : list) {
			keywords.add(document.getString("keyword"));
		}
		
		return keywords;
	}
	
public List<Document> getTopNSCValue(int topN) {
		
		BasicDBObject sort = new BasicDBObject();
//		BasicDBObject filter = new BasicDBObject();
//		filter.put("scv",new BasicDBObject("lt",0.0));
		sort.append("scv", -1);
		List<Document>  list =  MongoDBDaoImpl.getMongoDBDaoImplInstance().find(DB_NAME, COL_KEYWORD_SC_VALUE,null,null,sort,topN);
		return list;
	}
	
	

	
	
	

}
