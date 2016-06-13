package com.tengfei.term;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.bson.Document;
import org.junit.Test;

import com.tengfei.term.dao.PatentDao;
import com.tengfei.term.dao.PatentDaoFactory;
import com.tengfei.term.dao.impl.MongoDBDaoImpl;
import com.tengfei.term.dao.impl.PatentDaoImpl;
import com.tengfei.term.pojo.Patent;
import com.tengfei.term.utils.ChineseParser;
import com.tengfei.term.utils.PatentConstants;
import com.tengfei.term.utils.PatentFileReader;

public class MainEntry {

	String dbName ="local";
	
	/*/
	 * 电动汽车关键词词频统计
	 */
	@Test
	public void doELEC_CAR() throws Exception{
		//获取汽车专利
		String elec_car_file = "D:\\Project\\TermExtractor\\电动汽车.XLS";
		List<Patent> elec_car_patents =	PatentFileReader.getAbstracts(elec_car_file,PatentConstants.ELECTRIC_CAR);
		//存儲在系統中
		List<Document> list = new ArrayList<Document>();
		Set<String> keywords = new HashSet<String>();
		for(Patent p: elec_car_patents){
			list.add(p.toDocument());
			keywords.addAll(ChineseParser.getInstance().getKeyWords(p.getChineseAbstract()));
		}
		for(String keyword:keywords){
			System.out.println(keyword);
		}
		List<Document> kwfl = ChineseParser.getInstance().getKeywordsFrequency(elec_car_patents, keywords, PatentConstants.ELECTRIC_CAR);
		MongoDBDaoImpl.getMongoDBDaoImplInstance().insertMany(dbName, "keyword_freq", kwfl);

		ChineseParser.getInstance().close();
	}
	
	/*
	 * 基因芯片关键词词频统计
	 */
	@Test
	public void doGENE_CHIP() throws Exception{
		//获取汽车专利
		String elec_car_file = "D:\\Project\\TermExtractor\\基因芯片.XLS";
		List<Patent> elec_car_patents =	PatentFileReader.getAbstracts(elec_car_file,PatentConstants.GENE_CHIP);
		//存儲在系統中
		List<Document> list = new ArrayList<Document>();
		Set<String> keywords = new HashSet<String>();
		for(Patent p: elec_car_patents){
			list.add(p.toDocument());
			keywords.addAll(ChineseParser.getInstance().getKeyWords(p.getChineseAbstract()));
		}
		for(String keyword:keywords){
			System.out.println(keyword);
		}
		List<Document> kwfl = ChineseParser.getInstance().getKeywordsFrequency(elec_car_patents, keywords, PatentConstants.GENE_CHIP);
		MongoDBDaoImpl.getMongoDBDaoImplInstance().insertMany(dbName, "keyword_freq", kwfl);
		ChineseParser.getInstance().close();
	}
	@Test
	public void buildSCValueCollection(){
		
		PatentDao  dao = PatentDaoFactory.getInstance();
		List<String> candidateList = dao.getAllKeywords(PatentConstants.ELECTRIC_CAR);
		List<Document> scCols = new ArrayList<Document>();
		for (String string : candidateList) {
			Document d = new Document();
			double scVlaue = ChineseParser.getInstance().getSCValue(string, PatentConstants.ELECTRIC_CAR, PatentConstants.GENE_CHIP);
			d.put("keyword", string);
			d.put("scv", scVlaue);
			System.out.println(d.toJson());
			scCols.add(d);
		}	
		MongoDBDaoImpl.getMongoDBDaoImplInstance().insertMany(dbName, "keyword_scv", scCols);	
		ChineseParser.getInstance().close();
	}
	
	@Test
	public void getTop10SCValue(){
		
		PatentDao  dao = PatentDaoFactory.getInstance();
		List<Document> candidateList = dao.getTopNSCValue(50);
		for (Document doc : candidateList) {
			System.out.println(doc.toJson());
		}	
	}
	
	
	@Test
	public void testIsIncluded(){
		
		PatentDao  dao = new PatentDaoImpl();
	  	
	}
	
	@Test
	public void printAllKeys(){
		
		PatentDao  dao = PatentDaoFactory.getInstance();
		List<String> list = dao.getAllKeywords(PatentConstants.ELECTRIC_CAR);
		for (String string : list) {
			System.out.println(string);
		}

	}
	
	public static void main(String[] args) {
		LinkedHashSet<String> tree = new LinkedHashSet<String>();
		tree.add("China");
		tree.add("America");
		tree.add("Japan"); 
		tree.add("Chinese"); 
		Iterator<String> iter = tree.iterator();
		while(iter.hasNext()) { 
		System.out.println(iter.next()); 
		}
	}
	
}
