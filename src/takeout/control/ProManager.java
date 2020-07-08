package takeout.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import takeout.util.BusinessException;
import takeout.util.BaseException;
import takeout.util.DBUtil;
import takeout.util.DbException;
import takeout.model.BeanProduct;

public class ProManager {
	public List<BeanProduct> loadAllPro()throws BaseException{
		List<BeanProduct> result=new ArrayList<BeanProduct>();
		String shop_id=UserManager.currentUser.getUser_id();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select pro_id,pro_name,pro_price,pro_discount_amount,type_id"
					+ " from product where shop_id ='"+shop_id+"'order by pro_id";
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			while(rs.next()){
				BeanProduct u=new BeanProduct();
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

	public void createPro(BeanProduct pro) throws BaseException{
		if(pro.getPro_id()==null || "".equals(pro.getPro_id()) ){
			throw new BusinessException("商品编号不为空");
		}
		if(pro.getPro_name()==null || "".equals(pro.getPro_name()) ){
			throw new BusinessException("商品名称不为空");
		}
		if(pro.getType_id()==null || "".equals(pro.getType_id()) ){
			throw new BusinessException("类型不为空");
		}
		if(""+pro.getPro_price()==null || "".equals(""+pro.getPro_name()) ){
			throw new BusinessException("价格不为空");
		}
		
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from product where pro_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,pro.getPro_id());
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("商品编号已经存在");
			sql="select * from pro_type where type_id=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1,pro.getType_id());
			rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("所属分类不存在,请先添加商品类别");
			rs.close();
			pst.close();
			sql="insert into product(pro_id,pro_name,pro_price,pro_discount_amount,type_id,shop_id)"
					+ " values(?,?,?,?,?,?)";
			pst=conn.prepareStatement(sql);
			pst.setString(1, pro.getPro_id());
			pst.setString(2, pro.getPro_name());
			pst.setDouble(3, pro.getPro_price());
			pst.setDouble(4, pro.getPro_discount_amount());
			pst.setString(5, pro.getType_id());
			pst.setString(6, UserManager.currentUser.getUser_id());
			pst.execute();
			pst.close();
			//pro_quantity要设初始值为0吗？
			sql="update pro_type set pro_quantity=pro_quantity+1 where type_id=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1, pro.getType_id());
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
			//删除的时候，传入bean类型，这里需要修改
			String sql="select type_id from product where pro_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,id);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("该商品不存在");
			String type_id=rs.getString(1);
			rs.close();
			pst.close();
			sql="update pro_type set pro_quantity=pro_quantity-1 where type_id=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1, type_id);
			pst.execute();
			pst.close();
			sql="delete from product where pro_id=?";
			pst=conn.prepareStatement(sql);
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
