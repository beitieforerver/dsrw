package springold.base.version;

import java.util.Date;


/**
 * @Description: 版本控制实体类
 * @Author: Weitj
 * @Date: 2022/01/20 19:03
 */
public abstract class VersionableBean implements VersionLogable, Versionable{
	
	private static final long serialVersionUID = -3844665144002142247L;
	private Date creationTime;
	private Date modificationTime;
	
	
	/**
	 * 创建者。
	 */
	private Long creator;
	/**
	 * 修改者。
	 */
	private Long modifier;
	/**
	 * 创建者的标识。
	 * @return Long
	 */
	public Long getCreator() {
		return creator;
	}
	
	/**
	 * @param creator
	 */
	public void setCreator(Long creator) {
		this.creator = creator;
	}
	/**
	 * 修改者的标识。
	 * @return Long
	 */
	public Long getModifier() {
		return modifier;
	}
	public void setModifier(Long modifier) {
		this.modifier = modifier;
	}

	/**
	 * @return the creationTime
	 */
	public Date getCreationTime() {
		return creationTime;
	}

	/**
	 * @param creationTime the creationTime to set
	 */
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	/**
	 * @return the modificationTime
	 */
	public Date getModificationTime() {
		return modificationTime;
	}

	/**
	 * @param modificationTime the modificationTime to set
	 */
	public void setModificationTime(Date modificationTime) {
		this.modificationTime = modificationTime;
	}	
	
	public String getCreatorName(){
		if(getCreator() != null && isVersionUpdatorNameParsed()){
			return getClerkName(getCreator());
		}
		return null;
	}
	
	public String getModifierName(){
		if(getModifier() != null && isVersionUpdatorNameParsed()){
			return getClerkName(getModifier());
		}
		return null;
	}
	
	/**
	 * @Description: 获取当前用户的name
	 * @Author: Weitj
	 * @Date: 2022/01/20 19:01
	  * @param clerkId
	 * @return: java.lang.String
	 */
	private String getClerkName(Long clerkId) {

		return clerkId +"";
	}

	private boolean isVersionUpdatorNameParsed(){
		return true;
	}
}
