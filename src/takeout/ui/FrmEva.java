package takeout.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import takeout.control.AddressManager;
import takeout.control.ProManager;
import takeout.control.UserManager;
import takeout.model.BeanAddress;
import takeout.model.BeanPro_Evaluate;
import takeout.model.BeanProduct;
import takeout.model.BeanUser;
import takeout.util.BaseException;

public class FrmEva extends JDialog implements ActionListener{//需要注意的是，在完成订单后在订单文件中添加pro_evaluate中的内容
	public static BeanPro_Evaluate currentorderid=null;
	public static BeanPro_Evaluate currentproid=null;
	
	private JPanel toolBar = new JPanel();
	private Button btnEva = new Button("进行商品评价");
	private Object tblTitle[]={"订单编号","商家编号","商品编号","评价内容","评价日期","星级"};
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable proTable=new JTable(tablmod);
	private void reloadAddTable(){
		try {
			List<BeanPro_Evaluate> pro=(new ProManager()).loadAllEva();
			tblData =new Object[pro.size()][6];
			for(int i=0;i<pro.size();i++){
				tblData[i][0]=pro.get(i).getOrder_id();
				tblData[i][1]=pro.get(i).getShop_id();
				tblData[i][2]=pro.get(i).getPro_id();
				tblData[i][3]=pro.get(i).getComment();
				tblData[i][4]=pro.get(i).getComment_date();
				tblData[i][5]=pro.get(i).getPro_level();
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.proTable.validate();
			this.proTable.repaint();
			
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public FrmEva(JDialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnEva);
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		//提取现有数据
		this.reloadAddTable();
		this.getContentPane().add(new JScrollPane(this.proTable), BorderLayout.CENTER);
		//屏幕居中显示
		this.setSize(800,450);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();

		this.btnEva.addActionListener(this);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//System.exit(0);
			}
		});
	}
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==this.btnEva){
			int i= this.proTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,"请选择订单","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			else {
				FrmEva.currentorderid.setOrder_id(tblData[i][0].toString());
				FrmEva.currentproid.setPro_id(tblData[i][2].toString());
				FrmEva_Modify dlg=new FrmEva_Modify(this,"增加评价",true);
				dlg.setVisible(true);
				this.reloadAddTable();
			}
		}
	}
}
