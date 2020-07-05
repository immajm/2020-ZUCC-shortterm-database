package takeout.model;

public class BeanProduct {
	private int pro_id;
	private int pro_type_id;
	private int type_id;
	private String pro_name;
	private double pro_price;
	private double pro_discount_amount;
	
	public int getPro_id() {
		return pro_id;
	}
	public void setPro_id(int pro_id) {
		this.pro_id = pro_id;
	}
	public int getPro_type_id() {
		return pro_type_id;
	}
	public void setPro_type_id(int pro_type_id) {
		this.pro_type_id = pro_type_id;
	}
	public int getType_id() {
		return type_id;
	}
	public void setType_id(int type_id) {
		this.type_id = type_id;
	}
	public String getPro_name() {
		return pro_name;
	}
	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}
	public double getPro_price() {
		return pro_price;
	}
	public void setPro_price(double pro_price) {
		this.pro_price = pro_price;
	}
	public double getPro_discount_amount() {
		return pro_discount_amount;
	}
	public void setPro_discount_amount(double pro_discount_amount) {
		this.pro_discount_amount = pro_discount_amount;
	}
	
	
}
