package com.tengfei.term.dao;

import java.util.List;

import org.bson.Document;

import com.tengfei.term.pojo.Patent;

public interface PatentDao {
	
public void insertMany(List<Document> patents);

}
