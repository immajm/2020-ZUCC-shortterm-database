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
import java.util.Date;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import takeout.control.OrderManager;
import takeout.control.PossessManager;
import takeout.model.BeanCollect;
import takeout.model.BeanPossess;
import takeout.util.BaseException;

public class FrmCollect extends JDialog implements ActionListener{///未改
	public static String currentcoupon=null ;
	public static Date coupon_endtime;
	public static int currentremainorder=0;
	
	private JPanel toolBar = new JPanel();
	private Button btnOk = new Button("选择优惠券");
	private Button btnCancel = new Button("取消");
	
	private Object tblTitle[]={"优惠券编号","商家编号","集单要求数","已定单数"};
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable proTable=new JTable(tablmod);
	private void reloadCouponTable(){
		try {
		//	int cnt=(new OrderManager()).cnt_order();//数量的动态变化没有考虑，每次都是最多的订单数
			List<BeanCollect> pro=(new PossessManager()).loadCollect_cus();
			tblData =new Object[pro.size()][4];
			for(int i=0;i<pro.size();i++){
				tblData[i][0]=pro.get(i).getCoupon_id();
				tblData[i][1]=pro.get(i).getShop_id();
				tblData[i][2]=pro.get(i).getOrder_quantity();
				tblData[i][3]=currentremainorder;
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.proTable.validate();
			this.proTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public FrmCollect(JDialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		this.reloadCouponTable();
		this.getContentPane().add(new JScrollPane(this.proTable), BorderLayout.CENTER);
		//屏幕居中显示
		this.setSize(800,450);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		
		this.btnOk.addActionListener(this);
		this.btnCancel.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//System.exit(0);
			}
		});
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnCancel) {
			this.setVisible(false);
			return;
		}
		else if(e.getSource()==this.btnOk){
			int i=this.proTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择优惠券","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			String couponid=tblData[i][0].toString();
			String shopid=tblData[i][1].toString();
			int quantity=(int)Double.parseDouble(tblData[i][2].toString());
			if(JOptionPane.showConfirmDialog(this,"确定添加吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				try {
					(new PossessManager()).selectCollect(couponid,shopid,quantity);//增加持有优惠券，减少已订单数
				} catch (BaseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			this.setVisible(true);
			reloadCouponTable();
		}
	}
}
