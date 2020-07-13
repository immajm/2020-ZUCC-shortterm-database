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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import takeout.control.CouponManager;
import takeout.control.OrderManager;
import takeout.control.ProManager;
import takeout.model.BeanOrder_detail;
import takeout.model.BeanProduct;
import takeout.util.BaseException;
import takeout.util.BusinessException;
import takeout.util.DBUtil;
import takeout.util.DbException;

public class FrmCheckOrder extends JDialog implements ActionListener{
	private JPanel toolBar = new JPanel();
	private Button btnDelete = new Button("删除商品");
	private Button btnOk = new Button("结算");
	private Button btnCancel = new Button("取消");
	private Object tblTitle[]={"订单编号","商品编号","商品数量","单品价格","单品优惠"};
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable otable=new JTable(tablmod);
	 private void reloadOrder_dTable(){
			try {
				List<BeanOrder_detail> u=(new OrderManager()).loadAllOrder_d();
				tblData =new Object[u.size()][5];
				for(int i=0;i<u.size();i++){
					tblData[i][0]=u.get(i).getOrder_id();
					tblData[i][1]=u.get(i).getPro_id();
					tblData[i][2]=u.get(i).getSingle_quantity();
					tblData[i][3]=u.get(i).getSingle_cost();
					tblData[i][4]=u.get(i).getSingle_discount();
				}
				tablmod.setDataVector(tblData,tblTitle);
				this.otable.validate();
				this.otable.repaint();
				
			} catch (BaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	 
	public FrmCheckOrder(JDialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnDelete);
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		this.reloadOrder_dTable();
		this.getContentPane().add(new JScrollPane(this.otable), BorderLayout.CENTER);
		
		//屏幕居中显示
		this.setSize(800, 450);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();

		this.btnOk.addActionListener(this);
		this.btnCancel.addActionListener(this);
		this.btnDelete.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//System.exit(0);
			}
		});
	}
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==this.btnOk){
			if(JOptionPane.showConfirmDialog(this,"确定开始结算吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				FrmFinal_order dlg=new FrmFinal_order(this,"结算",true);
				dlg.setVisible(true);
			}
		}
		else if(e.getSource()==this.btnCancel) {
			this.setVisible(false);
			return;
		}
		else if(e.getSource()==this.btnDelete) {
			int i=this.otable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择要删除的商品","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"确定删除该商品吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				try {
					String orderid= tblData[i][0].toString();
					String proid = tblData[i][1].toString();
					(new OrderManager()).deleteOrd_detail(orderid,proid);
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
			}
			this.setVisible(true);
			reloadOrder_dTable();
		}
	}
   
    
}
