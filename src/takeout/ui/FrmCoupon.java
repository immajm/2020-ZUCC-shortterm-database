package takeout.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import takeout.control.CouponManager;
import takeout.control.UserManager;
import takeout.model.BeanCoupon;
import takeout.util.BaseException;
import takeout.util.BusinessException;
import takeout.util.DBUtil;
import takeout.util.DbException;

public class FrmCoupon extends JDialog implements ActionListener{ 
	private JPanel toolBar = new JPanel();
	private Button btnAdd = new Button("添加优惠券");
	private Button btnDelete = new Button("删除优惠券");
	private Object tblTitle[]={"优惠券编号","优惠券金额","集单要求数","起始日期","结束日期"};
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable couTable=new JTable(tablmod);
	private void reloadCouTable(){
		try {
			List<BeanCoupon> cou=(new CouponManager()).loadAllCou();
			tblData =new Object[cou.size()][5];
			for(int i=0;i<cou.size();i++){
				tblData[i][0]=cou.get(i).getCoupon_id();
				tblData[i][1]=cou.get(i).getDiscount();
				tblData[i][2]=cou.get(i).getOrder_quantity();
				tblData[i][3]=cou.get(i).getCoupon_starttime();
				tblData[i][4]=cou.get(i).getCoupon_endtime();
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.couTable.validate();
			this.couTable.repaint();
			
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public FrmCoupon(Frame f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnAdd);
		toolBar.add(this.btnDelete);
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		//提取现有数据
		this.reloadCouTable();
		this.getContentPane().add(new JScrollPane(this.couTable), BorderLayout.CENTER);
		
		//屏幕居中显示
		this.setSize(800, 600);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();

		this.btnAdd.addActionListener(this);
		this.btnDelete.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//System.exit(0);
			}
		});
	}
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.btnAdd){
			FrmCou_add dlg=new FrmCou_add(this,"添加优惠券",true);
			dlg.setVisible(true);
			//刷新表格
			this.reloadCouTable();
		}
		else if(e.getSource()==this.btnDelete){
			int i=this.couTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择优惠券","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"确定删除该优惠券吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				String id=this.tblData[i][0].toString();
				try {
					(new CouponManager()).deleteCou(id);
					this.reloadCouTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}
	}
}
