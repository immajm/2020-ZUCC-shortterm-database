package takeout.model;

import java.util.Date;


public class BeanUser {
	private String user_id;
	private String user_pwd;
	private Date register_time;
	
	
	public String getUser_id() {
		return user_id;
	}


	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}


	public String getUser_pwd() {
		return user_pwd;
	}


	public void setUser_pwd(String user_pwd) {
		this.user_pwd = user_pwd;
	}


	public Date getRegister_time() {
		return register_time;
	}


	public void setRegister_time(Date register_time) {
		this.register_time = register_time;
	}
	

	public static BeanUser getCurrentLoginUser() {
		return currentLoginUser;
	}


	public void setCurrentLoginUser(BeanUser currentLoginUser) {
		BeanUser.currentLoginUser = currentLoginUser;
	}


	public static BeanUser currentLoginUser=null;
	
}
