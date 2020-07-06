package takeout.model;

public class BeanUser {
	private String user_id;
	private String user_name;
	private String pwd;
	private String type;
	private String sex;
	private String tel;
	private String city;
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public static BeanUser getCurrentLoginUser() {
		return currentLoginUser;
	}
	public void setCurrentLoginUser(BeanUser currentLoginUser) {
		BeanUser.currentLoginUser = currentLoginUser;
	}
	public static BeanUser currentLoginUser=null;
	
}
