/*
 * $Id: Glossary.java 6172 2013-01-10 03:04:16Z lcj $
 * Glossary.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package springold.bean;

import springold.base.version.VersionableBean;

/**
 * @author mwx
 *
 */
public class Glossary extends VersionableBean {
	/**
	 * 
	 */
	
	public static final short CATEGORY_AFFAIR = 101;         //业务事别
	public static final short CATEGORY_MATTER = 102;         //业务事项	
	public static final short CATEGORY_BIZTYPE=110;          //业务类别
	public static final short CATEGORY_JOBTYPE=111;          //作业类型
	public static final short CATEGORY_TASKTYPE=112;         //任务类型
	public static final short CATEGORY_WORKTYPE=113;         //工作类型
	public static final short CATEGORY_PROCESSTYPE=114;      //事务类型

	private static final long serialVersionUID = -9021132824200466813L;
	private Long rn;
	private short category;
	private short displayOrder;
	private int code;
	private String term;
	/**
	 * 唯一标示
	 * 
	 * @return Long
	 */
	public Long getId() {
		return getRn();
	}
	
	/**
	 * @param id
	 */
	public void setId(Long id) {
		setRn(id);
	}
	/**
	 * @return the rn
	 */
	public Long getRn() {
		return rn;
	}
	/**记录编号
	 * @param rn the rn to set
	 */
	public void setRn(Long rn) {
		this.rn = rn;
	}
	/**
	 * @return the category
	 */
	public short getCategory() {
		return category;
	}
	/**
	 * 种类
	 * 
	 * 默认为0，表示忽略
	 * @param category the category to set
	 */
	public void setCategory(short category) {
		this.category = category;
	}
	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}
	/**
	 * 代码
	 * 
	 * 不空
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}
	/**
	 * @return the displayOrder
	 */
	public short getDisplayOrder() {
		return displayOrder;
	}
	/**
	 * 顺序
	 * 
	 * 默认为0，表示忽略
	 * @param displayOrder the displayOrder to set
	 */
	public void setDisplayOrder(short displayOrder) {
		this.displayOrder = displayOrder;
	}
	/**
	 * @return the term
	 */
	public String getTerm() {
		return term;
	}
	/**
	 * 名称
	 * 三十个汉字以内
	 * @param term the term to set
	 */
	public void setTerm(String term) {
		this.term = term;
	}
	
}
