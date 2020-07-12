package takeout.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import takeout.model.BeanAddress;
import takeout.model.BeanProduct;
import takeout.util.BaseException;
import takeout.util.BusinessException;
import takeout.util.DBUtil;
import takeout.util.DbException;

public class AddressManager {
	public List<BeanAddress> loadAllAddress()throws BaseException{
		List<BeanAddress> result=new ArrayList<BeanAddress>();
		String cusid=UserManager.currentUser.getUser_id();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select address_id,address,cus_id,tel,province,city,region"
					+ " from address where cus_id ='"+cusid+"'order by address_id";
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			while(rs.next()){
				BeanAddress u=new BeanAddress();
				u.setAddress_id(rs.getString(1));
				u.setAddress(rs.getString(2));
				u.setCus_id(rs.getString(3));
				u.setTel(rs.getString(4));
				u.setProvince(rs.getString(5));
				u.setCity(rs.getString(6));
				u.setRegion(rs.getString(7));
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

	public void createAddress(BeanAddress u) throws BaseException{
		if("".equals(u.getAddress_id()) ){
			throw new BusinessException("地址编号不为空");
		}
		if("".equals(u.getAddress()) ){
			throw new BusinessException("地址名称不为空");
		}
		if("".equals(u.getTel()) ){
			throw new BusinessException("联系电话不为空");
		}
		if("".equals(u.getProvince()) ){
			throw new BusinessException("省不为空");
		}
		if("".equals(u.getCity()) ){
			throw new BusinessException("市不为空");
		}
		if("".equals(u.getRegion()) ){
			throw new BusinessException("区不为空");
		}
		
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from address where address_id=? and cus_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,u.getAddress_id());
			pst.setString(2,UserManager.currentUser.getUser_id());
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("地址编号已经存在");
			sql="insert into address(address_id,address,cus_id,tel,province,city,region)"
					+ " values(?,?,?,?,?,?,?)";
			pst=conn.prepareStatement(sql);
			pst.setString(1, u.getAddress_id());
			pst.setString(2, u.getAddress());
			pst.setString(3, UserManager.currentUser.getUser_id());
			pst.setString(4, u.getTel());
			pst.setString(5, u.getProvince());
			pst.setString(6, u.getCity());
			pst.setString(7, u.getRegion());
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
