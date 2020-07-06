package takeout.ui;

import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import takeout.ui.*;
import takeout.control.UserManager;
import takeout.model.BeanUser;


public class FrmMain extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JMenuBar menubar=new JMenuBar(); ;
    private JMenu menu_cus=new JMenu("顾客管理");
    private JMenu menu_shop=new JMenu("商家管理");
    private JMenu menu_rider=new JMenu("骑手管理");
    private JMenu menu_pro=new JMenu("产品管理");
    private JMenu menu_order=new JMenu("订单管理");
   // private JMenu menu_more=new JMenu("更多");
    
    private JMenuItem  menuItem_AddCus=new JMenuItem("添加顾客");
    private JMenuItem  menuItem_DeleteCus=new JMenuItem("删除顾客");
    
    private JMenuItem  menuItem_AddShop=new JMenuItem("添加商家");
    private JMenuItem  menuItem_DeleteShop=new JMenuItem("删除商家");
    
    private JMenuItem  menuItem_AddRider=new JMenuItem("添加骑手");
    private JMenuItem  menuItem_DeleteRider=new JMenuItem("删除骑手");
    
    private JMenuItem  menuItem_AddPro=new JMenuItem("添加产品");
    private JMenuItem  menuItem_finishPro=new JMenuItem("删除产品");
    
    private JMenuItem  menuItem_AddOrder=new JMenuItem("添加订单");
    private JMenuItem  menuItem_DeleteOrder=new JMenuItem("删除订单");
    
    /*
    private JMenuItem  menuItem_modifyPwd=new JMenuItem("密码修改");
    private JMenuItem  menuItem_static1=new JMenuItem("统计1");
    private JMenuItem  menuItem_register=new JMenuItem("注册");
    */
   
	private FrmLogin dlgLogin=null;
	private JPanel statusBar = new JPanel();
	 /*
	private Object tblPlanTitle[]=BeanPlan.tableTitles;
	private Object tblPlanData[][];
	DefaultTableModel tabPlanModel=new DefaultTableModel();
	private JTable dataTablePlan=new JTable(tabPlanModel);
	
	
	private Object tblStepTitle[]=BeanStep.tblStepTitle;
	private Object tblStepData[][];
	DefaultTableModel tabStepModel=new DefaultTableModel();
	private JTable dataTableStep=new JTable(tabStepModel);
	
	private BeanPlan curPlan=null;
	List<BeanPlan> allPlan=null;
	List<BeanStep> planSteps=null;
	private void reloadPlanTable(){//这是测试数据，需要用实际数替换
		try {
			allPlan=PersonPlanUtil.planManager.loadAll();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblPlanData =  new String[allPlan.size()][BeanPlan.tableTitles.length];
		for(int i=0;i<allPlan.size();i++){
			for(int j=0;j<BeanPlan.tableTitles.length;j++)
				tblPlanData[i][j]=getCell1(allPlan,i,j);
		}
		tabPlanModel.setDataVector(tblPlanData,tblPlanTitle);
		this.dataTablePlan.validate();
		this.dataTablePlan.repaint();
	}
	public String getCell1(List<BeanPlan> allPlan,int row,int col){
		BeanPlan bp=new BeanPlan();
		bp=allPlan.get(row);
		if(col==0) return String.valueOf(bp.getPlan_id());
		else if(col==1) return bp.getPlan_name();
		else if(col==2) return String.valueOf(bp.getStep_count());
		else if(col==3) return String.valueOf(bp.getStart_step_count());
		else return "";
	}
	private void reloadPlanStepTabel(int planIdx){
		if(planIdx<0) return;
		curPlan=allPlan.get(planIdx);
		try {
			planSteps=PersonPlanUtil.stepManager.loadSteps(curPlan);
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblStepData =new String[planSteps.size()][BeanStep.tblStepTitle.length];
		for(int i=0;i<planSteps.size();i++){
			for(int j=0;j<BeanStep.tblStepTitle.length;j++)
				tblStepData[i][j]=getCell(planSteps,i,j);
		}
		
		tabStepModel.setDataVector(tblStepData,tblStepTitle);
		this.dataTableStep.validate();
		this.dataTableStep.repaint();
	}
	public String getCell(List<BeanStep> planSteps,int row,int col){
		BeanStep bs=new BeanStep();
		bs=planSteps.get(row);
		if(col==0) return String.valueOf(bs.getStep_id());
		else if(col==1) return bs.getStep_name();
		else if(col==2) return String.valueOf(bs.getPlan_begin_time());
		else if(col==3) return String.valueOf(bs.getPlan_end_time());
		else if(col==4) return String.valueOf(bs.getReal_begin_time());
		else if(col==5) return String.valueOf(bs.getReal_end_time());
		else return "";

	}*/
	public FrmMain(){
		
		this.setExtendedState(Frame.MAXIMIZED_BOTH);//全屏显示
		this.setTitle("外卖助手管理系统");
		dlgLogin=new FrmLogin(this,"登陆",true);
		dlgLogin.setVisible(true);
	    //菜单
		//System.out.println(UserManager.currentUser.getUser_id());
		//BeanUser.currentLoginUser.getUser_id()
		//if("管理员".equals(UserManager.currentUser.getUser_id())){
			this.menu_cus.add(this.menuItem_AddCus); this.menuItem_AddCus.addActionListener(this);
		    this.menu_cus.add(this.menuItem_DeleteCus); this.menuItem_DeleteCus.addActionListener(this);
		    this.menu_shop.add(this.menuItem_AddShop); this.menuItem_AddShop.addActionListener(this);
		    this.menu_shop.add(this.menuItem_DeleteShop); this.menuItem_DeleteShop.addActionListener(this);
		    this.menu_rider.add(this.menuItem_AddRider); this.menuItem_AddRider.addActionListener(this);
		    this.menu_rider.add(this.menuItem_DeleteRider); this.menuItem_DeleteRider.addActionListener(this);
		    this.menu_pro.add(this.menuItem_AddPro); this.menuItem_AddPro.addActionListener(this);
		    this.menu_pro.add(this.menuItem_finishPro); this.menuItem_finishPro.addActionListener(this);
		    this.menu_order.add(this.menuItem_AddOrder); this.menuItem_AddOrder.addActionListener(this);
		    this.menu_order.add(this.menuItem_DeleteOrder); this.menuItem_DeleteOrder.addActionListener(this);
		   
		    menubar.add(menu_cus);
		    menubar.add(menu_shop);
		    menubar.add(menu_rider);
		    menubar.add(menu_pro);
		    menubar.add(menu_order);
		    this.setJMenuBar(menubar);
		    
	//	}
    
	    
	   /* this.getContentPane().add(new JScrollPane(this.dataTablePlan), BorderLayout.WEST);
	    this.dataTablePlan.addMouseListener(new MouseAdapter (){

			@Override
			public void mouseClicked(MouseEvent e) {
				int i=FrmMain.this.dataTablePlan.getSelectedRow();
				if(i<0) {
					return;
				}
				FrmMain.this.reloadPlanStepTabel(i);
			}
	    	
	    });
	    
	    
	    //this.getContentPane().add(new JScrollPane(this.dataTableStep), BorderLayout.CENTER);
	    
	   // this.reloadPlanTable();
	    * */
	  
	    //状态栏
	    statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
	    JLabel label=new JLabel("您好!"+BeanUser.currentLoginUser.getUser_id());//修改成   您好！+登陆用户名
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
		/*if(e.getSource()==this.menuItem_AddCus){
			FrmAddPlan dlg=new FrmAddPlan(this,"添加计划",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_DeletePlan){
			if(this.curPlan==null) {
				JOptionPane.showMessageDialog(null, "请选择计划", "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				PersonPlanUtil.planManager.deletePlan(this.curPlan);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		else if(e.getSource()==this.menuItem_AddStep){
			FrmAddStep dlg=new FrmAddStep(this,"添加步骤",true);
			dlg.plan=curPlan;
			dlg.setVisible(true);
			this.reloadPlanStepTabel(curPlan.getPlan_order()-1);
			this.reloadPlanTable();
			int j = FrmMain.this.dataTablePlan.getSelectedRow();
		      if (j < 0) {
		        return;
		      }
		      FrmMain.this.reloadPlanStepTabel(j);
		}
		else if(e.getSource()==this.menuItem_DeleteStep){
			int i=FrmMain.this.dataTableStep.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null, "请选择步骤", "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				PersonPlanUtil.stepManager.deleteStep(this.planSteps.get(i));
				int j = FrmMain.this.dataTablePlan.getSelectedRow();
		        if (j < 0) {
		          return;
		        }
		        FrmMain.this.reloadPlanStepTabel(j);
		        this.reloadPlanStepTabel(curPlan.getPlan_order() - 1);
		        this.reloadPlanTable();
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		else if(e.getSource()==this.menuItem_startStep){
			int i=FrmMain.this.dataTableStep.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null, "请选择步骤", "错误",JOptionPane.ERROR_MESSAGE);
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
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		else if(e.getSource()==this.menuItem_finishStep){
			int i=FrmMain.this.dataTableStep.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null, "请选择步骤", "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				PersonPlanUtil.stepManager.finishStep(this.planSteps.get(i));
				int j = FrmMain.this.dataTablePlan.getSelectedRow();
		        this.reloadPlanStepTabel(curPlan.getPlan_order() - 1);
		        this.reloadPlanTable();
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		else if(e.getSource()==this.menuItem_moveUpStep){
			int i=FrmMain.this.dataTableStep.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null, "请选择步骤", "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				PersonPlanUtil.stepManager.moveUp(this.planSteps.get(i));
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		else if(e.getSource()==this.menuItem_moveDownStep){
			int i=FrmMain.this.dataTableStep.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null, "请选择步骤", "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				PersonPlanUtil.stepManager.moveDown(this.planSteps.get(i));
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		else if(e.getSource()==this.menuItem_static1){
			
		}
		else if(e.getSource()==this.menuItem_modifyPwd){
			FrmModifyPwd dlg=new FrmModifyPwd(this,"密码修改",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_register){
			FrmRegister dlg=new FrmRegister(this,"注册",true);
			dlg.setVisible(true);
		}*/
		
	}
}
