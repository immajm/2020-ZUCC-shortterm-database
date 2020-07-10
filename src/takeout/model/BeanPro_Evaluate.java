package takeout.model;

import java.sql.Blob;
import java.sql.Date;

public class BeanPro_Evaluate {
	private int shop_id;
	private int pro_id;
	private int cus_id;
	private String comment;
	private Date comment_date;
	private int pro_level;
	private Blob pro_photo;
	
	public int getShop_id() {
		return shop_id;
	}
	public void setShop_id(int shop_id) {
		this.shop_id = shop_id;
	}
	public int getPro_id() {
		return pro_id;
	}
	public void setPro_id(int pro_id) {
		this.pro_id = pro_id;
	}
	public int getCus_id() {
		return cus_id;
	}
	public void setCus_id(int cus_id) {
		this.cus_id = cus_id;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getComment_date() {
		return comment_date;
	}
	public void setComment_date(Date comment_date) {
		this.comment_date = comment_date;
	}
	public int getPro_level() {
		return pro_level;
	}
	public void setPro_level(int pro_level) {
		this.pro_level = pro_level;
	}
	public Blob getPro_photo() {
		return pro_photo;
	}
	public void setPro_photo(Blob pro_photo) {
		this.pro_photo = pro_photo;
	}
	
	
}
