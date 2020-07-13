package takeout.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import takeout.model.BeanPossess;
import takeout.ui.FrmCustomer;
import takeout.ui.FrmPossess;
import takeout.util.BaseException;
import takeout.util.DBUtil;
import takeout.util.DbException;

public class PossessManager {
	public void minus_quantity()throws BaseException{
		int cnt=0;
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql = "select quantity from possess where cus_id=? and coupon_id=? ";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,UserManager.currentUser.getUser_id());
			pst.setString(2,FrmPossess.currentcoupon);
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()) {
				cnt=rs.getInt(1);
			}
			rs.close();
			pst.close();
			if(cnt==1) {
				sql="delete from possess where where cus_id=? and coupon_id=?";
				pst=conn.prepareStatement(sql);
				pst.setString(1,UserManager.currentUser.getUser_id());
				pst.setString(2,FrmPossess.currentcoupon);
				pst.execute();
				pst.close();
			}
			else {
				sql="update possess set quantity=quantity-1 where cus_id=? and coupon_id=?";
				java.sql.PreparedStatement pst1=conn.prepareStatement(sql);
				pst1.setString(1,UserManager.currentUser.getUser_id());
				pst1.setString(2,FrmPossess.currentcoupon);
				pst1.execute();
			}
		}catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
	}
	
	//用户界面的显示
	public List<BeanPossess> loadCollect_cus()throws BaseException{
		List<BeanPossess> result=new ArrayList<BeanPossess>();
		String cus_id=UserManager.currentUser.getUser_id();
		Connection conn=null;
		String shopid=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select shop_id from product where pro_id in "
				+ "(select pro_id from order_detail where order_id='"+FrmCustomer.currentorderid+"')";
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			while(rs.next()){
				shopid=rs.getString(1);//商家编号
			}
			rs.close();
			st.close();
				
			sql="select coupon_id,shop_id,discount,quantity,coupon_endtime"
					+ " from possess where cus_id ='"+cus_id+"'and shop_id='"+shopid+"'";
			st=conn.createStatement();
			rs=st.executeQuery(sql);
			while(rs.next()){
				BeanPossess u=new BeanPossess();
				u.setCoupon_id(rs.getString(1));
				u.setShop_id(rs.getString(2));
				u.setDiscount(rs.getDouble(3));
				u.setQuantity(rs.getInt(4));
				u.setCoupon_endtime(rs.getDate(5));
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
}
