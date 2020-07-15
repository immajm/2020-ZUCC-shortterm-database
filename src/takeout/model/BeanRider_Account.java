package takeout.model;

import java.sql.Date;
import java.sql.Timestamp;

public class BeanRider_Account {
	
	private String rider_id;
	private String order_id;
	private Timestamp recordtime;
	private String rider_evaluate;//not null È¥µô
	private double income;//not null È¥µô
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
	public Timestamp getRecordtime() {
		return recordtime;
	}
	public void setRecordtime(Timestamp timestamp) {
		this.recordtime = timestamp;
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
