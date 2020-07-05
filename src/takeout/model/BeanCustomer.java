package takeout.model;

import java.sql.Date;

public class BeanCustomer {
	private int cus_id;
	private String cus_name;
	private String sex;
	private String cus_pwd;
	private String tel;
	private String mailbox;
	private String city;
	private Date reg_time;
	private String vip_state;
	private Date vip_endtime;
	public int getCus_id() {
		return cus_id;
	}
	public void setCus_id(int cus_id) {
		this.cus_id = cus_id;
	}
	public String getCus_name() {
		return cus_name;
	}
	public void setCus_name(String cus_name) {
		this.cus_name = cus_name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getCus_pwd() {
		return cus_pwd;
	}
	public void setCus_pwd(String cus_pwd) {
		this.cus_pwd = cus_pwd;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getMailbox() {
		return mailbox;
	}
	public void setMailbox(String mailbox) {
		this.mailbox = mailbox;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Date getReg_time() {
		return reg_time;
	}
	public void setReg_time(Date reg_time) {
		this.reg_time = reg_time;
	}
	public String getVip_state() {
		return vip_state;
	}
	public void setVip_state(String vip_state) {
		this.vip_state = vip_state;
	}
	public Date getVip_endtime() {
		return vip_endtime;
	}
	public void setVip_endtime(Date vip_endtime) {
		this.vip_endtime = vip_endtime;
	}
	
	
}
