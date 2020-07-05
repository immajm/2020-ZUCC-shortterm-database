package takeout.control;

import java.sql.Connection;
import java.sql.SQLException;

import takeout.itf.IUserManager;
import takeout.model.BeanUser;
import takeout.util.BaseException;
import takeout.util.DBUtil;
//import takeout.util.DBUtil_Pool;
import takeout.util.DbException;

public class UserManager implements IUserManager {
	public BeanUser reg(String userid, String pwd,String pwd2) throws BaseException {
		Connection conn=null;
		BeanUser bu=new BeanUser();
		try {
			conn=DBUtil.getConnection();
			 //conn = DBUtil_Pool.getConnection();
			if("".equals(userid)) throw new BaseException("用户名不为空");
			
			String sql="select user_id from tbl_user where user_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, userid);
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BaseException("用户名已存在");
			rs.close();
			pst.close();
			
			if(!(pwd.equals(pwd2))) throw new BaseException("输入的密码必须相同");
			
			sql="insert into tbl_user(user_id,user_pwd,register_time) values (?,?,?)";
			pst=conn.prepareStatement(sql);
			pst.setString(1,userid);
			pst.setString(2,pwd);
			pst.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));
			pst.execute();
			
			bu.setUser_id(userid);
			bu.setUser_pwd(pwd);
			bu.setRegister_time(new java.sql.Timestamp(System.currentTimeMillis()));
			pst.close();
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

	
	public BeanUser login(String userid, String pwd) throws BaseException {
		Connection conn=null;
		BeanUser bu = new BeanUser();
		try {
			conn=DBUtil.getConnection();
			 //conn = DBUtil_Pool.getConnection();
			String sql="select user_id from tbl_user where user_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, userid);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BaseException("用户不存在");
			rs.close();
			pst.close();
			
			sql="select user_pwd from tbl_user where user_id=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1,userid);
			rs=pst.executeQuery();
			while(rs.next()) {
				if(!(rs.getString(1).equals(pwd))) {
					throw new BaseException("密码错误");
				}
				else {
					rs.close(); 
					sql="select * from tbl_user where user_id='"+userid+"'";
					java.sql.Statement st= conn.createStatement();
					rs=st.executeQuery(sql);
					if(rs.next()) {
						bu.setUser_id(rs.getString(1));
						bu.setUser_pwd(rs.getString(2));
						bu.setRegister_time(rs.getDate(3));
						bu.setCurrentLoginUser(BeanUser.currentLoginUser);
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


	
	public void changePwd(BeanUser user, String oldPwd, String newPwd,String newPwd2) throws BaseException {
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			// conn = DBUtil_Pool.getConnection();
			String sql="select user_id from tbl_user where user_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, user.getUser_id());
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BaseException("用户不存在");
			rs.close();
			pst.close();
			
			sql="select user_pwd from tbl_user where user_id=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1, user.getUser_id());
			rs=pst.executeQuery();
			while(rs.next()) {
				if(!(rs.getString(1).equals(oldPwd))) throw new BaseException("原密码错误");
			}
			rs.close();
			pst.close();
			
			if(!(newPwd.equals(newPwd2))) throw new BaseException("新密码必须相同");
			
			sql="update tbl_user set user_pwd=? where user_id=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1,newPwd);
			pst.setString(2,user.getUser_id());
			pst.execute();
			pst.close();
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
