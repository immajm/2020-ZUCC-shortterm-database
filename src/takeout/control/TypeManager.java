package takeout.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import takeout.model.BeanPro_Type;
import takeout.model.BeanProduct;
import takeout.util.BaseException;
import takeout.util.BusinessException;
import takeout.util.DBUtil;
import takeout.util.DbException;

public class TypeManager {
	//没有类别修改功能，万一删除了关联商品的类别就会出错
	public List<BeanPro_Type> loadAllType()throws BaseException{
		List<BeanPro_Type> result=new ArrayList<BeanPro_Type>();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select type_id,type_name,pro_quantity from pro_type ";
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			while(rs.next()){
				BeanPro_Type u=new BeanPro_Type();
				u.setType_id(rs.getString(1));
				u.setType_name(rs.getString(2));
				u.setPro_quantity(rs.getInt(3));
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
	public void createType(BeanPro_Type type) throws BaseException{
		if(type.getType_id()==null || "".equals(type.getType_id()) ){
			throw new BusinessException("类别编号不为空");
		}
		if(type.getType_name()==null || "".equals(type.getType_name()) ){
			throw new BusinessException("类别名称不为空");
		}
		
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from pro_type where type_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,type.getType_id());
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("类别编号已经存在");
			rs.close();
			pst.close();
			sql="insert into pro_type(type_id,type_name,pro_quantity)"
					+ " values(?,?,0)";
			pst=conn.prepareStatement(sql);
			pst.setString(1, type.getType_id());
			pst.setString(2, type.getType_name());
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
			String sql="select pro_quantity from pro_type where type_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,id);
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) {
				if(rs.getInt(1)==0) throw new BusinessException("该类型的关联商品存在,不可删除");
			}
			rs.close();
			pst.close();
			sql="delete from pro_type where type_id=?";
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
