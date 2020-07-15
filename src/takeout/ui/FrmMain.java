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
	
	//����Ա
    private JMenu menu_cus=new JMenu("�˿͹���");
    private JMenu menu_shop=new JMenu("�̼ҹ���");
    private JMenu menu_rider=new JMenu("���ֹ���");
    private JMenu menu_order=new JMenu("��������");
   // �̼�
    private JMenu menu_product=new JMenu("��Ʒ����");
    private JMenu menu_activity=new JMenu("��������");
    private JMenu menu_checkorder=new JMenu("�����鿴");
    //�˿�
    //����
    private Button btnAccept = new Button("�ӵ�");
	private Button btnComplish = new Button("��ɶ���");
	private Button btnAccount = new Button("��ʷ�ӵ����������");
	//private Button btnall = new Button("������");
	
	
    //1
    private JMenuItem  menuItem_DeleteCus=new JMenuItem("�޸Ĺ˿�");
    private JMenuItem  menuItem_DeleteShop=new JMenuItem("�޸��̼�");
    private JMenuItem  menuItem_DeleteRider=new JMenuItem("�޸�����");
    private JMenuItem  menuItem_DeleteOrder=new JMenuItem("�޸Ķ���");
    //2
    private JMenuItem  menuItem_prolist=new JMenuItem("��Ʒ�˵�");
    private JMenuItem  menuItem_protype=new JMenuItem("��Ʒ���͹���");
    private JMenuItem  menuItem_full=new JMenuItem("�����");
    private JMenuItem  menuItem_coupon=new JMenuItem("�Ż�ȯ");
    private JMenuItem  menuItem_neworder=new JMenuItem("�¶���");
    private JMenuItem  menuItem_okoreder=new JMenuItem("����ɶ���");
    //3
    //д��FrmCustomer
    //4 ҳ��ֱ�Ӱ�ť
    /*
    private JMenuItem  menuItem_modifyPwd=new JMenuItem("�����޸�");
    private JMenuItem  menuItem_static1=new JMenuItem("ͳ��1");
    private JMenuItem  menuItem_register=new JMenuItem("ע��");
    */

	
    private Object tblTitle[]={"�������","�û�","ʡ","��","��","�µ�ʱ��","Ҫ���ʹ�","״̬","�ӵ�����"};
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
		dlgLogin=new FrmLogin(this,"��½",true);
		dlgLogin.setVisible(true);
	    //�˵�
		if("����Ա".equals(UserManager.currentUser.getType())){
			this.setExtendedState(Frame.MAXIMIZED_BOTH);//ȫ����ʾ
			this.setTitle("�������ֹ���ϵͳ��������Ա����");
		    this.menu_cus.add(this.menuItem_DeleteCus); this.menuItem_DeleteCus.addActionListener(this);
		    this.menu_shop.add(this.menuItem_DeleteShop); this.menuItem_DeleteShop.addActionListener(this);
		    this.menu_rider.add(this.menuItem_DeleteRider); this.menuItem_DeleteRider.addActionListener(this);
		    this.menu_order.add(this.menuItem_DeleteOrder); this.menuItem_DeleteOrder.addActionListener(this);
		    menubar_admin.add(menu_cus);
		    menubar_admin.add(menu_shop);
		    menubar_admin.add(menu_rider);
		    menubar_admin.add(menu_order);
		    this.setJMenuBar(menubar_admin);
		}else if("�̼�".equals(UserManager.currentUser.getType())) {
			this.setExtendedState(Frame.MAXIMIZED_BOTH);//ȫ����ʾ
			this.setTitle("�������ֹ���ϵͳ�����̼ҽ���");
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
		}else if("�˿�".equals(UserManager.currentUser.getType())) {
			try {
				new FrmCustomer();
			} catch (BaseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}else if("����".equals(UserManager.currentUser.getType())) {
			this.setExtendedState(Frame.MAXIMIZED_BOTH);//ȫ����ʾ
			this.setTitle("�������ֹ���ϵͳ�������ֽ���");
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
	  
	    //״̬��
	    statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
	    JLabel label=new JLabel("����!"+UserManager.currentUser.getUser_id());
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
		
		//����Ա
		if(e.getSource()==this.menuItem_DeleteCus) {
			FrmDeleteCus dlg=new FrmDeleteCus(this,"�޸��û�",true);
			dlg.setVisible(true);
		}
	/*	else if(e.getSource()==this.menuItem_DeleteShop) {
			FrmDeleteShop dlg=new FrmDeleteShop(this,"�޸��̼�",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_DeleteRider) {
			FrmDeleteRider dlg=new FrmDeleteRider(this,"�޸�����",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_DeleteOrder) {
			FrmDeleteOrder dlg=new FrmDeleteOrder(this,"�޸Ĺ���Ա",true);
			dlg.setVisible(true);
		}
	*/	
		//�̼�
		else if(e.getSource()==this.menuItem_prolist){
			FrmPro dlg=new FrmPro(this,"��Ʒ�˵�",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_protype){
			FrmType dlg=new FrmType(this,"��Ʒ����",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_full){
			FrmFull dlg=new FrmFull(this,"�����",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_coupon){
			FrmCoupon dlg=new FrmCoupon(this,"�Ż�ȯ",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.btnAccept){//�ӵ�
			int i=this.ROrderTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "��ѡ�񶩵�","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if("�����ѽӵ�".equals(tblData[i][7].toString())) {
				JOptionPane.showMessageDialog(null,  "�������ֽӵ���������ѡ��","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"ȷ���ӵ���","ȷ��",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				try {
					String orderid= tblData[i][0].toString();
					(new OrderManager()).R_accept_update(orderid);
					(new RiderManager()).createR_Eva();//�������ɺ������������۱����в��֣�������
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
				}
			}
			this.setVisible(true);
			reloadR_OrderTable();
		}
		else if(e.getSource()==this.btnComplish){//���
			int i=this.ROrderTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "��ѡ�񶩵�","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(!UserManager.currentUser.getUser_id().equals(tblData[i][8].toString())) {
				JOptionPane.showMessageDialog(null,  "�޷�����������ֵĶ�����������ѡ��","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if("�ɹ��µ�,�ȴ��ӵ�".equals(tblData[i][7].toString())) {
				System.out.println("�ɹ��µ�,�ȴ��ӵ�".equals(tblData[i][7].toString()));
				JOptionPane.showMessageDialog(null,  "���Ƚӵ������","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"�Ƿ���ɶ�����","ȷ��",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				try {
					String orderid= tblData[i][0].toString();
					(new OrderManager()).R_complish_update(orderid);//�ı䶩��״̬
					(new RiderManager()).ComplishOrder(orderid);//�������˱���Ϣ
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
				}
			}
			this.setVisible(true);
			reloadR_OrderTable();
		}
		else if(e.getSource()==this.btnAccount){//�����˵�
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