package takeout.itf;

import takeout.model.BeanAdmin;
import takeout.util.BaseException;

public interface IAdminManager {
	/*
	 * ע��
	 * Ҫ���û��������ظ�������Ϊ��
	 * ����������������һ�£����벻��Ϊ��
	 * ���ע��ʧ�ܣ����׳��쳣
	 * 
	 */
	public BeanAdmin reg(String admin_id, String admin_name,String pwd,String pwd2) throws BaseException;
	
}
