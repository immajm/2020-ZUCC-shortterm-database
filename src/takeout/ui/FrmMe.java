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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import takeout.control.AddressManager;
import takeout.control.ProManager;
import takeout.model.BeanAddress;
import takeout.model.BeanProduct;
import takeout.util.BaseException;

public class FrmMe  extends JDialog implements ActionListener{
	private JPanel toolBar = new JPanel();
	private Button btnAdddress = new Button("添加收货地址");
	
	private Object tblTitle[]={"地址编号","地址","用户","联系电话","省","市","区"};
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable proTable=new JTable(tablmod);
	private void reloadAddTable(){
		try {
			List<BeanAddress> pro=(new AddressManager()).loadAllAddress();
			tblData =new Object[pro.size()][7];
			for(int i=0;i<pro.size();i++){
				tblData[i][0]=pro.get(i).getAddress_id();
				tblData[i][1]=pro.get(i).getAddress();
				tblData[i][2]=pro.get(i).getCus_id();
				tblData[i][3]=pro.get(i).getTel();
				tblData[i][4]=pro.get(i).getProvince();
				tblData[i][5]=pro.get(i).getCity();
				tblData[i][6]=pro.get(i).getRegion();
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.proTable.validate();
			this.proTable.repaint();
			
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public FrmMe(JDialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnAdddress);
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

		this.btnAdddress.addActionListener(this);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//System.exit(0);
			}
		});
	}
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==this.btnAdddress){
			FrmAddress dlg=new FrmAddress(this,"添加地址",true);
			dlg.setVisible(true);
			this.reloadAddTable();
		}
		
		
	}
}
