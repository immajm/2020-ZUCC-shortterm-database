package takeout.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import takeout.model.BeanPossess;
import takeout.util.BaseException;

public class FrmPossess extends JDialog implements ActionListener{
	public static String currentcoupon=null ;
	public static double currentcoupon_discount=0;
	public static Date coupon_endtime;
	
	
	private JPanel toolBar = new JPanel();
	private Button btnCollect = new Button("集单送券");
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	
	private Object tblTitle[]={"优惠券编号","商家编号","优惠金额","数量","结束时间"};
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable proTable=new JTable(tablmod);
	private void reloadCouponTable(){
		try {
			List<BeanPossess> pro=(new PossessManager()).loadPossess_cus();
			tblData =new Object[pro.size()][5];
			for(int i=0;i<pro.size();i++){
				tblData[i][0]=pro.get(i).getCoupon_id();
				tblData[i][1]=pro.get(i).getShop_id();
				tblData[i][2]=pro.get(i).getDiscount();
				tblData[i][3]=pro.get(i).getQuantity();
				tblData[i][4]=pro.get(i).getCoupon_endtime();
				coupon_endtime=(Date) tblData[i][4];
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.proTable.validate();
			this.proTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public FrmPossess(JDialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnCollect);
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
		
		this.btnCollect.addActionListener(this);
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
			
			if(JOptionPane.showConfirmDialog(this,"确定使用吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				
					currentcoupon = tblData[i][0].toString();//使用，优惠券数量-1//最好一次选定，重新选数量多减了
					currentcoupon_discount=(int)Double.parseDouble(tblData[i][2].toString());
					try {
						(new PossessManager()).minus_quantity();
					} catch (BaseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
			}
			this.setVisible(true);
			reloadCouponTable();
		}
		else if(e.getSource()==this.btnCollect){
			FrmCollect dlg=new FrmCollect(this,"集单送券表",true);
			dlg.setVisible(true);
			this.reloadCouponTable();
		}
	}
}
