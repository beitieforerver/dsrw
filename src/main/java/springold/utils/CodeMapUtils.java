/*
 * $Id: CodeMapUtils.java 6432 2014-10-27 09:02:24Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package springold.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import springold.anotation.GlossaryCategory;
import springold.anotation.Ignore;
import springold.bean.Glossary;
import springold.config.Application;
import springold.service.GlossaryService;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * 代码名称映射处理类。
 * 
 * <p>该类可以从静态字段中提取代码-名称映射信息，也可以从
 * glossary中读取代码-名称对应关系。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public abstract class CodeMapUtils {
	private static final Log log = LogFactory.getLog(CodeMapUtils.class);
	
	private static GlossaryService glossaryService;
	
	
	/**
	 * 缓存。
	 */
	private static Map<String, Map<String, String>> codeMaps = new HashMap<String, Map<String,String>>();
	private static Map<String, Map<String, Code>> codeValueMaps = new HashMap<String, Map<String,Code>>();
	

	/**
	 * 从指定类指定前缀的静态字段定义中或者指定code（代码）的名称。
	 * 
	 * <p>通常是通过{@link CodeMapUtils#getCodeMap(Class, String)}获取集合，再从集合中查找指定code的名称。
	 * 
	 * @param <T> 代码的类型
	 * @param clazz 指定的类型
	 * @param staticFieldPrefix 指定的静态字段前缀，通常是最后一个“_”之前的部分
	 * @param code 代码值
	 * @return 代码对应的名称
	 * @see CodeMapUtils#getCodeMap(Class, String)
	 */
	public static <T> String getCodeName(Class<?> clazz, String staticFieldPrefix, T code){
		if(code == null){
			return null;
		}

		Map<String, Code> map = CodeMapUtils.getAllCodeMap(clazz, staticFieldPrefix);
		if(map != null){
			Code c = map.get(code.toString());
			if(c != null){
				return c.getName();
			}
		}
		return code.toString();
	} 
	
	/**
	 * 
	 * @param clazz
	 * @param staticFieldPrefix
	 * @return
	 */
	public static Map<String, Code> getAllCodeMap(Class<?> clazz, String staticFieldPrefix){
		String key = clazz.getName() + "." + staticFieldPrefix;
		Map<String, Code> map = codeValueMaps.get(key);
		if(map == null){
			map = new LinkedHashMap<String, Code>();
			Field[] fields = clazz.getFields();
			String s = staticFieldPrefix + "_";
			int len = s.length();
			String name = null;
			for(Field f: fields){
				name = f.getName();
				if(name.startsWith(s)){
			
					GlossaryCategory gc = f.getAnnotation(GlossaryCategory.class);
					//System.out.println(">>>" + f + " :: " + gc);
					if(gc != null && gc.value() > 0){
						if(log.isDebugEnabled()){
							log.debug(String.format("属性 '%s' 含有注释 '%s'，不处理静态属性定义，查询Glossary中定义以替换。", f, gc));
						}
						
						Map<String, Code> map2 = getAllCodeMap(gc.value());
						if(map2 != null && !map2.isEmpty()){
							map = map2;
							break;//Jump out for
						}
					}
					

					
					
					try {
						Code codeValue = new Code(name.substring(len), f.get(null).toString(), Code.SOURCE_STATIC_FIELDS);
						if(f.getAnnotation(Ignore.class) != null){
							if(log.isDebugEnabled()){
								log.debug(String.format("属性 '%s' 含有注释 'Ignore'，不将该属性读取到最终的CodeMap中。", f));
							}
							codeValue.setStatus(Code.STATUS_IGNORE);
						}
						
						map.put(codeValue.getCode(), codeValue);
					} catch (Exception e) {
						throw new IllegalArgumentException(e);
					} 
				}
			}
			codeValueMaps.put(key, map);
		}
		return map;
	}
	
	



	/**
	 * 从指定类的特定前缀的静态字段中获取CodeMap（代码名称映射）集合。
	 * <p>通常在类中以public static final <TYPE>  staticFieldPrefix_<XXX>的形式来定义
	 * 静态字段。
	 * 
	 * <ol><b>注意两点：</b>
	 * <li>{@link Ignore}标注：当静态字段标记了这个注释时，该字段将不出现在最终的CodeMap
	 * 		集合中。
	 * <li>{@link GlossaryCategory}标注：当静态字段标记了这个注释时，CodeMap体系将首先根据
	 * 		标注提供的<code>category</code>在{@link Glossary}中查询CodeMap集合，当数据库的
	 * 		Glossary中没	有该category的配置时，才取静态字段的定义作为CodeMap集合。此时，静态
	 * 		字段的定义是作为一种缺省配置使用的。
	 * </ol>
	 * 
	 * @param clazz 指定的类
	 * @param staticFieldPrefix 指定的前缀字符串
	 * @return CodeMap（代码名称映射）集合
	 */
	public static Map<String, String> getCodeMap(Class<?> clazz, String staticFieldPrefix){
		return buildFilteredStringCodeMap(CodeMapUtils.getAllCodeMap(clazz, staticFieldPrefix));
	}
	
	/**
	 * @Description: 获取所有的CodeMap集合，包括被忽略的。
	 * @Author: Weitj
	 * @Date: 2022/01/24 9:10
	  * @param glossaryCategory 
	 * @return: java.util.Map<java.lang.String,springold.utils.CodeMapUtils.Code>
	 */
	public static Map<String, Code> getAllCodeMap(short glossaryCategory) {
		log.debug("从Glossary中查询数据：" + glossaryCategory);
		GlossaryService service = getGlossaryService();
		if(service == null){
			return null;
		}
		
		List<Glossary> list = service.findByCategory(glossaryCategory);
		Map<String, Code> map = new LinkedHashMap<String, Code>();
		if(list != null && !list.isEmpty()){
			for (Glossary g : list) {
				Code code = new Code(g.getTerm(), g.getCode() + "", Code.SOURCE_GLOSSARY);
				map.put(code.getCode(), code);
			}
		}
		return map;
	}
	
	/**
	 * @Description: 根据Glossary的category从Glossary查询CodeMap集合，Map类型。
	 * 	 集合有缓存，有顺序。
	 * @Author: Weitj
	 * @Date: 2022/01/24 9:31
	  * @param glossaryCategory 
	 * @return: java.util.Map<java.lang.String,java.lang.String>
	 */
	public static Map<String, String> getCodeMap(short glossaryCategory) {
		return buildFilteredStringCodeMap(CodeMapUtils.getAllCodeMap(glossaryCategory));
	}
	
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	private static Map<String, Code> buildFilteredCodeMap(Map<String, Code> map){
		if(map == null){
			return null;
		}
		
		Map<String, Code> result = new LinkedHashMap<String, Code>();
		for(Code code: map.values()){
			if(code.getStatus() == Code.STATUS_NORMAL){
				result.put(code.getCode(), code);
			}
		}
		return result;
	}
	
	private static Map<String, String> buildStringCodeMap(Map<String, Code> map){
		if(map == null){
			return null;
		}
		
		Map<String, String> result = new LinkedHashMap<String, String>();
		for(Code code: map.values()){
			result.put(code.getCode(), code.getName());
		}
		return result;
	}
	
	
	private static Map<String, String> buildFilteredStringCodeMap(Map<String, Code> map){
		map = buildFilteredCodeMap(map);
		return buildStringCodeMap(map);
	}


	
	/**
	 * 获取应用程序上下文中GlossaryService的实例。
	 * @return
	 */
	private static GlossaryService getGlossaryService(){
		if(Application.isInitialized){
			if(glossaryService == null){
				glossaryService = Application.getBean("glossaryService", GlossaryService.class);
			}
			
			if(glossaryService == null){
				log.warn("容器中找不到 glossaryServiceWrapper 的定义。");
				return null;
			}
			return glossaryService;
		}else{
			log.warn("应用程序或者容器没有初始化，无法获取glossaryService。");
			return null;
		}
	}
	
	/**
	 * 从 Glossary 中查询 指定代码对应的名称。
	 * 
	 * @param category Glossary的category
	 * @param code 指定的代码
	 * @return 代码对应的名称
	 * @see GlossaryService#getByCategoryAndCode(short, int)
	 */
	public static <T> String getCodeName(short category, T code){
		if(code == null){
			return null;
		}
		int c = Integer.MIN_VALUE;
		if(code instanceof Number){
			c = ((Number)code).intValue();
		}else{
			try {
				c = Integer.parseInt(code.toString());
			} catch (NumberFormatException e) {
				log.warn("无法将code转成int类型：" + code);
//				c = Integer.MIN_VALUE;
				return code.toString();
			}
		}
		
		if(c == Integer.MIN_VALUE){
			return null;
		}
		
		getGlossaryService();
		if(glossaryService != null){
			return glossaryService.getByCategoryAndCode(category, c);
		}else{
			log.warn("应用程序或者容器没有初始化，无法从Glossary中获取编码对照数据。");
			return null;
		}
	}
	
	/**
	 * 清理CodeMap体系中的缓存。
	 * 
	 */
	public static void clearCache(){
		codeMaps.clear();
	}

	
	public static class Code implements Serializable{
		private static final long serialVersionUID = 1481143585434417322L;
		
		public static final int SOURCE_UNKNOWN = 0;
		public static final int SOURCE_STATIC_FIELDS = 1;
		public static final int SOURCE_GLOSSARY = 2;
		
		public static final int STATUS_NORMAL = 0;
		public static final int STATUS_IGNORE = 1;
		
		private String name;
		private String code;
		private int source = SOURCE_UNKNOWN;
		private int status = STATUS_NORMAL;
		
		public Code(String name, String code, int source){
			super();
			this.name = name;
			this.code = code;
			this.source = source;
		}
		/**
		 * @param name
		 * @param code
		 * @param source
		 * @param status
		 */
		public Code(String name, String code, int source, int status) {
			super();
			this.name = name;
			this.code = code;
			this.source = source;
			this.status = status;
		}
		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}
		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}
		/**
		 * @return the code
		 */
		public String getCode() {
			return code;
		}
		/**
		 * @param code the code to set
		 */
		public void setCode(String code) {
			this.code = code;
		}
		/**
		 * @return the source
		 */
		public int getSource() {
			return source;
		}
		/**
		 * @param source the source to set
		 */
		public void setSource(int source) {
			this.source = source;
		}
		/**
		 * @return the status
		 */
		public int getStatus() {
			return status;
		}
		/**
		 * @param status the status to set
		 */
		public void setStatus(int status) {
			this.status = status;
		}
		/* (non-Javadoc)
		 * @see org.opoo.apps.Labelable#getData()
		 */
		public Serializable getData() {
			return code;
		}
		/* (non-Javadoc)
		 * @see org.opoo.apps.Labelable#getLabel()
		 */
		public String getLabel() {
			return name;
		}
		
		public String toString(){
			return name + ":" + code;
		}
	}

}
