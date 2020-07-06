package takeout.model;

public class BeanShop {
	private String shop_id;
	private String shop_name;
	private String shop_pwd;
	private int shop_level;
	private double avgcost;
	private int total_sale;
	
	
	public String getShop_id() {
		return shop_id;
	}
	public void setShop_id(String shop_id) {
		this.shop_id = shop_id;
	}
	public String getShop_name() {
		return shop_name;
	}
	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}
	
	public String getShop_pwd() {
		return shop_pwd;
	}
	public void setShop_pwd(String shop_pwd) {
		this.shop_pwd = shop_pwd;
	}
	public int getShop_level() {
		return shop_level;
	}
	public void setShop_level(int shop_level) {
		this.shop_level = shop_level;
	}
	public double getAvgcost() {
		return avgcost;
	}
	public void setAvgcost(double avgcost) {
		this.avgcost = avgcost;
	}
	public int getTotal_sale() {
		return total_sale;
	}
	public void setTotal_sale(int total_sale) {
		this.total_sale = total_sale;
	}
	
}
