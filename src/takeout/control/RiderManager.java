package takeout.control;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import takeout.model.BeanOrder_detail;
import takeout.model.BeanPro_Evaluate;
import takeout.model.BeanRider;
import takeout.model.BeanRider_Account;
import takeout.ui.FrmCustomer;
import takeout.ui.FrmEva_Modify;
import takeout.util.BaseException;
import takeout.util.DBUtil;
import takeout.util.DbException;

public class RiderManager {
	
	public void updatepositon(String riderid,String position)throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="update rider set position ='"+position+"' where rider_id='"+riderid+"'";
			java.sql.Statement st=conn.createStatement();
			st.execute(sql);
		}catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
			
	}
	
	public double totalincome(String riderid)throws BaseException{
		double sum=0;
		int cnt=0,single=0;
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select count(order_id) from order_all where rider_id='"+riderid+"' ";
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			if(rs.next()) {
				cnt=rs.getInt(1);
			}
			if(cnt<=100) single=2;
			else if(cnt>100&&cnt<=300) single=3;
			else if(cnt>300&&cnt<=450) single=5;
			else if(cnt>450&&cnt<=550) single=6;
			else if(cnt>550&&cnt<=650) single=7;
			else if(cnt>650) single=8;
			
			sum=cnt*single;
			
			sql="select sum(income) from rider_account where rider_id='"+riderid+"' ";
			st=conn.createStatement();
			rs=st.executeQuery(sql);
			if(rs.next()) {
				sum+=rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		return sum;
	}
	
	public int cntorder(String riderid)throws BaseException{
		int cnt=0;
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select count(order_id) from order_all where rider_id='"+riderid+"' ";
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			if(rs.next()) {
				cnt=rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		return cnt;
	}
	
	
	public void ModifyREva(BeanRider_Account u)throws BaseException{
		Connection conn=null;
		String orderid=u.getOrder_id();
		String riderid=u.getRider_id();
		String Comment=u.getRider_evaluate();
		try {
			conn=DBUtil.getConnection();
			String sql="update rider_account set rider_evaluate=?,recordtime=?"
						+ " where order_id=? and rider_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,Comment);
			pst.setTimestamp(2,new java.sql.Timestamp(System.currentTimeMillis()));
			pst.setString(3,orderid);
			pst.setString(4,riderid);
			pst.execute();
			pst.close();
		}catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
			
	}
	
	public void createR_Eva() throws BaseException{//订单生成后生成骑手评价表已有部分，无评论
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select rider_id from order_all where order_id='"+FrmCustomer.currentorderid+"' ";
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			if(rs.next()) {
				sql="insert into rider_account(rider_id,order_id)values (?,?,?,?)";
				java.sql.PreparedStatement pst=conn.prepareStatement(sql);
				pst.setString(1,rs.getString(1));
				pst.setString(2,FrmCustomer.currentorderid);
				pst.execute();
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
	
	
	public List<BeanRider_Account> loadAllR_Eva()throws BaseException{
		/*骑手编号","订单编号","评价内容","评价日期*/
		List<BeanRider_Account> result=new ArrayList<BeanRider_Account>();
		String cus_id=UserManager.currentUser.getUser_id();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select rider_id,order_id,rider_evaluate,recordtime"
					+ " from rider_account where order_id in "
					+ "(select order_id from order_all where cus_id ='"+cus_id+"')";
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			while(rs.next()){
				BeanRider_Account u=new BeanRider_Account();
				u.setRider_id(rs.getString(1));
				u.setOrder_id(rs.getString(2));
				u.setRider_evaluate(rs.getString(3));
				u.setRecordtime((Timestamp)rs.getTimestamp(4));//入账时间
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
	
	
	public List<BeanRider_Account> loadCheckR_Eva()throws BaseException{//load骑手评价
		List<BeanRider_Account> result=new ArrayList<BeanRider_Account>();
		String rider_id=UserManager.currentUser.getUser_id();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select rider_id,order_id,recordtime,rider_evaluate,income"
					+ " from rider_account where rider_id ='"+rider_id+"'";
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			while(rs.next()){
				BeanRider_Account u=new BeanRider_Account();
				u.setRider_id(rs.getString(1));
				u.setOrder_id(rs.getString(2));
				u.setRecordtime((Timestamp)rs.getTimestamp(3));//入账时间
				u.setRider_evaluate(rs.getString(4));
				u.setIncome(rs.getDouble(5));
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
	
	//骑手入账表的记录
	public void ComplishOrder(String orderid) throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="insert into rider_account(rider_id,order_id)"
					+ " values(?,?)";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, UserManager.currentUser.getUser_id());
			pst.setString(2, orderid);
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
	//用户评价决定骑手入账表的结算
	public void salary(String orderid,String riderid) throws BaseException{
		Connection conn=null;
		double income=0;
		try {
			conn=DBUtil.getConnection();
			String sql="select rider_evaluate from rider_account where order_id='"+orderid+"'";
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			while(rs.next()) {
				if(rs.getString(1).equals("好")) 
					income=0.5;
				else if(rs.getString(1).equals("差")) income=-20;
			}
			sql="update rider_account set income =? "
				+ " where rider_id='"+riderid+"' and order_id='"+orderid+"'";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setDouble(1,income);
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
	
	public BeanRider reg(String id, String name,String pwd,String pwd2) throws BaseException {
		Connection conn=null;
		BeanRider bu=new BeanRider();
		try {
			conn=DBUtil.getConnection();
			if("".equals(id)) throw new BaseException("用户id不为空");
			if("".equals(name)) throw new BaseException("昵称不为空");
			
			String sql="select rider_id from rider where rider_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, id);
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BaseException("用户id已存在");
			rs.close();
			pst.close();
			
			if(!(pwd.equals(pwd2))) throw new BaseException("输入的密码必须相同");
			
			sql="insert into rider(rider_id,rider_name,rider_pwd) values (?,?,?)";
			pst=conn.prepareStatement(sql);
			pst.setString(1,id);
			pst.setString(2,name);
			pst.setString(3,pwd);
			pst.execute();
			pst.close();
			
			bu.setRider_id(id);
			bu.setRider_name(name);
			bu.setRider_pwd(pwd);
			
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
