package takeout.model;

import java.sql.Date;
import java.sql.Timestamp;

public class BeanOrder_all {
	private String order_id;
	private String shop_id;
	private String address_id;
	private String cus_id;
	private String coupon_id;//物理模型里面是int型，如果数据库重装，需要手动修改为varchar(20)
	private double original_cost;
	private double final_cost;
	private Timestamp order_time;
	private Timestamp reachtime;
	private String order_state;
	private String rider_id;
	private int full_id;
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
	public String getAddress_id() {
		return address_id;
	}
	public void setAddress_id(String address_id) {
		this.address_id = address_id;
	}
	public String getCus_id() {
		return cus_id;
	}
	public void setCus_id(String cus_id) {
		this.cus_id = cus_id;
	}
	public String getCoupon_id() {
		return coupon_id;
	}
	public void setCoupon_id(String coupon_id) {
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
	
	public Timestamp getOrder_time() {
		return order_time;
	}
	public void setOrder_time(Timestamp order_time) {
		this.order_time = order_time;
	}
	public Timestamp getReachtime() {
		return reachtime;
	}
	public void setReachtime(Timestamp reachtime) {
		this.reachtime = reachtime;
	}
	public String getOrder_state() {
		return order_state;
	}
	public void setOrder_state(String order_state) {
		this.order_state = order_state;
	}
	public String getRider_id() {
		return rider_id;
	}
	public void setRider_id(String rider_id) {
		this.rider_id = rider_id;
	}
	public int getFull_id() {
		return full_id;
	}
	public void setFull_id(int full_id) {
		this.full_id = full_id;
	}
	
	
	
}
