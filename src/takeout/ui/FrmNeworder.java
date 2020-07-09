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
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import takeout.util.BaseException;

public class FrmNeworder extends JDialog {
	private JPanel toolBar = new JPanel();
	private Object tblTitle[]={"商品编号","商品名称","价格","优惠价格","所属分类"};
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable proTable=new JTable(tablmod);
	private void reloadProTable(){
		try {
			List<BeanProduct> pro=(new ProManager()).loadAllPro();
			tblData =new Object[pro.size()][5];
			for(int i=0;i<pro.size();i++){
				tblData[i][0]=pro.get(i).getPro_id();
				tblData[i][1]=pro.get(i).getPro_name();
				tblData[i][2]=pro.get(i).getPro_price();
				tblData[i][3]=pro.get(i).getPro_discount_amount();
				tblData[i][4]=pro.get(i).getType_id();
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.proTable.validate();
			this.proTable.repaint();
			
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public FrmNeworder(Frame f, String s, boolean b) {
		super(f, s, b);
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		//提取现有数据
		this.reloadProTable();
		this.getContentPane().add(new JScrollPane(this.proTable), BorderLayout.CENTER);
		
		//屏幕居中显示
		this.setSize(800, 600);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();

	}
	
}
