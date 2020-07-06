package takeout.itf;

import takeout.model.BeanAdmin;
import takeout.util.BaseException;

public interface IAdminManager {
	/*
	 * 注册
	 * 要求用户名不能重复，不能为空
	 * 两次输入的密码必须一致，密码不能为空
	 * 如果注册失败，则抛出异常
	 * 
	 */
	public BeanAdmin reg(String admin_id, String admin_name,String pwd,String pwd2) throws BaseException;
	
}
