package takeout.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import takeout.model.BeanCollect;
import takeout.model.BeanPossess;
import takeout.ui.FrmCollect;
import takeout.ui.FrmCou_add;
import takeout.ui.FrmCustomer;
import takeout.ui.FrmPossess;
import takeout.util.BaseException;
import takeout.util.BusinessException;
import takeout.util.DBUtil;
import takeout.util.DbException;

public class PossessManager {
	
	public void selectCollect(String couponid,String shopid,int order_quantity) throws BaseException{//增加持有优惠券，减少已订单数
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			if(order_quantity<=FrmCollect.currentremainorder) {
				double dis=0;
				String sql="select discount from coupon where coupon_id='"+couponid+"' and shop_id ='"+shopid+"'";
				java.sql.Statement st=conn.createStatement();
				java.sql.ResultSet rs=st.executeQuery(sql);
				while(rs.next()) {
					dis=rs.getDouble(1);
				}
				
				sql="select count(coupon_id) from possess where cus_id='"+UserManager.currentUser.getUser_id()+"' "
					+ " and shop_id ='"+shopid+"' and coupon_id='"+couponid+"'";
				st=conn.createStatement();
				rs=st.executeQuery(sql);
				while(rs.next()){
					if(rs.getInt(1)==0) {
						sql="insert into possess(coupon_id,cus_id,shop_id,discount,quantity)"
						+ " values(?,?,?,?,?)";
						java.sql.PreparedStatement pst = conn.prepareStatement(sql);
						pst.setString(1, couponid);
						pst.setString(2, UserManager.currentUser.getUser_id());
						pst.setString(3,shopid);
						pst.setDouble(4, dis);
						pst.setInt(5, 1);
						pst.execute();
					}
					else {
						sql="update possess set quantity=quantity+1 where shop_id =? and coupon_id=? and cus_id=? ";
								java.sql.PreparedStatement pst = conn.prepareStatement(sql);
								pst.setString(1, shopid);
								pst.setString(2, couponid);
								pst.setString(3,UserManager.currentUser.getUser_id());
								pst.execute();
					}
				}
				FrmCollect.currentremainorder=FrmCollect.currentremainorder-order_quantity;
			}
			else {
				throw new BusinessException("已订单数不够");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}

		
	}
	public void createCollect(String id,int quantity) throws BaseException{//优惠券生成后生成集单表已有部分，无已订单数
		String shopid=null;
		shopid=UserManager.currentUser.getUser_id();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="insert into collect(coupon_id,shop_id,order_quantity)"
					+ " values (?,?,?) ";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, id);
			pst.setString(2, shopid);
			pst.setInt(3,quantity);
			pst.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		
	}
	
	
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
				sql="delete from possess where cus_id=? and coupon_id=?";
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
	public List<BeanPossess> loadPossess_cus()throws BaseException{
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
	//用户界面的显示
		public List<BeanCollect> loadCollect_cus()throws BaseException{
			List<BeanCollect> result=new ArrayList<BeanCollect>();
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
					
				sql="select coupon_id,shop_id,order_quantity"
						+ " from coupon where shop_id='"+shopid+"'";
				st=conn.createStatement();
				rs=st.executeQuery(sql);
				while(rs.next()){
					BeanCollect u=new BeanCollect();
					u.setCoupon_id(rs.getString(1));
					u.setShop_id(rs.getString(2));
					u.setOrder_quantity(rs.getInt(3));
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
