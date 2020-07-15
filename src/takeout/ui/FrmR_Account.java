package takeout.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import takeout.control.OrderManager;
import takeout.control.ProManager;
import takeout.control.RiderManager;
import takeout.control.UserManager;
import takeout.model.BeanOrder_detail;
import takeout.model.BeanPro_Evaluate;
import takeout.model.BeanRider_Account;
import takeout.util.BaseException;

public class FrmR_Account extends JDialog {
	
	private JPanel workPane = new JPanel();
	private JLabel label1 = new JLabel("总收入：");
	private JLabel label2 = new JLabel("总接单量：");
	private JLabel label3 = new JLabel("骑手身份：");
	
	private Object tblTitle[]={"骑手编号","订单编号","入账时间","用户评价","单笔收入"};
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable Table=new JTable(tablmod);
	private void reloadAddTable(){
		try {
			List<BeanRider_Account> pro=(new RiderManager()).loadCheckR_Eva();
			tblData =new Object[pro.size()][5];
			for(int i=0;i<pro.size();i++){
				tblData[i][0]=pro.get(i).getRider_id();
				tblData[i][1]=pro.get(i).getOrder_id();
				tblData[i][2]=pro.get(i).getRecordtime();
				tblData[i][3]=pro.get(i).getRider_evaluate();
				tblData[i][4]=pro.get(i).getIncome();
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.Table.validate();
			this.Table.repaint();
			
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 
	public FrmR_Account() throws BaseException {
		
		
		workPane.add(label1);
		double  income=(new RiderManager()).totalincome(UserManager.currentUser.getUser_id());
		JLabel label11 = new JLabel(""+income);
		workPane.add(label11);
		
		
		workPane.add(label2);
		int cnt=(new RiderManager()).cntorder(UserManager.currentUser.getUser_id());
		JLabel label22 = new JLabel(""+cnt);
		workPane.add(label22);
		
		workPane.add(label3);
		String position=null;
		if(cnt<=300) position="新人";
		else if(cnt>300&&cnt<=500) position="优秀员工";
		else if(cnt>500) position="单王";
		JLabel label33 = new JLabel(position);
		workPane.add(label33);
		(new RiderManager()).updatepositon(UserManager.currentUser.getUser_id(),position);
		
		this.getContentPane().add(workPane, BorderLayout.NORTH);
		this.setSize(300, 300);
		
		this.reloadAddTable();
		this.getContentPane().add(new JScrollPane(this.Table), BorderLayout.CENTER);
		
		//屏幕居中显示
		this.setSize(800, 450);
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
