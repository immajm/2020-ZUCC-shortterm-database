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

import takeout.control.FullManager;
import takeout.control.ProManager;
import takeout.model.BeanFull;
import takeout.model.BeanProduct;
import takeout.util.BaseException;

public class FrmFull extends JDialog implements ActionListener{
	private JPanel toolBar = new JPanel();
	private Button btnAdd = new Button("添加满减活动");
	private Button btnDelete = new Button("删除满减活动");
	private Object tblTitle[]={"满减编号","满减要求金额","优惠金额","可否与优惠券叠加"};
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable fullTable=new JTable(tablmod);
	private void reloadFullTable(){
		try {
			List<BeanFull> full=(new FullManager()).loadAllFull();
			tblData =new Object[full.size()][5];
			for(int i=0;i<full.size();i++){
				tblData[i][0]=full.get(i).getFull_id();
				tblData[i][1]=full.get(i).getFull_demand();
				tblData[i][2]=full.get(i).getFull_reduction();
				tblData[i][3]=full.get(i).getTag();
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.fullTable.validate();
			this.fullTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public FrmFull(Frame f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnAdd);
		toolBar.add(this.btnDelete);
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		//提取现有数据
		this.reloadFullTable();
		this.getContentPane().add(new JScrollPane(this.fullTable), BorderLayout.CENTER);
		
		// 屏幕居中显示
		this.setSize(800, 600);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();

		this.btnAdd.addActionListener(this);
		this.btnDelete.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//System.exit(0);
			}
		});
	}
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.btnAdd){
			FrmFull_add dlg=new FrmFull_add(this,"添加满减活动",true);
			dlg.setVisible(true);
			//刷新表格
			this.reloadFullTable();
		}
		else if(e.getSource()==this.btnDelete){
			int i=this.fullTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择满减活动","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"确定删除该满减活动吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				String id=this.tblData[i][0].toString();
				try {
					(new FullManager()).deleteUser(id);
					this.reloadFullTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}
	}
	
}
