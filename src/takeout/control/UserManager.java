package takeout.control;

import java.sql.Connection;
import java.sql.SQLException;

import takeout.model.BeanUser;
import takeout.util.BaseException;
import takeout.util.DBUtil;
import takeout.util.DbException;

public class UserManager {
	public static BeanUser currentUser=null;
	
	public BeanUser login(String user_id, String pwd) throws BaseException {
		Connection conn=null;
		BeanUser bu = new BeanUser();
		try {
			conn=DBUtil.getConnection();
			 //conn = DBUtil_Pool.getConnection();
			String sql="select admin_id from admin where admin_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, user_id);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BaseException("用户id不存在");
			rs.close();
			pst.close();
			
			sql="select pwd from admin where admin_id=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1,user_id);
			rs=pst.executeQuery();
			if(rs.next()) {
				if(!(rs.getString(1).equals(pwd))) {
					throw new BaseException("密码错误");
				}
				else {
					rs.close(); 
					sql="select * from tbl_user where user_id='"+user_id+"'";
					java.sql.Statement st= conn.createStatement();
					rs=st.executeQuery(sql);
					if(rs.next()) {
						bu.setUser_id(user_id);
						bu.setPwd(pwd);
					}
				}
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
		return bu;
	}
	/*1管理员2商家3客户4骑手*/
	public BeanUser loadUser(int type,String userid)throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			if(type==1) {
				String sql="select admin_id,pwd from Admin where admin_id=?";
				java.sql.PreparedStatement pst=conn.prepareStatement(sql);
				pst.setString(1,userid);
				java.sql.ResultSet rs=pst.executeQuery();
				if(!rs.next()) throw new BaseException("登陆账号不存在");
				BeanUser u=new BeanUser();
				u.setUser_id(rs.getString(1));
				u.setPwd(rs.getString(2));
				u.setType("管理员");
				rs.close();
				pst.close();
				return u;
			}else if(type==2) {
				String sql="select shop_id,shop_pwd from Shop where shop_id=?";
				java.sql.PreparedStatement pst=conn.prepareStatement(sql);
				pst.setString(1,userid);
				java.sql.ResultSet rs=pst.executeQuery();
				if(!rs.next()) throw new BaseException("登陆账号不存在");
				BeanUser u=new BeanUser();
				u.setUser_id(rs.getString(1));
				u.setPwd(rs.getString(2));
				u.setType("商家");
				rs.close();
				pst.close();
				return u;
			}else if(type==3) {
				String sql="select cus_id,cus_pwd from Customer where cus_id=?";
				java.sql.PreparedStatement pst=conn.prepareStatement(sql);
				pst.setString(1,userid);
				java.sql.ResultSet rs=pst.executeQuery();
				if(!rs.next()) throw new BaseException("登陆账号不存在");
				BeanUser u=new BeanUser();
				u.setUser_id(rs.getString(1));
				u.setPwd(rs.getString(2));
				u.setType("客户");
				rs.close();
				pst.close();
				return u;
			}else {
				String sql="select rider_id,rider_pwd from Rider where rider_id=?";
				java.sql.PreparedStatement pst=conn.prepareStatement(sql);
				pst.setString(1,userid);
				java.sql.ResultSet rs=pst.executeQuery();
				if(!rs.next()) throw new BaseException("登陆账号不存在");
				BeanUser u=new BeanUser();
				u.setUser_id(rs.getString(1));
				u.setPwd(rs.getString(2));
				u.setType("骑手");
				rs.close();
				pst.close();
				return u;
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
		
	}


}
