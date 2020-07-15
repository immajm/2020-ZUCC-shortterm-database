package takeout.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import takeout.control.AddressManager;
import takeout.control.ProManager;
import takeout.control.UserManager;
import takeout.model.BeanAddress;
import takeout.model.BeanProduct;
import takeout.util.BaseException;
import takeout.util.DBUtil;
import takeout.util.DbException;

public class FrmMe  extends JDialog {
	
	private JPanel workPane = new JPanel();
	private JLabel label1 = new JLabel("注册时间：");
	private JLabel label11 = new JLabel(""+new java.sql.Timestamp(System.currentTimeMillis()));
	private JLabel label2 = new JLabel("会员状态：");
	private JLabel label22 = new JLabel("已开通尊享会员，订单95折");
	private JLabel label3 = new JLabel("会员截止日期：");//10天
	private JLabel label33 = new JLabel(""+new java.sql.Timestamp(System.currentTimeMillis()+864000000));
	private JLabel label4 = new JLabel("您已经是会员");

	
	public FrmMe(JDialog f, String s, boolean b) throws DbException {
		super(f, s, b);
		
		String state=null;
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select vip_state from customer where cus_id ='"+UserManager.currentUser.getUser_id()+"'";
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			while(rs.next()) {
				if(rs.getString(1)==null) state="不是会员";
				else state="尊享会员";
			}
			
				
		}catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		
		if(state.equals("尊享会员")) {
			String ts = null;
			try {
				conn=DBUtil.getConnection();
				String sql="select vip_endtime from customer where cus_id ='"+UserManager.currentUser.getUser_id()+"'";
				java.sql.Statement st=conn.createStatement();
				java.sql.ResultSet rs=st.executeQuery(sql);
				while(rs.next()) {
					ts =rs.getTimestamp(1).toString();
				}
			}catch (SQLException e) {
				e.printStackTrace();
				throw new DbException(e);
			}
			
			JLabel label41 = new JLabel("会员截止日期：");//一个月
			JLabel label42 = new JLabel(ts);
			
			workPane.add(label4);
			workPane.add(label41);
			workPane.add(label42);
		}
		else if(state.equals("不是会员")) {
			workPane.add(label1);
			workPane.add(label11);
			workPane.add(label2);
			workPane.add(label22);
			workPane.add(label3);
			workPane.add(label33);
			try {
				conn=DBUtil.getConnection();
				String sql="update customer set reg_time=?,vip_state=?,vip_endtime=?"
						+ " where cus_id =?";
				java.sql.PreparedStatement pst=conn.prepareStatement(sql);
				pst.setTimestamp(1, new java.sql.Timestamp(System.currentTimeMillis()));
				pst.setString(2, "尊享会员");
				pst.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()+864000000));
				pst.setString(4, UserManager.currentUser.getUser_id());
				pst.execute();
			
			}catch (SQLException e) {
				e.printStackTrace();
				throw new DbException(e);
			}
			
		}
		
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(300, 200);
		
		//屏幕居中显示
		this.setSize(450,200);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//System.exit(0);
			}
		});
	}
	
}
