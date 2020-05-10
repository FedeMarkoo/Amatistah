package data.objects;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Menu {

	@Id	
	private Integer menuID;
	private String descript;
	private String url;
	private String title;

	public Menu() {
	}
	
	public Menu(Menu menu) {
		this(menu.menuID, menu.descript, menu.url, menu.title);
	}
	
	public Menu(Integer menuID, String descript, String url, String title) {
		super();
		this.menuID = menuID;
		this.descript = descript;
		this.url = url;
		this.title = title;
	}

	public Integer getMenuID() {
		return menuID;
	}
	public String getDescript() {
		return descript;
	}
	public String getDestination() {
		return url;
	} 
	public void setMenuID(Integer menuID) {
		this.menuID = menuID;
	}
	public void setDescript(String descript) {
		this.descript = descript;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
