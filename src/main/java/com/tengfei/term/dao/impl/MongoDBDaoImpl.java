package com.tengfei.term.dao.impl;

import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.tengfei.term.dao.MongoDBDao;

/**
 * 类名： MongoDBDaoImpl 包名：com.tengfei.term.dao.impl 
 * 作者： houhx 时间： 2014-8-30
 * 下午04:21:11 
 * 描述： TODO(这里用一句话描述这个类的作用)
 */
public class MongoDBDaoImpl implements MongoDBDao {

	/**
	 * MongoClient的实例代表数据库连接池，是线程安全的，可以被多线程共享，客户端在多线程条件下仅维持一个实例即可
	 * Mongo是非线程安全的，目前mongodb API中已经建议用MongoClient替代Mongo
	 */
	private MongoClient mongoClient = null;

	/**
	 * 
	 * 私有的构造函数 作者：houhx
	 */
	private MongoDBDaoImpl() {
		if (mongoClient == null) {
			MongoClientOptions.Builder build = new MongoClientOptions.Builder();
			// 与数据最大连接数50
			build.connectionsPerHost(50);
			// 如果当前所有的connection都在使用中，则每个connection上可以有50个线程排队等待
			build.threadsAllowedToBlockForConnectionMultiplier(50);
			build.connectTimeout(1 * 60 * 1000);
			build.maxWaitTime(2 * 60 * 1000);
			MongoClientOptions options = build.build();
			mongoClient = new MongoClient("127.0.0.1", options);

		}
	}

	/******** 单例模式声明开始，采用饿汉式方式生成，保证线程安全 ********************/

	// 类初始化时，自行实例化，饿汉式单例模式
	private static final MongoDBDaoImpl mongoDBDaoImpl = new MongoDBDaoImpl();

	/**
	 * 
	 * 方法名：getMongoDBDaoImplInstance 作者：houhx 创建时间：2014-8-30 下午04:29:26
	 * 描述：单例的静态工厂方法
	 * 
	 * @return
	 */
	public synchronized static MongoDBDaoImpl getMongoDBDaoImplInstance() {
		return mongoDBDaoImpl;
	}

	public MongoDatabase getDb(String dbName) {
		// TODO Auto-generated method stub
		return mongoClient.getDatabase(dbName);
	}

	public MongoCollection<?> getCollection(String dbName, String collectionName) {
		// TODO Auto-generated method stub
		return getDb(dbName).getCollection(collectionName);
	}

	/************************ 单例模式声明结束 *************************************/

	// @Override
	public boolean insertOne(String dbName, String collectionName, Document record) {
		MongoDatabase db = getDb(dbName);
		MongoCollection<Document> dbCollection = db.getCollection(collectionName);

		try {
			dbCollection.insertOne(record);
			return true;
			// resultString = result.getError();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return false;

	}
	
	public boolean insertMany(String dbName, String collectionName, List<Document> records) {
		MongoDatabase db = getDb(dbName);
		MongoCollection<Document> dbCollection = db.getCollection(collectionName);

		try {
			dbCollection.insertMany(records);
			return true;
			// resultString = result.getError();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return false;

	}

	// @Override
	// public boolean delete(String dbName, String collectionName, String[]
	// keys,
	// Object[] values) {
	// MongoDatabase db = null;
	// MongoCollection<Document> dbCollection = null;
	// if(keys!=null && values!=null){
	// if(keys.length != values.length){ //如果keys和values不对等，直接返回false
	// return false;
	// }else{
	// try {
	// db = mongoClient.getDatabase(dbName); //获取指定的数据库
	// dbCollection = db.getCollection(collectionName); //获取指定的collectionName集合
	//
	// BasicDBObject doc = new BasicDBObject(); //构建删除条件
	// WriteResult result = null; //删除返回结果
	// String resultString = null;
	//
	// for(int i=0; i<keys.length; i++){
	// doc.put(keys[i], values[i]); //添加删除的条件
	// }
	// result = dbCollection.remove(doc); //执行删除操作
	//
	// resultString = result.get
	//
	// if(null != db){
	// try {
	// db.close //请求结束后关闭db
	// db = null;
	// } catch (Exception e) {
	// // TODO: handle exception
	// e.printStackTrace();
	// }
	//
	// }
	//
	// return (resultString!=null) ? false : true; //根据删除执行结果进行判断后返回结果
	// } catch (Exception e) {
	// // TODO: handle exception
	// e.printStackTrace();
	// } finally{
	//// if(null != db){
	//// db.requestDone(); //关闭db
	//// db = null;
	//// }
	// }
	//
	// }
	// }
	// return false;
	// }

	// @Override
	// public ArrayList<DBObject> find(String dbName, String collectionName,
	// Bson filter, int num) {
	//
	//
	// filter.
	// ArrayList<DBObject> resultList = new ArrayList<DBObject>(); //创建返回的结果集
	// MongoDatabase db = null;
	// MongoCollection dbCollection = null;
	// DBCursor cursor = null;
	// if(keys!=null && values!=null){
	// if(keys.length != values.length){
	// return resultList; //如果传来的查询参数对不对，直接返回空的结果集
	// }else{
	// try {
	// db = getDb(dbName); //获取数据库实例
	// dbCollection = db.getCollection(collectionName); //获取数据库中指定的collection集合
	//
	// BasicDBObject queryObj = new BasicDBObject(); //构建查询条件
	//
	// for(int i=0; i<keys.length; i++){ //填充查询条件
	// queryObj.put(keys[i], values[i]);
	// }
	// cursor = dbCollection.find(Bson filter); //查询获取数据
	// int count = 0;
	// if(num != -1){ //判断是否是返回全部数据，num=-1返回查询全部数据，num!=-1则返回指定的num数据
	// while(count<num && cursor.hasNext()){
	// resultList.add(cursor.next());
	// count++;
	// }
	// return resultList;
	// }else{
	// while(cursor.hasNext()){
	// resultList.add(cursor.next());
	// }
	// return resultList;
	// }
	// } catch (Exception e) {
	// // TODO: handle exception
	// } finally{
	// if(null != cursor){
	// cursor.close();
	// }
	//// if(null != db){
	//// db.requestDone(); //关闭数据库请求
	//// }
	// }
	// }
	// }
	//
	// return resultList;
	// }
	//
	//
	//
	//
	//
	//// @Override
	//// public boolean isExit(String dbName, String collectionName, String key,
	//// Object value) {
	//// // TODO Auto-generated method stub
	//// DB db = null;
	//// DBCollection dbCollection = null;
	//// if(key!=null && value!=null){
	//// try {
	//// db = mongoClient.getDB(dbName); //获取数据库实例
	//// dbCollection = db.getCollection(collectionName);
	// //获取数据库中指定的collection集合
	//// BasicDBObject obj = new BasicDBObject(); //构建查询条件
	//// obj.put(key, value);
	////
	//// if(dbCollection.count(obj) > 0) {
	//// return true;
	//// }else{
	//// return false;
	//// }
	//// } catch (Exception e) {
	//// // TODO: handle exception
	//// e.printStackTrace();
	//// } finally{
	//// if(null != db){
	//// db.requestDone(); //关闭db
	//// db = null;
	//// }
	//// }
	////
	//// }
	//// return false;
	//// }
	////
	//// @Override
	//// public boolean update(String dbName, String collectionName,
	//// DBObject oldValue, DBObject newValue) {
	//// DB db = null;
	//// DBCollection dbCollection = null;
	//// WriteResult result = null;
	//// String resultString = null;
	////
	//// if(oldValue.equals(newValue)){
	//// return true;
	//// }else{
	//// try {
	//// db = mongoClient.getDB(dbName); //获取数据库实例
	//// dbCollection = db.getCollection(collectionName);
	// //获取数据库中指定的collection集合
	////
	//// result = dbCollection.update(oldValue, newValue);
	//// resultString = result.getError();
	////
	//// return (resultString!=null) ? false : true;
	//// } catch (Exception e) {
	//// // TODO: handle exception
	//// e.printStackTrace();
	//// } finally{
	//// if(null != db){
	//// db.requestDone(); //关闭db
	//// db = null;
	//// }
	//// }
	////
	//// }
	////
	//// return false;
	//// }
	//
	// /**
	// * 方法名：main
	// * 作者：houhx
	// * 创建时间：2014-8-30 下午04:21:11
	// * 描述：TODO(这里用一句话描述这个方法的作用)
	// * @param args
	// */
	// public static void main(String[] args) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// public boolean update(String dbName, String collectionName, DBObject
	// oldValue, DBObject newValue) {
	// // TODO Auto-generated method stub
	// return false;
	// }
	//
	// public boolean isExit(String dbName, String collectionName, String key,
	// Object value) {
	// // TODO Auto-generated method stub
	// return false;
	// }
	//
	// public boolean delete(String dbName, String collectionName, String[]
	// keys,
	// Object[] values) {
	// // TODO Auto-generated method stub
	// return false;
	// }

}
