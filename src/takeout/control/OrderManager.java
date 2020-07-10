package takeout.control;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import takeout.model.BeanOrder_all;
import takeout.model.BeanOrder_detail;
import takeout.util.BaseException;
import takeout.util.BusinessException;
import takeout.util.DBUtil;
import takeout.util.DbException;

public class OrderManager {
	public void create_order(String id) {
		
	}
	public void createOrd_detail(BeanOrder_detail ord) throws BaseException{
		
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="insert into order_detail(order_id,pro_id,single_quantity,single_cost,single_discount)"
					+ " values(?,?,?,?,?)";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, ord.getOrder_id());
			pst.setString(2, ord.getPro_id());
			pst.setInt(3, ord.getSingle_quantity());
			pst.setDouble(4, ord.getSingle_cost());
			pst.setDouble(5, ord.getSingle_discount());
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
public void createOrd_all(String cus,String orderid,String add_id) throws BaseException{
		String order_id;
		String shop_id;
		String address_id;
		String cus_id;
		String coupon_id;
		double original_cost;
		double final_cost;
		Date order_time;
		Date reachtime;
		String order_state;
		int rider_id;
		int full_id;
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			order_id=orderid;//订单编号
			String sql="select distinct shop_id from product where pro_id in "
					+ "(select pro_id from order_detail )";
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			while(rs.next()){
				shop_id=rs.getString(1);//商家编号
			}
			rs.close();
			st.close();
			address_id=add_id;//地址编号
			cus_id=cus;//用户编号
			List<BeanOrder_detail> result=new ArrayList<BeanOrder_detail>();
			sql="select single_quantity,single_cost,single_discount"
				+ " from order_detail where order_id ='"+orderid+"'";
			st=conn.createStatement();
			rs=st.executeQuery(sql);
			while(rs.next()){
				BeanOrder_detail u=new BeanOrder_detail();
				u.setSingle_quantity(rs.getInt(1));//数量
				u.setSingle_cost(rs.getDouble(2));//该商品总价
				u.setSingle_discount(rs.getDouble(3));//单品优惠
				result.add(u);
			}
			//订单价格=∑（单价-单品优惠）*数量-优惠券-满减
		/*	for(int i=0;i<result.size();i++){
				
				tblData[i][0]=pro.get(i).getPro_id();
				tblData[i][1]=pro.get(i).getPro_name();
				tblData[i][2]=pro.get(i).getPro_price();
				tblData[i][3]=pro.get(i).getPro_discount_amount();
				tblData[i][4]=pro.get(i).getType_id();
			}*/
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
	public List<BeanOrder_all> loadAllOrder_shop()throws BaseException{
		List<BeanOrder_all> result=new ArrayList<BeanOrder_all>();
		String shop_id=UserManager.currentUser.getUser_id();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select pro_id,pro_name,pro_price,pro_discount_amount,type_id"
					+ " from product where shop_id ='"+shop_id+"'order by pro_id";
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			while(rs.next()){
				BeanOrder_all u=new BeanOrder_all();
				u.setPro_id(rs.getString(1));
				u.setPro_name(rs.getString(2));
				u.setPro_price(rs.getDouble(3));
				u.setPro_discount_amount(rs.getDouble(4));
				u.setType_id(rs.getString(5));
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
