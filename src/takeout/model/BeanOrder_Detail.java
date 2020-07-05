package takeout.model;

import java.sql.Date;

public class BeanOrder_Detail {
	private int order_id;
	private int shop_id;
	private int pro_id;
	private String address_id;
	private int cus_id;
	private int coupon_id;
	private double original_cost;
	private double final_cost;
	private Date order_time;
	private Date reachtime;
	private String order_state;
	private int rider_id;
	private int full_id;
	private int quantity;
	private double price;
	private double one_discount;
	
	
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
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
	public String getAddress_id() {
		return address_id;
	}
	public void setAddress_id(String address_id) {
		this.address_id = address_id;
	}
	public int getCus_id() {
		return cus_id;
	}
	public void setCus_id(int cus_id) {
		this.cus_id = cus_id;
	}
	public int getCoupon_id() {
		return coupon_id;
	}
	public void setCoupon_id(int coupon_id) {
		this.coupon_id = coupon_id;
	}
	public double getOriginal_cost() {
		return original_cost;
	}
	public void setOriginal_cost(double original_cost) {
		this.original_cost = original_cost;
	}
	public double getFinal_cost() {
		return final_cost;
	}
	public void setFinal_cost(double final_cost) {
		this.final_cost = final_cost;
	}
	public Date getOrder_time() {
		return order_time;
	}
	public void setOrder_time(Date order_time) {
		this.order_time = order_time;
	}
	public Date getReachtime() {
		return reachtime;
	}
	public void setReachtime(Date reachtime) {
		this.reachtime = reachtime;
	}
	public String getOrder_state() {
		return order_state;
	}
	public void setOrder_state(String order_state) {
		this.order_state = order_state;
	}
	public int getRider_id() {
		return rider_id;
	}
	public void setRider_id(int rider_id) {
		this.rider_id = rider_id;
	}
	public int getFull_id() {
		return full_id;
	}
	public void setFull_id(int full_id) {
		this.full_id = full_id;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getOne_discount() {
		return one_discount;
	}
	public void setOne_discount(double one_discount) {
		this.one_discount = one_discount;
	}
	
	
}
