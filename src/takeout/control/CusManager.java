package takeout.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import takeout.model.BeanCustomer;
import takeout.model.BeanOrder_detail;
import takeout.model.BeanProduct;
import takeout.ui.FrmCustomer;
import takeout.util.BaseException;
import takeout.util.BusinessException;
import takeout.util.DBUtil;
import takeout.util.DbException;

public class CusManager {
	public void delete(String id) throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql = "delete from customer where cus_id=? ";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,id);
			pst.execute();
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new DbException(e1);
		}
	}
	public List<BeanCustomer> loadAll()throws BaseException{
		List<BeanCustomer> result=new ArrayList<BeanCustomer>();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select cus_id,cus_name,cus_pwd from customer";
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			while(rs.next()){
				BeanCustomer u=new BeanCustomer();
				u.setCus_id(rs.getString(1));
				u.setCus_name(rs.getString(2));
				u.setCus_pwd(rs.getString(3));
				result.add(u);
			}
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
		return result;
	}
	
	
	public BeanCustomer reg(String id, String name,String pwd,String pwd2) throws BaseException {
		Connection conn=null;
		BeanCustomer bu=new BeanCustomer();
		try {
			conn=DBUtil.getConnection();
			 //conn = DBUtil_Pool.getConnection();
			if("".equals(id)) throw new BaseException("用户id不为空");
			if("".equals(name)) throw new BaseException("昵称不为空");
			
			String sql="select cus_id from customer where cus_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, id);
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BaseException("用户id已存在");
			rs.close();
			pst.close();
			
			if(!(pwd.equals(pwd2))) throw new BaseException("输入的密码必须相同");
			
			sql="insert into customer(cus_id,cus_name,cus_pwd) values (?,?,?)";
			pst=conn.prepareStatement(sql);
			pst.setString(1,id);
			pst.setString(2,name);
			pst.setString(3,pwd);
			pst.execute();
			pst.close();
			
			bu.setCus_id(id);
			bu.setCus_name(name);
			bu.setCus_pwd(pwd);
			
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
