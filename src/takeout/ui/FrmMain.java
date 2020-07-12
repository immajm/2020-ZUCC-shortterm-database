package takeout.ui;

import java.awt.event.*;
import java.awt.BorderLayout;
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


import takeout.control.UserManager;
import takeout.ui.*;
import takeout.util.BaseException;



public class FrmMain extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JMenuBar menubar_admin=new JMenuBar(); 
	private JMenuBar menubar_shop=new JMenuBar(); 
	private JMenuBar menubar_cus=new JMenuBar(); 
	private JMenuBar menubar_rider=new JMenuBar(); 
	
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
    private JMenu menu_menu=new JMenu("�˵�");
    //����
    private JMenu menu_spareorder=new JMenu("�ɽӶ���");
    ////************/////
    //1
    private JMenuItem  menuItem_AddCus=new JMenuItem("��ӹ˿�");
    private JMenuItem  menuItem_DeleteCus=new JMenuItem("ɾ���˿�");
    private JMenuItem  menuItem_AddShop=new JMenuItem("����̼�");
    private JMenuItem  menuItem_DeleteShop=new JMenuItem("ɾ���̼�");
    private JMenuItem  menuItem_AddRider=new JMenuItem("�������");
    private JMenuItem  menuItem_DeleteRider=new JMenuItem("ɾ������");
    private JMenuItem  menuItem_AddOrder=new JMenuItem("��Ӷ���");
    private JMenuItem  menuItem_DeleteOrder=new JMenuItem("ɾ������");
    //2
    private JMenuItem  menuItem_prolist=new JMenuItem("��Ʒ�˵�");
    private JMenuItem  menuItem_protype=new JMenuItem("��Ʒ���͹���");
    private JMenuItem  menuItem_full=new JMenuItem("�����");
    private JMenuItem  menuItem_coupon=new JMenuItem("�Ż�ȯ");
    private JMenuItem  menuItem_neworder=new JMenuItem("�¶���");
    private JMenuItem  menuItem_okoreder=new JMenuItem("����ɶ���");
    //3
    //private JMenuItem  menuItem_buy=new JMenuItem("�µ�");
    //4
    private JMenuItem  menuItem_takeorder=new JMenuItem("�ӵ�");
    
    /*
    private JMenuItem  menuItem_modifyPwd=new JMenuItem("�����޸�");
    private JMenuItem  menuItem_static1=new JMenuItem("ͳ��1");
    private JMenuItem  menuItem_register=new JMenuItem("ע��");
    */

	private FrmLogin dlgLogin=null;
	private JPanel statusBar = new JPanel();
	

	
	public FrmMain() {
		dlgLogin=new FrmLogin(this,"��½",true);
		dlgLogin.setVisible(true);
	    //�˵�
		if("����Ա".equals(UserManager.currentUser.getType())){
			this.setExtendedState(Frame.MAXIMIZED_BOTH);//ȫ����ʾ
			this.setTitle("�������ֹ���ϵͳ��������Ա����");
			this.menu_cus.add(this.menuItem_AddCus); this.menuItem_AddCus.addActionListener(this);
		    this.menu_cus.add(this.menuItem_DeleteCus); this.menuItem_DeleteCus.addActionListener(this);
		    this.menu_shop.add(this.menuItem_AddShop); this.menuItem_AddShop.addActionListener(this);
		    this.menu_shop.add(this.menuItem_DeleteShop); this.menuItem_DeleteShop.addActionListener(this);
		    this.menu_rider.add(this.menuItem_AddRider); this.menuItem_AddRider.addActionListener(this);
		    this.menu_rider.add(this.menuItem_DeleteRider); this.menuItem_DeleteRider.addActionListener(this);
		    this.menu_order.add(this.menuItem_AddOrder); this.menuItem_AddOrder.addActionListener(this);
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
			this.menu_spareorder.add(this.menuItem_takeorder); this.menuItem_takeorder.addActionListener(this);
			menubar_rider.add(menu_spareorder);
			this.setJMenuBar(menubar_rider);
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
		//�̼�
		if(e.getSource()==this.menuItem_prolist){
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
		/*else if(e.getSource()==this.menuItem_neworder){
			FrmNeworder dlg=new FrmNeworder(this,"�¶���",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_okoreder){
			FrmOkorder dlg=new FrmOkorder(this,"����ɶ���",true);
			dlg.setVisible(true);
	}
		//�̼Ҷ�������û��д
		 * 
		 * 
		else if(e.getSource()==this.menuItem_startStep){
			int i=FrmMain.this.dataTableStep.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null, "��ѡ����", "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				PersonPlanUtil.stepManager.startStep(this.planSteps.get(i));
				int j = FrmMain.this.dataTablePlan.getSelectedRow();
		        if (j < 0) {
		          return;
		        }
		        FrmMain.this.reloadPlanStepTabel(j);
		        this.reloadPlanStepTabel(curPlan.getPlan_order() - 1);
		        this.reloadPlanTable();
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		else if(e.getSource()==this.menuItem_finishStep){
			int i=FrmMain.this.dataTableStep.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null, "��ѡ����", "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				PersonPlanUtil.stepManager.finishStep(this.planSteps.get(i));
				int j = FrmMain.this.dataTablePlan.getSelectedRow();
		        this.reloadPlanStepTabel(curPlan.getPlan_order() - 1);
		        this.reloadPlanTable();
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		else if(e.getSource()==this.menuItem_moveUpStep){
			int i=FrmMain.this.dataTableStep.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null, "��ѡ����", "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				PersonPlanUtil.stepManager.moveUp(this.planSteps.get(i));
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		else if(e.getSource()==this.menuItem_moveDownStep){
			int i=FrmMain.this.dataTableStep.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null, "��ѡ����", "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				PersonPlanUtil.stepManager.moveDown(this.planSteps.get(i));
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		else if(e.getSource()==this.menuItem_static1){
			
		}
		else if(e.getSource()==this.menuItem_modifyPwd){
			FrmModifyPwd dlg=new FrmModifyPwd(this,"�����޸�",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_register){
			FrmRegister dlg=new FrmRegister(this,"ע��",true);
			dlg.setVisible(true);
		}*/
		
	}
}