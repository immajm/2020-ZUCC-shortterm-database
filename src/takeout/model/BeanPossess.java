package takeout.model;

import java.sql.Date;

public class BeanPossess {
	private String coupon_id;
	private String cus_id;
	private String shop_id;
	private double discount;
	private int quantity;
	private Date coupon_endtime;
	public String getCoupon_id() {
		return coupon_id;
	}
	public void setCoupon_id(String coupon_id) {
		this.coupon_id = coupon_id;
	}
	public String getCus_id() {
		return cus_id;
	}
	public void setCus_id(String cus_id) {
		this.cus_id = cus_id;
	}
	public String getShop_id() {
		return shop_id;
	}
	public void setShop_id(String shop_id) {
		this.shop_id = shop_id;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Date getCoupon_endtime() {
		return coupon_endtime;
	}
	public void setCoupon_endtime(Date coupon_endtime) {
		this.coupon_endtime = coupon_endtime;
	}
	
	
	
}
