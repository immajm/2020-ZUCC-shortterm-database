package takeout.ui;

import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import takeout.control.OrderManager;
import takeout.control.RiderManager;
import takeout.control.UserManager;
import takeout.model.BeanOrder_all;
import takeout.model.BeanOrder_detail;
import takeout.ui.*;
import takeout.util.BaseException;

public class FrmMain extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JMenuBar menubar_admin=new JMenuBar(); 
	private JMenuBar menubar_shop=new JMenuBar(); 
	private JPanel toolBar = new JPanel();
	
	//管理员
    private JMenu menu_cus=new JMenu("顾客管理");
    private JMenu menu_shop=new JMenu("商家管理");
    private JMenu menu_rider=new JMenu("骑手管理");
    private JMenu menu_order=new JMenu("订单管理");
   // 商家
    private JMenu menu_product=new JMenu("商品管理");
    private JMenu menu_activity=new JMenu("店面活动管理");
    private JMenu menu_checkorder=new JMenu("订单查看");
    //顾客
    //骑手
    private Button btnAccept = new Button("接单");
	private Button btnComplish = new Button("完成订单");
	private Button btnAccount = new Button("历史接单与入账情况");
	//private Button btnall = new Button("总收入");
	
	
    //1
    private JMenuItem  menuItem_DeleteCus=new JMenuItem("修改顾客");
    private JMenuItem  menuItem_DeleteShop=new JMenuItem("修改商家");
    private JMenuItem  menuItem_DeleteRider=new JMenuItem("修改骑手");
    private JMenuItem  menuItem_DeleteOrder=new JMenuItem("修改订单");
    //2
    private JMenuItem  menuItem_prolist=new JMenuItem("商品菜单");
    private JMenuItem  menuItem_protype=new JMenuItem("商品类型管理");
    private JMenuItem  menuItem_full=new JMenuItem("满减活动");
    private JMenuItem  menuItem_coupon=new JMenuItem("优惠券");
    private JMenuItem  menuItem_neworder=new JMenuItem("新订单");
    private JMenuItem  menuItem_okoreder=new JMenuItem("已完成订单");
    //3
    //写在FrmCustomer
    //4 页面直接按钮
    /*
    private JMenuItem  menuItem_modifyPwd=new JMenuItem("密码修改");
    private JMenuItem  menuItem_static1=new JMenuItem("统计1");
    private JMenuItem  menuItem_register=new JMenuItem("注册");
    */

	
    private Object tblTitle[]={"订单编号","用户","省","市","区","下单时间","要求送达","状态","接单骑手"};
	private Object tblData[][];
	DefaultTableModel tabModel=new DefaultTableModel();
	private JTable ROrderTable=new JTable(tabModel);
	
	private void reloadR_OrderTable(){
		try {
			List<BeanOrder_all> u=(new OrderManager()).loadAllOrder_R();
			tblData =new Object[u.size()][9];
			for(int i=0;i<u.size();i++){
				tblData[i][0]=u.get(i).getOrder_id();
				tblData[i][1]=u.get(i).getCus_id();
				tblData[i][2]=OrderManager.province[i];
				tblData[i][3]=OrderManager.city[i];
				tblData[i][4]=OrderManager.region[i];
				tblData[i][5]=u.get(i).getOrder_time();
				tblData[i][6]=u.get(i).getReachtime();
				tblData[i][7]=u.get(i).getOrder_state();
				tblData[i][8]=u.get(i).getRider_id();
			}
			tabModel.setDataVector(tblData,tblTitle);
			this.ROrderTable.validate();
			this.ROrderTable.repaint();
			
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private FrmLogin dlgLogin=null;
	private JPanel statusBar = new JPanel();
	
	public FrmMain() {
		dlgLogin=new FrmLogin(this,"登陆",true);
		dlgLogin.setVisible(true);
	    //菜单
		if("管理员".equals(UserManager.currentUser.getType())){
			this.setExtendedState(Frame.MAXIMIZED_BOTH);//全屏显示
			this.setTitle("外卖助手管理系统——管理员界面");
		    this.menu_cus.add(this.menuItem_DeleteCus); this.menuItem_DeleteCus.addActionListener(this);
		    this.menu_shop.add(this.menuItem_DeleteShop); this.menuItem_DeleteShop.addActionListener(this);
		    this.menu_rider.add(this.menuItem_DeleteRider); this.menuItem_DeleteRider.addActionListener(this);
		    this.menu_order.add(this.menuItem_DeleteOrder); this.menuItem_DeleteOrder.addActionListener(this);
		    menubar_admin.add(menu_cus);
		    menubar_admin.add(menu_shop);
		    menubar_admin.add(menu_rider);
		    menubar_admin.add(menu_order);
		    this.setJMenuBar(menubar_admin);
		}else if("商家".equals(UserManager.currentUser.getType())) {
			this.setExtendedState(Frame.MAXIMIZED_BOTH);//全屏显示
			this.setTitle("外卖助手管理系统——商家界面");
			this.menu_product.add(this.menuItem_protype); this.menuItem_protype.addActionListener(this);
			this.menu_product.add(this.menuItem_prolist); this.menuItem_prolist.addActionListener(this);
			this.menu_activity.add(this.menuItem_full); this.menuItem_full.addActionListener(this);
			this.menu_activity.add(this.menuItem_coupon); this.menuItem_coupon.addActionListener(this);
			this.menu_checkorder.add(this.menuItem_neworder); this.menuItem_neworder.addActionListener(this);
			this.menu_checkorder.add(this.menuItem_okoreder); this.menuItem_okoreder.addActionListener(this);
			 menubar_shop.add(menu_product);
			 menubar_shop.add(menu_activity);
			 menubar_shop.add(menu_checkorder);
			 this.setJMenuBar(menubar_shop);
		}else if("顾客".equals(UserManager.currentUser.getType())) {
			try {
				new FrmCustomer();
			} catch (BaseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}else if("骑手".equals(UserManager.currentUser.getType())) {
			this.setExtendedState(Frame.MAXIMIZED_BOTH);//全屏显示
			this.setTitle("外卖助手管理系统——骑手界面");
			toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
			this.toolBar.add(this.btnAccept);this.btnAccept.addActionListener(this);
			this.toolBar.add(this.btnComplish);this.btnComplish.addActionListener(this);
			this.toolBar.add(this.btnAccount);this.btnAccount.addActionListener(this);
			/*this.toolBar.add(this.btnall);this.btnall.addActionListener(this);*/
			
			this.getContentPane().add(toolBar, BorderLayout.NORTH);
			
			this.getContentPane().add(new JScrollPane(this.ROrderTable), BorderLayout.WEST);
		    this.ROrderTable.addMouseListener(new MouseAdapter (){

				@Override
				public void mouseClicked(MouseEvent e) {
					int i=FrmMain.this.ROrderTable.getSelectedRow();
					if(i<0) {
						return;
					}
					FrmMain.this.reloadR_OrderTable();
				}
		    	
		    });
		    this.getContentPane().add(new JScrollPane(this.ROrderTable), BorderLayout.CENTER);
		    this.reloadR_OrderTable();
		}
	  
	    //状态栏
	    statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
	    JLabel label=new JLabel("您好!"+UserManager.currentUser.getUser_id());
	    statusBar.add(label);
	    this.getContentPane().add(statusBar,BorderLayout.SOUTH);
	    this.addWindowListener(new WindowAdapter(){   
	    	public void windowClosing(WindowEvent e){ 
	    		System.exit(0);
             }
        });
	    this.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		//管理员
		if(e.getSource()==this.menuItem_DeleteCus) {
			FrmDeleteCus dlg=new FrmDeleteCus(this,"修改用户",true);
			dlg.setVisible(true);
		}
	/*	else if(e.getSource()==this.menuItem_DeleteShop) {
			FrmDeleteShop dlg=new FrmDeleteShop(this,"修改商家",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_DeleteRider) {
			FrmDeleteRider dlg=new FrmDeleteRider(this,"修改骑手",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_DeleteOrder) {
			FrmDeleteOrder dlg=new FrmDeleteOrder(this,"修改管理员",true);
			dlg.setVisible(true);
		}
	*/	
		//商家
		else if(e.getSource()==this.menuItem_prolist){
			FrmPro dlg=new FrmPro(this,"商品菜单",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_protype){
			FrmType dlg=new FrmType(this,"商品类型",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_full){
			FrmFull dlg=new FrmFull(this,"满减活动",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_coupon){
			FrmCoupon dlg=new FrmCoupon(this,"优惠券",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.btnAccept){//接单
			int i=this.ROrderTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择订单","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if("骑手已接单".equals(tblData[i][7].toString())) {
				JOptionPane.showMessageDialog(null,  "已有骑手接单，请重新选择","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"确定接单？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				try {
					String orderid= tblData[i][0].toString();
					(new OrderManager()).R_accept_update(orderid);
					(new RiderManager()).createR_Eva();//订单生成后生成骑手评价表已有部分，无评论
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
			}
			this.setVisible(true);
			reloadR_OrderTable();
		}
		else if(e.getSource()==this.btnComplish){//完成
			int i=this.ROrderTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择订单","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(!UserManager.currentUser.getUser_id().equals(tblData[i][8].toString())) {
				JOptionPane.showMessageDialog(null,  "无法完成其他骑手的订单，请重新选择","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if("成功下单,等待接单".equals(tblData[i][7].toString())) {
				System.out.println("成功下单,等待接单".equals(tblData[i][7].toString()));
				JOptionPane.showMessageDialog(null,  "请先接单再完成","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"是否完成订单？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				try {
					String orderid= tblData[i][0].toString();
					(new OrderManager()).R_complish_update(orderid);//改变订单状态
					(new RiderManager()).ComplishOrder(orderid);//增加入账表信息
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
			}
			this.setVisible(true);
			reloadR_OrderTable();
		}
		else if(e.getSource()==this.btnAccount){//骑手账单
			FrmR_Account dlg;
			try {
				dlg = new FrmR_Account();dlg.setVisible(true);
			} catch (BaseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}

	}
}