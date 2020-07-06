package takeout.control;

import java.sql.Connection;
import java.sql.SQLException;

import takeout.itf.IAdminManager;
import takeout.util.BaseException;
import takeout.util.DBUtil;
import takeout.util.DbException;
import takeout.model.BeanAdmin;

public class AdminManager implements IAdminManager {
	public BeanAdmin reg(String id, String name,String pwd,String pwd2) throws BaseException {
		Connection conn=null;
		BeanAdmin bu=new BeanAdmin();
		try {
			conn=DBUtil.getConnection();
			 //conn = DBUtil_Pool.getConnection();
			if("".equals(id)) throw new BaseException("用户id不为空");
			if("".equals(name)) throw new BaseException("昵称不为空");
			
			String sql="select admin_id from admin where admin_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, id);
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BaseException("用户id已存在");
			rs.close();
			pst.close();
			
			if(!(pwd.equals(pwd2))) throw new BaseException("输入的密码必须相同");
			
			sql="insert into admin(admin_id,admin_name,pwd) values (?,?,?)";
			pst=conn.prepareStatement(sql);
			pst.setString(1,id);
			pst.setString(2,name);
			pst.setString(3,pwd);
			pst.execute();
			pst.close();
			
			bu.setAdmin_id(id);
			bu.setAdmin_name(name);
			bu.setPwd(pwd);
			
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

	
	
	
/*	public void changePwd(BeanAdmin user, String oldPwd, String newPwd,String newPwd2) throws BaseException {
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			// BeanAdmin = DBUtil_Pool.getConnection();
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
	}*/

}
