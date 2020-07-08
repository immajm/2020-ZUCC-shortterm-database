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

import takeout.control.ProManager;
import takeout.control.TypeManager;
import takeout.model.BeanPro_Type;
import takeout.model.BeanProduct;
import takeout.util.BaseException;

public class FrmType extends JDialog implements ActionListener{
	private JPanel toolBar = new JPanel();
	private Button btnAdd = new Button("添加类别");
	private Button btnDelete = new Button("删除类别");
	private Object tblTitle[]={"类别编号","类别名称","产品数量"};
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable typeTable=new JTable(tablmod);
	private void reloadTypeTable(){
		try {
			List<BeanPro_Type> type=(new TypeManager()).loadAllType();
			tblData =new Object[type.size()][3];
			for(int i=0;i<type.size();i++){
				tblData[i][0]=type.get(i).getType_id();
				tblData[i][1]=type.get(i).getType_name();
				tblData[i][2]=type.get(i).getPro_quantity();
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.typeTable.validate();
			this.typeTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public FrmType(Frame f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnAdd);
		toolBar.add(this.btnDelete);
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		//提取现有数据
		this.reloadTypeTable();
		this.getContentPane().add(new JScrollPane(this.typeTable), BorderLayout.CENTER);
		
		//屏幕居中显示
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
			FrmType_add dlg=new FrmType_add(this,"添加类别",true);
			dlg.setVisible(true);
			//刷新表格
			this.reloadTypeTable();
		}
		else if(e.getSource()==this.btnDelete){
			int i=this.typeTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择类别","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"确定删除该类别吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				String id=this.tblData[i][0].toString();
				try {
					(new TypeManager()).deleteUser(id);
					this.reloadTypeTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}
	}
}
