package takeout.model;

import java.sql.Date;

public class BeanCoupon {
	private String coupon_id;
	private String order_id;
	private String shop_id;
	private double discount;
	private int order_quantity;
	private Date coupon_starttime;
	private Date coupon_endtime;
	
	public String getCoupon_id() {
		return coupon_id;
	}
	public void setCoupon_id(String coupon_id) {
		this.coupon_id = coupon_id;
	}
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
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public int getOrder_quantity() {
		return order_quantity;
	}
	public void setOrder_quantity(int order_quantity) {
		this.order_quantity = order_quantity;
	}
	public Date getCoupon_starttime() {
		return coupon_starttime;
	}
	public void setCoupon_starttime(Date coupon_starttime) {
		this.coupon_starttime = coupon_starttime;
	}
	public Date getCoupon_endtime() {
		return coupon_endtime;
	}
	public void setCoupon_endtime(Date coupon_endtime) {
		this.coupon_endtime = coupon_endtime;
	}
	
	
}
