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
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import takeout.control.AddressManager;
import takeout.control.OrderManager;
import takeout.control.RiderManager;
import takeout.model.BeanAddress;
import takeout.util.BaseException;
import takeout.util.DBUtil;
import takeout.util.DbException;

public class FrmFinal_order extends JDialog implements ActionListener{
	public static String currentaddress=null ;
	private JPanel toolBar = new JPanel();
	private Button btnOk = new Button("确认");
	private Button btnAddress = new Button("选择收货地址");
	private Button btnAdd = new Button("添加收货地址");
	private Button btnPossess = new Button("优惠券持有表");
	private Button btnFull = new Button("选择满减方案");
	
	private Object tblTitle[]={"地址编号","地址","用户","联系电话","省","市","区"};
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable proTable=new JTable(tablmod);
	private void reloadAddTable(){
		try {
			List<BeanAddress> pro=(new AddressManager()).loadAllAddress();
			tblData =new Object[pro.size()][7];
			for(int i=0;i<pro.size();i++){
				tblData[i][0]=pro.get(i).getAddress_id();
				tblData[i][1]=pro.get(i).getAddress();
				tblData[i][2]=pro.get(i).getCus_id();
				tblData[i][3]=pro.get(i).getTel();
				tblData[i][4]=pro.get(i).getProvince();
				tblData[i][5]=pro.get(i).getCity();
				tblData[i][6]=pro.get(i).getRegion();
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.proTable.validate();
			this.proTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//结算时表格为空不可以结算
	public FrmFinal_order(JDialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnAddress);
		toolBar.add(btnAdd);
		toolBar.add(btnPossess);
		toolBar.add(btnFull);
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
	//	this.getContentPane().add(new JScrollPane(this.otable), BorderLayout.CENTER);
		this.reloadAddTable();
		this.getContentPane().add(new JScrollPane(this.proTable), BorderLayout.CENTER);
		//屏幕居中显示
		this.setSize(800, 450);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.btnAddress.addActionListener(this);
		this.btnOk.addActionListener(this);
		this.btnPossess.addActionListener(this);
		this.btnAdd.addActionListener(this);
		this.btnFull.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//System.exit(0);
			}
		});
	}
	public void actionPerformed(ActionEvent e){
		
		if(e.getSource()==this.btnOk){

			int cnt=-1;
			try {
				cnt = new OrderManager().count_order();
			} catch (BaseException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			if(JOptionPane.showConfirmDialog(this,"确定开始结算吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				if(cnt==0) {
					JOptionPane.showMessageDialog(null, "购物车空空如也","提示",JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(currentaddress==null) {
					JOptionPane.showMessageDialog(null, "请选择收货地址","提示",JOptionPane.ERROR_MESSAGE);
					return;
				}
				try {
					new OrderManager().createOrd_all();
					FrmCustomer.generate_orderid=1;//flag设为1，下一单开始重新取订单号
					(new OrderManager()).create_Eva();//订单生成后生成商品评价表已有部分，无评论
					FrmCollect.currentremainorder++;//用于订单数和集单优惠的关联
				} catch (BaseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				FrmBill dlg;
				try {
					dlg = new FrmBill(this,"账单",true);
					dlg.setVisible(true);
				} catch (DbException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		}
		else if(e.getSource()==this.btnAddress) {
			
			int i=this.proTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择地址","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"确定选择该地址吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
					currentaddress = tblData[i][0].toString();
			}
			this.setVisible(true);
			reloadAddTable();
			
			
		}
		else if(e.getSource()==this.btnPossess) {
			FrmPossess dlg=new FrmPossess(this,"选择优惠券",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.btnAdd) {
			FrmAddress_Add dlg=new FrmAddress_Add(this,"添加地址",true);
			dlg.setVisible(true);
			this.reloadAddTable();
		}
		else if(e.getSource()==this.btnFull) {
			Frm_showFull dlg=new Frm_showFull(this,"选择满减",true);
			dlg.setVisible(true);
		}
	}
   
}
