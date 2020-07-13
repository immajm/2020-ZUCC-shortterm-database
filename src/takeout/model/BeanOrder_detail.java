package takeout.model;

public class BeanOrder_detail {
	private String order_id;//把E-R图里的order_id放最前面这样方便看
	private String pro_id;
	private int single_quantity;
	private double single_cost;
	private double single_discount;
	public String getPro_id() {
		return pro_id;
	}
	public void setPro_id(String pro_id) {
		this.pro_id = pro_id;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public int getSingle_quantity() {
		return single_quantity;
	}
	public void setSingle_quantity(int single_quantity) {
		this.single_quantity = single_quantity;
	}
	public double getSingle_cost() {
		return single_cost;
	}
	public void setSingle_cost(double single_cost) {
		this.single_cost = single_cost;
	}
	public double getSingle_discount() {
		return single_discount;
	}
	public void setSingle_discount(double single_discount) {
		this.single_discount = single_discount;
	}
	
	
}
