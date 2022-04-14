package springold.base.version;

import java.util.Date;


/**
 * 对象是带有版本标识的接口。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface Versionable {
	
	/**
	 * 获取创建时间。
	 * @return
	 */
	Date getCreationTime();
	/**
	 * 设置创建时间。
	 * @param creationTime
	 */
	void setCreationTime(Date creationTime);
	/**
	 * 获取修改时间。
	 * @return
	 */
	Date getModificationTime();
	/**
	 * 设置修改时间。
	 * @param modificationTime
	 */
	void setModificationTime(Date modificationTime);

}
