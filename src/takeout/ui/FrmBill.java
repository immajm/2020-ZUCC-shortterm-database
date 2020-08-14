package takeout.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import takeout.control.OrderManager;
import takeout.control.UserManager;
import takeout.util.DBUtil;
import takeout.util.DbException;

public class FrmBill extends JDialog {
	
	double original_cost=OrderManager.currentoriginal_cost;
	double final_cost=OrderManager.currentfinal_cost;
	double discount=original_cost-final_cost;
	
	private JPanel workPane = new JPanel();
	private JLabel label1 = new JLabel("��Ʒԭ�ۣ�");
	private JLabel label11 = new JLabel(""+original_cost);
	private JLabel label2 = new JLabel("���Żݽ�");
	private JLabel label22 = new JLabel(""+discount);
	private JLabel label3 = new JLabel("ʵ����");
	private JLabel label33 = new JLabel(""+final_cost);
	private JLabel label4 = new JLabel("������");
	private JLabel label44 = new JLabel("");
	private JLabel label5 = new JLabel("�Ż�ȯ��");
	private JLabel label6 = new JLabel("�����Ա��");
	private JLabel label66 = new JLabel("����95��");
	
	public FrmBill(JDialog f, String s, boolean b) throws DbException {
		super(f, s, b);
		workPane.add(label1);//ԭ��
		workPane.add(label11);
		
		String state=null;//��Ա
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select vip_state from customer where cus_id ='"+UserManager.currentUser.getUser_id()+"'";
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			while(rs.next()) {
				if(rs.getString(1)==null) state="���ǻ�Ա";
				else state="�����Ա";
			}
		}catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		if(state.equals("�����Ա")) {
			workPane.add(label6);
			workPane.add(label66);
		}
		
		if(FrmPossess.currentcoupon!=null) {
			
			workPane.add(label5);
			JLabel label55 = new JLabel(""+FrmPossess.currentcoupon_discount);
			workPane.add(label55);
		}
		
		if(Frm_showFull.currentfull!=null) {
			workPane.add(label4);
			JLabel label44 = new JLabel(""+Frm_showFull.currentfullreduction);
			workPane.add(label44);
		}
			
		String state1=null;//��Ա
		Connection conn1=null;
		try {
			conn1=DBUtil.getConnection();
			String sql="select vip_state from customer where cus_id ='"+UserManager.currentUser.getUser_id()+"'";
			java.sql.Statement st=conn1.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			while(rs.next()) {
				if(rs.getString(1)==null) state1="���ǻ�Ա";
				else state1="�����Ա";
			}
		}catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		if(state1.equals("�����Ա")) {
			workPane.add(label6);
			workPane.add(label66);
		}
		
		workPane.add(label2);//��Ʒ�Ż�
		workPane.add(label22);
		
		workPane.add(label3);
		workPane.add(label33);
		
		
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(300, 300);
		//��Ļ������ʾ
		this.setSize(350,350);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
	}
}
