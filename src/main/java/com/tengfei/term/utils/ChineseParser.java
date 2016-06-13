package com.tengfei.term.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bson.Document;

import com.tengfei.term.dao.PatentDao;
import com.tengfei.term.dao.impl.PatentDaoImpl;
import com.tengfei.term.pojo.KeyWord;
import com.tengfei.term.pojo.ParsedPhrase;
import com.tengfei.term.pojo.Patent;

public class ChineseParser {

	private static ChineseParser instance = null;

	private ChineseParser() {
		try {
			init();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public synchronized static ChineseParser getInstance() {

		if (instance == null) {
			instance = new ChineseParser();
		}
		return instance;

	}

	public void close() {

		if (instance != null) {
			CLibrary.Instance.NLPIR_Exit();
		}
	}

	public String transString(String aidString, String ori_encoding, String new_encoding) {
		try {
			return new String(aidString.getBytes(ori_encoding), new_encoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<ParsedPhrase> doParse(String parsedString, int type) throws Exception {

		String nativeBytes = null;
		try {
			nativeBytes = CLibrary.Instance.NLPIR_ParagraphProcess(parsedString, 3);
			// String nativeStr = new String(nativeBytes, 0,
			// nativeBytes.length,"utf-8");
			System.out.println("分词结果为： " + nativeBytes);
			// System.out.println("分词结果为： "
			// + transString(nativeBytes, system_charset, "UTF-8"));
			//
			// System.out.println("分词结果为： "
			// + transString(nativeBytes, "gb2312", "utf-8"));

			int nCountKey = 0;
			String nativeByte = CLibrary.Instance.NLPIR_GetKeyWords(parsedString, 10, false);

			System.out.print("关键词提取结果是：" + nativeByte);

			// int nativeElementSize = 4 * 6 +8;//size of result_t in native
			// code
			// int nElement = nativeByte.length / nativeElementSize;
			// ByteArrayInputStream(nativeByte));
			//
			// nativeByte = new byte[nativeByte.length];
			// nCountKey = testNLPIR30.NLPIR_KeyWord(nativeByte, nElement);
			//
			// Result[] resultArr = new Result[nCountKey];
			// DataInputStream dis = new DataInputStream(new
			// ByteArrayInputStream(nativeByte));
			// for (int i = 0; i < nCountKey; i++)
			// {
			// resultArr[i] = new Result();
			// resultArr[i].start = Integer.reverseBytes(dis.readInt());
			// resultArr[i].length = Integer.reverseBytes(dis.readInt());
			// dis.skipBytes(8);
			// resultArr[i].posId = Integer.reverseBytes(dis.readInt());
			// resultArr[i].wordId = Integer.reverseBytes(dis.readInt());
			// resultArr[i].word_type = Integer.reverseBytes(dis.readInt());
			// resultArr[i].weight = Integer.reverseBytes(dis.readInt());
			// }
			// dis.close();
			//
			// for (int i = 0; i < resultArr.length; i++)
			// {
			// System.out.println("start=" + resultArr[i].start + ",length=" +
			// resultArr[i].length + "pos=" + resultArr[i].posId + "word=" +
			// resultArr[i].wordId + " weight=" + resultArr[i].weight);
			// }

			//

		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}

		return null;

	}

	public List<KeyWord> getKeyWords(String parsedString, String parentId, int type) throws Exception {

		List<KeyWord> list = new ArrayList<KeyWord>();
		try {
			String nativeByte = CLibrary.Instance.NLPIR_GetKeyWords(parsedString, 10, false);
			System.out.print("关键词提取结果是：" + nativeByte);
			if (nativeByte != null) {
				String[] arr = nativeByte.split("#");
				for (String t : arr) {
					if (!t.isEmpty())
						list.add(new KeyWord(t, parentId, type));

				}
			}

		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}

		return list;

	}

	/*
	 * 在同一个语料库中，抽取唯一的关键词
	 */
	public Set<String> getKeyWords(String parsedString) throws Exception {

		Set<String> keywords = new HashSet<String>();

		try {
			String nativeByte = CLibrary.Instance.NLPIR_GetKeyWords(parsedString, 10, false);
			if (nativeByte != null) {
				String[] arr = nativeByte.split("#");
				for (String t : arr) {
					if (!t.isEmpty()) {
						keywords.add(t);

					}

				}
			}

		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}

		return keywords;
	}

	/**
	 * 获取某一个领域的关键词词频
	 * 
	 * @param list
	 * @param keywords
	 * @param field
	 * @return
	 */
	public List<Document> getKeywordsFrequency(List<Patent> list, Set<String> keywords, int field) {

		List<Document> wfl = new ArrayList<Document>();
		for (String keyword : keywords) {
			Document doc = new Document();
			doc.put("keyword", keyword);
			int count = 0;
			for (Patent p : list) {
				count = count + countToken(p.getChineseAbstract(), keyword);
			}

			doc.put("count", count);
			doc.put("field", field);
			wfl.add(doc);

			System.out.println(doc);
		}

		return wfl;

	}

	public double getSCValue(String candidateKw, int field, int bgField) {
		double scValue = 0;

		double log2s = Math.log(candidateKw.length()) / Math.log(2);
		PatentDao dao = new PatentDaoImpl();
		
		int sfs = dao.getWordFrequency(candidateKw, field);
		int bfs = dao.getWordFrequency(candidateKw, bgField);
		double lgsfs = Math.log10(sfs);
		
		
		
		List<Document> list = dao.getNestingCandidate(candidateKw, bgField);
		if (list==null||list.size()==0) { // 没有被嵌套
			System.out.println("候选词汇【"+candidateKw+"】没有被嵌套...");			
			System.out.println("sfs:"+sfs+",bfs:"+bfs);
			scValue = log2s * lgsfs * (sfs / (sfs + bfs));
		} else {// 被嵌套
			System.out.println("候选词汇【"+candidateKw+"】被嵌套...");
			scValue = log2s*lgsfs/(sfs + bfs);
			int scs = list.size();
			int sfsNest=0;
			for (Document document : list) {
				sfsNest += dao.getWordFrequency(document.getString("keyword"), field);
			}
			
			System.out.println("候选词汇【"+candidateKw+"】被嵌套的词汇总频率次数为："+sfsNest);

			scValue*=sfs-sfsNest/scs;
			//sfs ="";
		}

		return scValue;
	}

	

	public int countToken(String str, String token) {
		int count = 0;
		while (str.indexOf(token) != -1) {
			count++;
			str = str.substring(str.indexOf(token) + token.length());
		}
		// System.out.println(token+"出现的次数："+count);
		return count;
	}

	private void init() throws Exception {

		String argu = "";
		// String system_charset = "GBK";//GBK----0
		String system_charset = "UTF-8";
		int charset_type = 1;
		// int charset_type = 0;
		// 调用printf打印信息
		int init_flag = CLibrary.Instance.NLPIR_Init(argu.getBytes(system_charset), charset_type,
				"0".getBytes(system_charset));

		if (0 == init_flag) {
			throw new Exception("初始化失败！");
		}

	}

	public static void main(String[] args) throws Exception {

		for (int i = 0; i <= 2; i++) {
			System.out.println(i);

			String parsedString = "本发明提供一种转矩传感器以及动力转向装置。在具有一对解算器的转矩传感器";
			ChineseParser.getInstance().getKeyWords(parsedString, "0", 0);// (parsedString,
																			// 0);

		}

	}
}
