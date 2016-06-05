package com.tengfei.term.dao.impl;

import java.util.List;

import org.bson.Document;

import com.tengfei.term.dao.PatentDao;

public class PatentDaoImpl implements PatentDao {

	public void insertMany(List<Document> patents) {
		// TODO Auto-generated method stub
		MongoDBDaoImpl.getMongoDBDaoImplInstance().insertMany("patent", "raw_patent", patents);
	}

}
