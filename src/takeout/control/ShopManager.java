package takeout.control;

import java.sql.Connection;
import java.sql.SQLException;


import takeout.itf.IShopManager;
import takeout.model.BeanAdmin;
import takeout.model.BeanShop;
import takeout.util.BaseException;
import takeout.util.DBUtil;
import takeout.util.DbException;

public class ShopManager implements IShopManager{
	public BeanShop reg(String id, String name,String pwd,String pwd2) throws BaseException {
		Connection conn=null;
		BeanShop bu=new BeanShop();
		try {
			conn=DBUtil.getConnection();
			 //conn = DBUtil_Pool.getConnection();
			if("".equals(id)) throw new BaseException("用户id不为空");
			if("".equals(name)) throw new BaseException("昵称不为空");
			
			String sql="select shop_id from shop where shop_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, id);
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BaseException("用户id已存在");
			rs.close();
			pst.close();
			
			if(!(pwd.equals(pwd2))) throw new BaseException("输入的密码必须相同");
			
			sql="insert into shop(shop_id,shop_name,shop_pwd) values (?,?,?)";
			pst=conn.prepareStatement(sql);
			pst.setString(1,id);
			pst.setString(2,name);
			pst.setString(3,pwd);
			pst.execute();
			pst.close();
			
			bu.setShop_id(id);
			bu.setShop_name(name);
			bu.setShop_pwd(pwd);
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	
		return bu;
	}

}
