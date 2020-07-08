package takeout.model;

public class BeanFull {
	private String full_id;
	private String shop_id;
	private String order_id;//这一条可以不要把，应该在订单里加full
	private double full_demand;//在数据库和ER图里还没有改名字
	private double full_reduction;
	private String tag;
	public String getFull_id() {
		return full_id;
	}
	public void setFull_id(String full_id) {
		this.full_id = full_id;
	}
	public String getShop_id() {
		return shop_id;
	}
	public void setShop_id(String shop_id) {
		this.shop_id = shop_id;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public double getFull_demand() {
		return full_demand;
	}
	public void setFull_demand(double full_demand) {
		this.full_demand = full_demand;
	}
	public double getFull_reduction() {
		return full_reduction;
	}
	public void setFull_reduction(double full_reduction) {
		this.full_reduction = full_reduction;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	
	
	
	
	
	
	
}
