package takeout.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import takeout.model.BeanFull;
import takeout.model.BeanPossess;
import takeout.model.BeanProduct;
import takeout.ui.FrmCustomer;
import takeout.util.BaseException;
import takeout.util.BusinessException;
import takeout.util.DBUtil;
import takeout.util.DbException;

public class FullManager {
	public List<BeanFull> loadFull_cus()throws BaseException{
		List<BeanFull> result=new ArrayList<BeanFull>();
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
				
			sql="select full_id,shop_id,full_demand,full_reduction,tag"
					+ " from full where shop_id='"+shopid+"'";
			st=conn.createStatement();
			rs=st.executeQuery(sql);
			while(rs.next()){
				BeanFull u=new BeanFull();
				u.setFull_id(rs.getString(1));
				u.setShop_id(rs.getString(2));
				u.setFull_demand(rs.getDouble(3));
				u.setFull_reduction(rs.getDouble(4));
				u.setTag(rs.getString(5));
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
	
	public List<BeanFull> loadAllFull()throws BaseException{
		List<BeanFull> result=new ArrayList<BeanFull>();
		String id=UserManager.currentUser.getUser_id();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select full_id,full_demand,full_reduction,tag"
					+ " from full where shop_id ='"+id+"'";
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			while(rs.next()){
				BeanFull u=new BeanFull();
				u.setFull_id(rs.getString(1));
				u.setFull_demand(rs.getDouble(2));
				u.setFull_reduction(rs.getDouble(3));
				u.setTag(rs.getString(4));
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

	public void createFull(BeanFull full) throws BaseException{
		if(full.getFull_id()==null || "".equals(full.getFull_id()) ){
			throw new BusinessException("满减编号不为空");
		}
		if(""+full.getFull_demand()==null || "".equals(""+full.getFull_demand()) ){
			throw new BusinessException("满减要求金额不为空");
		}
		if(""+full.getFull_reduction()==null || "".equals(""+full.getFull_reduction()) ){
			throw new BusinessException("优惠金额不为空");
		}
		if(full.getTag()==null || "".equals(full.getTag()) ){
			throw new BusinessException("可否与优惠券叠加？");
		}
		
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from full where full_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,full.getFull_id());
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("商品编号已经存在");
			
			sql="insert into full(full_id,full_demand,full_reduction,tag,shop_id)"
					+ " values(?,?,?,?,?)";
			pst=conn.prepareStatement(sql);
			pst.setString(1, full.getFull_id());
			pst.setDouble(2, full.getFull_demand());
			pst.setDouble(3, full.getFull_reduction());
			pst.setString(4, full.getTag());
			pst.setString(5, UserManager.currentUser.getUser_id());
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

	public void deleteUser(String id) throws BaseException {
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="delete from full where full_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,id);
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
