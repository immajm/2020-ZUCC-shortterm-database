package takeout.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import takeout.model.BeanOrder_all;
import takeout.model.BeanOrder_detail;
import takeout.ui.FrmCustomer;
import takeout.ui.FrmFinal_order;
import takeout.ui.FrmPossess;
import takeout.ui.Frm_showFull;
import takeout.util.BaseException;
import takeout.util.DBUtil;
import takeout.util.DbException;

public class OrderManager {
	public static double  currentoriginal_cost;//ԭʼ���
	public static double  currentfinal_cost;//������
	public static Date  final_ordertime;//�µ�ʱ��
	public static String currentshop_id;//��ǰ�̼�
	
	public static String[] province =new String[100] ;//ʡ
	public static String[] city =new String[100];//��
	public static String[] region =new String[100];//��
	
	public int cnt_order()throws BaseException{
		int cnt=0;
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select count(order_id) from order_all where "
					+ " cus_id ='"+UserManager.currentUser.getUser_id()+"' and "
					+ " shop_id ='"+currentshop_id+"'";
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			while(rs.next()){
				cnt=rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		return cnt;
	}
	
	public void R_complish_update(String orderid)throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="update order_all set order_state ='�������' "
					+ " where order_id ='"+orderid+"'";
			java.sql.Statement st=conn.createStatement();
			st.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
			
	}
	public void R_accept_update(String orderid)throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="update order_all set order_state ='�����ѽӵ�',rider_id='"+UserManager.currentUser.getUser_id()+"'"
					+ " where order_id ='"+orderid+"'";
			java.sql.Statement st=conn.createStatement();
			st.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
			
	}
	
	
	public List<BeanOrder_all> loadAllOrder_R() throws BaseException{//�г������ѽӵ������µ��Ķ���
		//�������","�û�","ʡ","��","��","�µ�ʱ��","Ҫ���ʹ�","״̬","�ӵ�����"
		List<BeanOrder_all> result=new ArrayList<BeanOrder_all>();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select order_id,order_all.cus_id,province,city,region,order_time,reachtime,order_state,rider_id"
					+ " from order_all,address where order_all.address_id=address.address_id "
					+ "and (order_state ='�ɹ��µ�,�ȴ��ӵ�' or order_state ='�����ѽӵ�')";
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			int i=0;
			while(rs.next()){
				BeanOrder_all u=new BeanOrder_all();
				u.setOrder_id(rs.getString(1));
				u.setCus_id(rs.getString(2));
				province[i]=rs.getString(3);
				city[i]=rs.getString(4);
				region[i]=rs.getString(5);
				u.setOrder_time((Timestamp)rs.getTimestamp(6));
				u.setReachtime((Timestamp)rs.getTimestamp(7));
				u.setOrder_state(rs.getString(8));
				u.setRider_id(rs.getString(9));
				result.add(u);
				i++;
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
	public void create_Eva() throws BaseException{//�������ɺ�������Ʒ���۱����в��֣�������
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
	public void deleteOrd_detail(String orderid,String proid) throws BaseException{//�����ɾ����Ʒ
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
	public void createOrd_detail(BeanOrder_detail u) throws BaseException{//�����������Ʒ
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
	public List<BeanOrder_detail> loadAllOrder_d()throws BaseException{//���й��ﳵ��Ʒ
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
	
	public void updatefullid()throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="update order_all set full_id ='"+Frm_showFull.currentfull+"' "
					+ " where order_id='"+FrmCustomer.currentorderid+"'";
			java.sql.Statement st=conn.createStatement();
			st.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
	}
	public double original() throws BaseException{
		Connection conn=null;
		double original_cost = 0;
		double discount = 0;
		try {
			conn=DBUtil.getConnection();
			String sql="select single_quantity,single_cost,single_discount"
				+ " from order_detail where order_id ='"+FrmCustomer.currentorderid+"'";
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			while(rs.next()){
				original_cost+=rs.getInt(1)*rs.getDouble(2);//����*����
				discount+=rs.getInt(1)*rs.getDouble(3);//����*��Ʒ�Ż�
			}
			rs.close();
			st.close();
		}catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		return original_cost-discount;
	}
	
	public void createOrd_all() throws BaseException{//�����ܶ��������ж��Ƿ������ȯ
		String order_id=FrmCustomer.currentorderid;
		String shop_id = null;//
		String address_id=FrmFinal_order.currentaddress;
		String cus_id=UserManager.currentUser.getUser_id();
		String coupon_id=FrmPossess.currentcoupon;
		String full_id = null;
		double original_cost=0;//
		double discount=0;//
		double final_cost;//
		String order_state="�ɹ��µ�,�ȴ��ӵ�";
		
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select shop_id from product where pro_id in "
				+ "(select pro_id from order_detail where order_id='"+FrmCustomer.currentorderid+"')";
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			while(rs.next()){
				shop_id=rs.getString(1);//�̼ұ��
				currentshop_id=shop_id;
			}
			rs.close();
			st.close();
			
			//�����۸�=�ƣ�����-��Ʒ�Żݣ�*����-�Ż�ȯ-����
			sql="select single_quantity,single_cost,single_discount"
				+ " from order_detail where order_id ='"+FrmCustomer.currentorderid+"'";
			st=conn.createStatement();
			rs=st.executeQuery(sql);
			while(rs.next()){
				original_cost+=rs.getInt(1)*rs.getDouble(2);//����*����
				discount+=rs.getInt(1)*rs.getDouble(3);//����*��Ʒ�Ż�
			}
			rs.close();
			st.close();
			
			//�Ż�ȯ
			if(FrmPossess.currentcoupon!=null)
				discount+=FrmPossess.currentcoupon_discount;
			//����
			if(Frm_showFull.currentfull!=null) {
				discount+=Frm_showFull.currentfullreduction;
			}
			
			//��Ա����
			String state=null;
			try {
				sql="select vip_state from customer where cus_id ='"+UserManager.currentUser.getUser_id()+"'";
				java.sql.Statement st1=conn.createStatement();
				java.sql.ResultSet rs1=st1.executeQuery(sql);
				while(rs1.next()) {
					if(rs1.getString(1)==null) state="���ǻ�Ա";
					else state="�����Ա";
				}
			}catch (SQLException e) {
				e.printStackTrace();
				throw new DbException(e);
			}
			
			if(state.equals("�����Ա")) {
				final_cost=(original_cost-discount)*0.95;
			}
			else {
				final_cost=original_cost-discount;
			}
			
		    //������order_all
		    sql="update order_all set shop_id=?,address_id=?,cus_id=?,coupon_id=?,full_id=?,"
		    		+ "original_cost=?,final_cost=?,order_time=?,reachtime=?,order_state=? "
		    		+ "where order_id='"+order_id+"'";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,shop_id);
			pst.setString(2,address_id);
			pst.setString(3,cus_id);
			pst.setString(4,coupon_id);
			pst.setString(5,full_id);
			pst.setDouble(6,original_cost);
			pst.setDouble(7,final_cost);
			pst.setTimestamp(8,new java.sql.Timestamp(System.currentTimeMillis()));
			pst.setTimestamp(9,new java.sql.Timestamp(System.currentTimeMillis()+900000));//��Сʱ/15mins
			pst.setString(10,order_state);
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
