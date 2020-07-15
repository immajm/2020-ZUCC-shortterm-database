package takeout.model;

import java.sql.Blob;
import java.sql.Date;
import java.sql.Timestamp;

public class BeanPro_Evaluate {
	private String order_id;
	private String shop_id;
	private String pro_id;
	private String cus_id;
	private String comment;
	private Timestamp comment_date;
	private int pro_level;
	private Blob pro_photo;//œ»≤ª∑≈
	
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getShop_id() {
		return shop_id;
	}
	public void setShop_id(String shop_id) {
		this.shop_id = shop_id;
	}
	public String getPro_id() {
		return pro_id;
	}
	public void setPro_id(String pro_id) {
		this.pro_id = pro_id;
	}
	public String getCus_id() {
		return cus_id;
	}
	public void setCus_id(String cus_id) {
		this.cus_id = cus_id;
	}
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Timestamp getComment_date() {
		return comment_date;
	}
	public void setComment_date(Timestamp timestamp) {
		this.comment_date = timestamp;
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
