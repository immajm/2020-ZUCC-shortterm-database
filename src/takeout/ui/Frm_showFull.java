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

import takeout.control.FullManager;
import takeout.control.OrderManager;
import takeout.model.BeanFull;
import takeout.util.BaseException;

public class Frm_showFull extends JDialog implements ActionListener{
	public static String currentfull=null ;
	public static double currentfulldemand=0;
	public static double currentfullreduction=0 ;
	
	private JPanel toolBar = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	
	private Object tblTitle[]={"满减编号","商家编号","满减要求","满减金额","是否共用优惠券"};
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable proTable=new JTable(tablmod);
	private void reloadFullTable(){
		try {
			List<BeanFull> pro=(new FullManager()).loadFull_cus();
			tblData =new Object[pro.size()][5];
			for(int i=0;i<pro.size();i++){
				tblData[i][0]=pro.get(i).getFull_id();
				tblData[i][1]=pro.get(i).getShop_id();
				tblData[i][2]=pro.get(i).getFull_demand();
				tblData[i][3]=pro.get(i).getFull_reduction();
				tblData[i][4]=pro.get(i).getTag();
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.proTable.validate();
			this.proTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Frm_showFull(JDialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		this.reloadFullTable();
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
				JOptionPane.showMessageDialog(null,  "请选择满减方案","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if(JOptionPane.showConfirmDialog(this,"确定使用吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				
				currentfull = tblData[i][0].toString();//金额到达就可以满减，否则不行
				currentfulldemand=(int)Double.parseDouble(tblData[i][2].toString());
				currentfullreduction=(int)Double.parseDouble(tblData[i][3].toString());
				double totalcost = 0;
				
				try {
					totalcost=(new OrderManager().original());
				} catch (BaseException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				if(currentfulldemand<totalcost) {
					try {
						(new OrderManager()).updatefullid();
					} catch (BaseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else {
					JOptionPane.showMessageDialog(null,  "不满足满减要求","提示",JOptionPane.ERROR_MESSAGE);
					return;
				}
				this.setVisible(true);
				reloadFullTable();
			}
		}
	}
}
