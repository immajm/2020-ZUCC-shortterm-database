package takeout.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import takeout.model.BeanCoupon;
import takeout.util.BaseException;
import takeout.util.BusinessException;
import takeout.util.DBUtil;
import takeout.util.DbException;

public class CouponManager {
	public List<BeanCoupon> loadAllCou()throws BaseException{
		List<BeanCoupon> result=new ArrayList<BeanCoupon>();
		String shop_id=UserManager.currentUser.getUser_id();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select coupon_id,discount,order_quantity,coupon_starttime,coupon_endtime"
					+ " from coupon where shop_id ='"+shop_id+"'order by coupon_id";
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			while(rs.next()){
				BeanCoupon u=new BeanCoupon();
				u.setCoupon_id(rs.getString(1));
				u.setDiscount(rs.getDouble(2));
				u.setOrder_quantity(rs.getInt(3));
				u.setCoupon_starttime(rs.getDate(4));
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

	public void createCou(BeanCoupon cou) throws BaseException{
		if(cou.getCoupon_id()==null || "".equals(cou.getCoupon_id()) ){
			throw new BusinessException("优惠券编号不为空");
		}
		if(""+cou.getDiscount()==null || "".equals(""+cou.getDiscount()) ){
			throw new BusinessException("优惠券金额不为空");
		}
		if(""+cou.getOrder_quantity()==null || "".equals(""+cou.getOrder_quantity()) ){
			throw new BusinessException("集单要求数不为空");
		}
		if(cou.getCoupon_starttime()==null || "".equals(cou.getCoupon_starttime()) ){
			throw new BusinessException("起始日期不为空");
		}
		if(cou.getCoupon_endtime()==null || "".equals(cou.getCoupon_endtime()) ){
			throw new BusinessException("结束日期不为空");
		}
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from coupon where coupon_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,cou.getCoupon_id());
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("优惠券编号已经存在");
			rs.close();
			pst.close();
			sql="insert into coupon(coupon_id,discount,order_quantity,coupon_starttime,coupon_endtime,shop_id)"
					+ " values(?,?,?,?,?,?)";
			pst=conn.prepareStatement(sql);
			pst.setString(1, cou.getCoupon_id());
			pst.setDouble(2, cou.getDiscount());
			pst.setInt(3, cou.getOrder_quantity());
			pst.setDate(4, cou.getCoupon_starttime());
			pst.setDate(5, cou.getCoupon_endtime());
			pst.setString(6, UserManager.currentUser.getUser_id());
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

	public void deleteCou(String id) throws BaseException {
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="delete from product where pro_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, id);
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
