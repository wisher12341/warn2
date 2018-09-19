/**
 * 
 */
package com.warn.entity;

import java.util.List;

/**
 * 菜单
 */
public class Menu {
	/**
	 * 菜单ID
	 */
	private Integer id;
	/**
	 * 菜单名称
	 */
	private String name;
	/**
	 * 父级菜单ID 0表示根节点
	 */
	private Integer parentid;
	/**
	 * 菜单顺序
	 */
	private String sequence;
	/**
	 * 菜单图标样式
	 */
	private String iconCls;	
	/**
	 * 菜单链接地址
	 */
	private String url;
	/**
	 * 可用状态
	 */
	private Integer enable;
	/**
	 * 子节点个数
	 */
	private Integer countChildrens;
	
	private Menu parentMenu; //父节点
	private List<Menu> subMenu; //子节点
	private boolean hasMenu = false;
	
	public Integer getCountChildrens() {
		return countChildrens;
	}
	public void setCountChildrens(Integer countChildrens) {
		this.countChildrens = countChildrens;
	}
	public Integer getEnable() {
		return enable;
	}
	public void setEnable(Integer enable) {
		this.enable = enable;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getParentid() {
		return parentid;
	}
	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}
	public String getSequence() {
		return sequence;
	}
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Menu getParentMenu() {
		return parentMenu;
	}
	public void setParentMenu(Menu parentMenu) {
		this.parentMenu = parentMenu;
	}
	public List<Menu> getSubMenu() {
		return subMenu;
	}
	public void setSubMenu(List<Menu> subMenu) {
		this.subMenu = subMenu;
	}
	public boolean isHasMenu() {
		return hasMenu;
	}
	public void setHasMenu(boolean hasMenu) {
		this.hasMenu = hasMenu;
	}

	@Override
	public boolean equals(Object obj) {
		Menu menu= (Menu) obj;
		return this.getId().intValue()==menu.getId().intValue();
		//不能直接  this.getId() 毕竟 不然超过201的id就无法相等
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = result * 31 + this.getId().hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "Menu{" +
				"id=" + id +
				", name='" + name + '\'' +
				", parentid=" + parentid +
				", sequence='" + sequence + '\'' +
				", iconCls='" + iconCls + '\'' +
				", url='" + url + '\'' +
				", enable=" + enable +
				", countChildrens=" + countChildrens +
				", parentMenu=" + parentMenu +
				", subMenu=" + subMenu +
				", hasMenu=" + hasMenu +
				'}';
	}
}
