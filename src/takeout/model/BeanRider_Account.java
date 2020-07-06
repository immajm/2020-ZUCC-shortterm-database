package takeout.model;

import java.sql.Date;

public class BeanRider_Account {
	private String order_id;
	private String rider_id;
	private Date recordtime;
	private String rider_evaluate;
	private double income;
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getRider_id() {
		return rider_id;
	}
	public void setRider_id(String rider_id) {
		this.rider_id = rider_id;
	}
	public Date getRecordtime() {
		return recordtime;
	}
	public void setRecordtime(Date recordtime) {
		this.recordtime = recordtime;
	}
	public String getRider_evaluate() {
		return rider_evaluate;
	}
	public void setRider_evaluate(String rider_evaluate) {
		this.rider_evaluate = rider_evaluate;
	}
	public double getIncome() {
		return income;
	}
	public void setIncome(double income) {
		this.income = income;
	}
	
	
	
}
