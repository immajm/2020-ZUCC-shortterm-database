package takeout.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import takeout.model.BeanOrder_detail;
import takeout.ui.FrmAddress;
import takeout.ui.FrmCustomer;
import takeout.ui.FrmPossess;
import takeout.util.BaseException;
import takeout.util.DBUtil;
import takeout.util.DbException;

public class OrderManager {
	public static double  currentoriginal_cost;//原始金额
	public static double  currentfinal_cost;//结算金额
	public static Date  final_ordertime;//下单时间
	public static String currentshop_id;//当前商家
	
	public void load_Eva() throws BaseException{//订单生成后生成商品评价表已有部分，无评论
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select pro_id from order_detail where order_id='"+FrmCustomer.currentorderid+"' ";
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			if(rs.next()) {
				sql="insert into pro_evaluate(order_id,cus_id,shop_id,pro_id)values (?,?,?,?)";
				java.sql.PreparedStatement pst=conn.prepareStatement(sql);
				pst.setString(1,FrmCustomer.currentorderid);
				pst.setString(2,UserManager.currentUser.getUser_id());
				pst.setString(3,currentshop_id);
				pst.setString(4,rs.getString(1));
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
	
	public int count_order() throws BaseException{
		Connection conn=null;
		int cnt=-1;
		try {
			conn=DBUtil.getConnection();
			String sql="select count(pro_id) from order_detail where order_id='"+FrmCustomer.currentorderid+"'";
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			if(rs.next()) {
				cnt=rs.getInt(1);
			}
		}catch (SQLException e1) {
			e1.printStackTrace();
			throw new DbException(e1);
		}
		return cnt;
	}
	
	@SuppressWarnings("resource")
	public void deleteOrd_detail(String orderid,String proid) throws BaseException{//详情表删除商品
		int cnt=0;
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			
			String sql = "select single_quantity from order_detail where order_id=? and pro_id=? ";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,orderid);
			pst.setString(2,proid);
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()) {
				cnt=rs.getInt(1);
			}
			if(cnt==1) {
				sql="delete from order_detail where pro_id=? and order_id=?";
				pst=conn.prepareStatement(sql);
				pst.setString(1,proid);
				pst.setString(2,orderid);
				pst.execute();
				pst.close();
			}
			else {
				sql="update order_detail set single_quantity=single_quantity-1 where order_id=? and pro_id=?";
				pst=conn.prepareStatement(sql);
				pst.setString(1,orderid);
				pst.setString(2,proid);
				pst.execute();
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new DbException(e1);
		}
	}
	public void createOrd_detail(BeanOrder_detail u) throws BaseException{//增加详情表商品
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="insert into order_detail(order_id,pro_id,single_quantity,single_cost,single_discount)"
					+ " values(?,?,?,?,?)";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, u.getOrder_id());
			pst.setString(2, u.getPro_id());
			pst.setInt(3, u.getSingle_quantity());
			pst.setDouble(4, u.getSingle_cost());
			pst.setDouble(5, u.getSingle_discount());
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
	public List<BeanOrder_detail> loadAllOrder_d()throws BaseException{//罗列购物车商品
		List<BeanOrder_detail> result=new ArrayList<BeanOrder_detail>();
		String order_id=FrmCustomer.currentorderid;
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select order_id,pro_id,single_quantity,single_cost,single_discount"
					+ " from order_detail where order_id ='"+order_id+"'";
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			while(rs.next()){
				BeanOrder_detail u=new BeanOrder_detail();
				u.setOrder_id(rs.getString(1));
				u.setPro_id(rs.getString(2));
				u.setSingle_quantity(rs.getInt(3));
				u.setSingle_cost(rs.getDouble(4));
				u.setSingle_discount(rs.getDouble(5));
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
	
	
	public void createOrd_all() throws BaseException{//增加总订单，并判断是否可以送券
		String order_id=FrmCustomer.currentorderid;
		String shop_id = null;//
		String address_id=FrmAddress.currentaddress;
		String cus_id=UserManager.currentUser.getUser_id();
		String coupon_id=FrmPossess.currentcoupon;
		String full_id = null;
		double original_cost=0;//
		double discount=0;//
		double final_cost;//
		Date order_time = null;
		Date reachtime;//等待骑手添加
		String order_state="成功下单,等待接单";
		int rider_id;
		
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select shop_id from product where pro_id in "
				+ "(select pro_id from order_detail where order_id='"+FrmCustomer.currentorderid+"')";
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			while(rs.next()){
				shop_id=rs.getString(1);//商家编号
				currentshop_id=shop_id;
			}
			rs.close();
			st.close();
			
			//订单价格=∑（单价-单品优惠）*数量-优惠券-满减
			sql="select single_quantity,single_cost,single_discount"
				+ " from order_detail where order_id ='"+FrmCustomer.currentorderid+"'";
			st=conn.createStatement();
			rs=st.executeQuery(sql);
			while(rs.next()){
				original_cost+=rs.getInt(1)*rs.getDouble(2);//数量*单价
				discount+=rs.getInt(1)*rs.getDouble(3);//数量*单品优惠
			}
			rs.close();
			st.close();
			
			//优惠券
			if(FrmPossess.currentcoupon!=null) {
				sql="select discount from possess where coupon_id ='"+FrmPossess.currentcoupon+"'";
				st=conn.createStatement();
				rs=st.executeQuery(sql);
				while(rs.next()){
					discount+=rs.getDouble(1);
				}
				rs.close();
				st.close();
			}
				
		//满减	if()
				
		//会员打折还没有搞  if()
			final_cost=original_cost-discount;
			
			//下单时间
			Date date = new Date();       //当前时间转为String
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    String createdate = sdf.format(date);
		    try {
		         String time = createdate;//String转为util.Date
		         sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		         java.util.Date ot=sdf.parse(time);
		         order_time = new java.sql.Date(ot.getTime());//util.Date转为sql.Date
		         final_ordertime=order_time;
		    } catch (ParseException e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		    }
		    
		    //生成新order_all
		    sql="update order_all set shop_id=?,address_id=?,cus_id=?,coupon_id=?,full_id=?,"
		    		+ "original_cost=?,final_cost=?,order_time=?,order_state=? "
		    		+ "where order_id='"+order_id+"'";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,shop_id);
			pst.setString(2,address_id);
			pst.setString(3,cus_id);
			pst.setString(4,coupon_id);
			pst.setString(5,full_id);
			pst.setDouble(6,original_cost);
			pst.setDouble(7,final_cost);
			pst.setDate(8,(java.sql.Date) order_time);
			pst.setString(9,order_state);
			pst.execute();
			
			currentoriginal_cost=original_cost;
			currentfinal_cost=final_cost;
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
