package takeout.itf;


import takeout.model.BeanShop;
import takeout.util.BaseException;

public interface IShopManager {
	/*
	 * ע��
	 * Ҫ���û��������ظ�������Ϊ��
	 * ����������������һ�£����벻��Ϊ��
	 * ���ע��ʧ�ܣ����׳��쳣
	 * 
	 */
	public BeanShop reg(String id, String name,String pwd,String pwd2) throws BaseException ;

}
