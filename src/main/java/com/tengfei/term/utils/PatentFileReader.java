/**
 * 
 */
package com.tengfei.term.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.tengfei.term.pojo.Patent;

/**
 * @author Administrator
 *
 */
public class PatentFileReader {

	public static List<Patent> getAbstracts(String filePath,int field) throws FileNotFoundException, IOException {

		List<Patent> list = new ArrayList<Patent>();

		File file = new File(filePath);
		POIFSFileSystem poifsFileSystem = new POIFSFileSystem(new FileInputStream(file));
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(poifsFileSystem);
		HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);

		int rowstart = hssfSheet.getFirstRowNum()+1;
		int rowEnd = hssfSheet.getLastRowNum();
		for (int i = rowstart; i <= rowEnd; i++) {
			HSSFRow row = hssfSheet.getRow(i);
			if (null == row)
				continue;
			
			HSSFCell cell = row.getCell(0);			
			String patentId = cell.toString();
			//System.out.println(patentId);
			
			cell = row.getCell(4);
			String richText = cell.getRichStringCellValue().getString();
			String chineseAbstract = richText.substring(richText.indexOf("|")+1, richText.length());
			list.add(new Patent(patentId,chineseAbstract,field));
		}

		return list;

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			PatentFileReader.getAbstracts("D:\\Project\\TermExtractor\\电动汽车.XLS",PatentConstants.ELECTRIC_CAR);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
