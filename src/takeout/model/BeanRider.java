package takeout.model;

import java.sql.Date;

public class BeanRider {
	
	private String rider_id;
	private String rider_name;
	private String rider_pwd;//sql中没有添加pwd项哦
	private Date entry_date;
	private String position;
	
	public String getRider_id() {
		return rider_id;
	}
	public void setRider_id(String rider_id) {
		this.rider_id = rider_id;
	}
	public String getRider_name() {
		return rider_name;
	}
	public void setRider_name(String rider_name) {
		this.rider_name = rider_name;
	}
	public String getRider_pwd() {
		return rider_pwd;
	}
	public void setRider_pwd(String rider_pwd) {
		this.rider_pwd = rider_pwd;
	}
	public Date getEntry_date() {
		return entry_date;
	}
	public void setEntry_date(Date entry_date) {
		this.entry_date = entry_date;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
	

	
}
