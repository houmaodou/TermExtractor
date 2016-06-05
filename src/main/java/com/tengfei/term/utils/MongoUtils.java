package com.tengfei.term.utils;


import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.tengfei.term.dao.MongoDBDao;
import com.tengfei.term.dao.impl.MongoDBDaoImpl;

public class MongoUtils {
	

public static void main(String[] args) {
	
	String dbName ="local";
	MongoDBDao dao = MongoDBDaoImpl.getMongoDBDaoImplInstance();
	
	MongoDatabase db = dao.getDb(dbName);
	
//	db.createCollection("users");
	
	MongoCollection<Document>  users = db.getCollection("users");
	Document doc = new  Document();
	
	doc.put("name", "hexin");
	doc.put("sex", "1");
	
	users.insertOne(doc);
	
	//users.fin
	
	MongoCursor<Document> iter = users.find().iterator();
	while(iter.hasNext()){
		System.out.println(iter.next());
	}
	
	
   //  System.out.println(JSON.serialize(cur));
 }

}
