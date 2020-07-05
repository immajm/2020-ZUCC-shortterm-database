package takeout.model;

import java.sql.Date;

public class BeanPossess {
	private int coupon_id;
	private int cus_id;
	private int shop_id;
	private double discount;
	private int quantity;
	private Date coupon_endtime;
	
	public int getCoupon_id() {
		return coupon_id;
	}
	public void setCoupon_id(int coupon_id) {
		this.coupon_id = coupon_id;
	}
	public int getCus_id() {
		return cus_id;
	}
	public void setCus_id(int cus_id) {
		this.cus_id = cus_id;
	}
	public int getShop_id() {
		return shop_id;
	}
	public void setShop_id(int shop_id) {
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
