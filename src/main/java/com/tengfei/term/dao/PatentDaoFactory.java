/**
 * 
 */
package com.tengfei.term.dao;

import com.tengfei.term.dao.impl.PatentDaoImpl;

/**
 * @author Administrator
 *
 */
public class PatentDaoFactory {

	public static PatentDao getInstance() {
		PatentDao dao = null;
		try {
			dao = new PatentDaoImpl();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dao;
	}

}
